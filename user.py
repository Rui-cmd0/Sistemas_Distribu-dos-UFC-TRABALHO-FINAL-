from proxy import Proxy
from filme import Filme
import time
from os import system

proxy = Proxy()

def testaNum(value):
    try:
        int(value)
        return True
    except ValueError:
        return False

def testaInput(prompt):
    while True:
        value = input(prompt)
        if testaNum(value):
            return int(value)
        else:
            print("Apenas números são permitidos!")

print("-----------------------------------")
print("Gerenciador de catálogo de filmes")
print("-----------------------------------")
print("Operações:\n1-Cadastrar\n2-Remover\n3-Listar Detalhes\n4-Exibir catálogo\n5-Limpar tela\n6-Sair")
print("-----------------------------------")

while True:
    operation = input("Insira uma operação: ")

    if operation == "1":
        print("[Cadastrar]")
        titulo = input("Título: ")
        diretor = input("Diretor: ")
        ano = testaInput("Ano: ")
        duracao = testaInput("Duração: ")
        genero = input("Gênero: ")
        classificacao = testaInput("Classificação: ")
        descricao = input("Descrição: ")
        filme = Filme(titulo, diretor, ano, duracao, genero, classificacao, descricao)
        proxy.adicionarFilme(filme)

    elif operation == "2":
        print("[Remover]")
        id = testaInput("ID: ")
        proxy.removerFilme(id)

    elif operation == "3":
        print("[Exibindo detalhes]")
        id = testaInput("ID: ")
        proxy.exibirDetalhe(id)

    elif operation == "4":
        print("[Exibindo catálogo]")
        proxy.mostrarCatalogo()

    elif operation == "5":
        system("cls")
        print("-----------------------------------")
        print("Gerenciador de catálogo de filmes")
        print("-----------------------------------")
        print("Operações:\n1-Cadastrar\n2-Remover\n3-Listar Detalhes\n4-Exibir catálogo\n5-Limpar tela\n6-Sair")
        print("-----------------------------------")

    elif operation == "6":
        print("Encerrando...")
        time.sleep(1)
        proxy.close()
        break

    elif operation.isdigit() == False:
        print("\nOperação inválida!\n")

    else:
        print("\nOpção inválida!\n")