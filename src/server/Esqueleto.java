package org.example;
import java.sql.SQLException;

public class Esqueleto {
    private Database db;

    public Esqueleto(Database db) {
        this.db = db;
    }

    public String adicionarFilme(String arguments) {
        Filme filme = Filme.fromJson(arguments);
        try {
            return db.adicionarFilme(filme);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String removerFilme(String arguments) {
        int id = Integer.parseInt(arguments);
        try {
            return db.removerFilme(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String exibirDetalhe(String arguments) {
        int id = Integer.parseInt(arguments);
        try {
            return db.exibirDetalhe(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String mostrarCatalogo() {
        try {
            return db.mostrarCatalogo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}