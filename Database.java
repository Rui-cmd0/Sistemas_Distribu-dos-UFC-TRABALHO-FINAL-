package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Database {
    private Connection connection;
    private PreparedStatement query;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/filme", "admin", "admin");
    }

    public String adicionarFilme(Filme filme) throws SQLException {
        query = connection.prepareStatement("SELECT id FROM filme WHERE titulo = ?");
        query.setString(1, filme.getTitulo());

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            return "Erro: Filme ja cadastrado!";
        } else {
            query = connection.prepareStatement("INSERT INTO filme (titulo, diretor, ano, duracao, genero, classificacao, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)");
            query.setString(1, filme.getTitulo());
            query.setString(2, filme.getDiretor());
            query.setInt(3, filme.getAno());
            query.setInt(4, filme.getDuracao());
            query.setString(5, filme.getGenero());
            query.setInt(6, filme.getClassificacao());
            query.setString(7, filme.getDescricao());

            int rowsAffected = query.executeUpdate();

            if (rowsAffected == 1) {
                return "Filme cadastrado com sucesso!";
            } else {
                return "Erro ao cadastrar filme!";
            }
        }
    }

    public String removerFilme(int id) throws SQLException {
        query = connection.prepareStatement("DELETE FROM filme WHERE id = ?");
        query.setInt(1, id);

        int rowsAffected = query.executeUpdate();

        if (rowsAffected == 1) {
            return "Filme removido com sucesso!";
        } else {
            return "Filme nao encontrado!";
        }
    }

    public String exibirDetalhe(int id) throws SQLException {
        query = connection.prepareStatement("SELECT * FROM filme WHERE id = ?");
        query.setInt(1, id);

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            String titulo = resultSet.getString("titulo");
            String diretor = resultSet.getString("diretor");
            int ano = resultSet.getInt("ano");
            int duracao = resultSet.getInt("duracao");
            String genero = resultSet.getString("genero");
            int classificacao = resultSet.getInt("classificacao");
            String descricao = resultSet.getString("descricao");

            Filme filme = new Filme(titulo, diretor, ano, duracao, genero, classificacao, descricao);
            return filme.toJson();
        } else {
            return "Filme nao encontrado!";
        }
    }

    public String mostrarCatalogo() throws SQLException {
        query = connection.prepareStatement("SELECT id, titulo FROM filme");
        ResultSet resultSet = query.executeQuery();

        List<JSONObject> catalogo = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String titulo = resultSet.getString("titulo");

            JSONObject filme = new JSONObject();
            filme.put("id", id);
            filme.put("titulo", titulo);

            catalogo.add(filme);
        }

        if (catalogo.isEmpty()) {
            return "Catalogo vazio!";
        }

        String catalogoJSON = new JSONArray(catalogo).toString();

        return catalogoJSON;
    }
}
