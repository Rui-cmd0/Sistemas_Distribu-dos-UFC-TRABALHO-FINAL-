package org.example;

public class Despachante {
    private Database db;

    public Despachante(Database db) {
        this.db = db;
    }

    public Cabecalho invoke(String jsonStr) {
        Cabecalho cabecalho = Cabecalho.fromJson(jsonStr);
        int methodId = cabecalho.getMethodId();
        String arguments = cabecalho.getArguments();
        Esqueleto esqueleto = new Esqueleto(db);
        String responseArguments;

        switch (methodId) {
            case 1:
                responseArguments = esqueleto.adicionarFilme(arguments);
                break;
            case 2:
                responseArguments = esqueleto.removerFilme(arguments);
                break;
            case 3:
                responseArguments = esqueleto.exibirDetalhe(arguments);
                break;
            case 4:
                responseArguments = esqueleto.mostrarCatalogo();
                break;
            default:
                responseArguments = "Método não reconhecido";
                break;
        }

        return new Cabecalho(1, cabecalho.getObjectReference(), methodId, responseArguments);
    }
}