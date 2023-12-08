from json import *

class Filme:
    def __init__(self, titulo, diretor, ano, duracao, genero, classificacao, descricao):
        self.id=None
        self.titulo=titulo
        self.diretor=diretor
        self.ano=ano
        self.duracao=duracao
        self.genero=genero
        self.classificacao=classificacao
        self.descricao=descricao
        

    def to_json(self):
        filme_data = {
            "titulo": self.titulo,
            "diretor": self.diretor,
            "ano": self.ano,
            "duracao": self.duracao,
            "genero": self.genero,
            "classificacao": self.classificacao,
            "descricao": self.descricao
        }
        return dumps(filme_data)
    
    @classmethod
    def from_json(cls, json_str):
        return cls(**loads(json_str))