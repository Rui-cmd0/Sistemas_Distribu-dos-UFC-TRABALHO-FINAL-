from udpClient import UDPClient
import json
from filme import Filme
from cabecalho import Cabecalho
import time

class Proxy:
    def __init__(self):
        self.client = UDPClient('34.201.103.18', 8080)
        self.count = 0
        self.maxCount = 3
        self.close = self.client.close
        self.requestId = 1

    def adicionarFilme(self, filme):
        filme_json = filme.to_json()
        data = self.doOperation("filme", 1, filme_json)

        if data is None:
            return

        if "Filme cadastrado com sucesso!" in data:
            print("\nFilme cadastrado com sucesso!\n")
            return

        if "Erro: Filme ja cadastrado!" in data:
            print("\nFilme ja cadastrado!\n")
            return

        try:
            header = Cabecalho.from_json(data)
            print(header.arguments)

        except Exception as e:
            print("Erro ao processar a resposta do servidor:", str(e))
            return

    def removerFilme(self, id):
        data = self.doOperation("filme", 2, id)

        if data is None:
            return

        if "Filme nao encontrado!" in data:
            print("\nFilme nao encontrado!\n")
            return

        if "Filme removido com sucesso!" in data:
            print("\nFilme removido com sucesso!\n")
            return

        try:
            header = Cabecalho.from_json(data)
            print(header.arguments)

        except Exception as e:
            print("Erro ao processar a resposta do servidor:", str(e))
            return

    def exibirDetalhe(self, id):
        data = self.doOperation("filme", 3, id)

        if data is None:
            return

        if "Filme nao encontrado!" in data:
            print("\nFilme nao encontrado!\n")
            return

        try:
            header = Cabecalho.from_json(data)
            filme = Filme.from_json(header.arguments)
            filme_hora = int(filme.duracao) // 60
            filme_minuto = int(filme.duracao) % 60
            print(f"\nTitulo: {filme.titulo}\nDiretor: {filme.diretor}\nAno: {filme.ano}\nDuração: {filme_hora}h{filme_minuto}min\nGênero: {filme.genero}\nClassificação: {filme.classificacao}\nDescrição: {filme.descricao}\n")

        except Exception as e:
            print("Erro ao processar a resposta do servidor:", str(e))
            return

    def mostrarCatalogo(self):
        data = self.doOperation("filme", 4, "")

        if data is None:
            return

        if "Catalogo vazio!" in data:
            print("\nCatalogo vazio!\n")
            return

        try:
            header = Cabecalho.from_json(data)
            catalogo = json.loads(header.arguments)
            for filme in catalogo:
                print(f"\nID: {filme['id']}\nTitulo: {filme['titulo']}\n")
                
        except Exception as e:
            print("Erro ao processar a resposta do servidor:", str(e))
            return

    def timeout(self):
        if self.count < self.maxCount:
            print("\nTempo de resposta excedido!\n")
            self.count += 1
        else:
            print("\nServidor indisponível! Encerrando conexão...\n")
            time.sleep(1)
            self.close()
            exit()

    def doOperation(self, objectReference, methodId, arguments):
        requestId = self.requestId
        cabecalho = Cabecalho(0, objectReference, methodId, arguments, requestId)
        msgJson = cabecalho.to_json()
        self.client.sendRequest(msgJson)
        data = self.client.getResponse()
        if data is None:
            self.timeout()
            data = self.client.getResponse()
            if data:
                self.count = 0
                return
            else:
                return
        self.requestId += 1
        msg_bytes = data[:len(data)]
        return msg_bytes