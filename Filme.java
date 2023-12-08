package org.example;
import org.json.JSONObject;

public class Filme {
    private String titulo;
    private String diretor;
    private int ano;
    private int duracao;
    private String genero;
    private int classificacao;
    private String descricao;

    public Filme(String titulo, String diretor, int ano, int duracao, String genero, int classificacao, String descricao) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.ano = ano;
        this.duracao = duracao;
        this.genero = genero;
        this.classificacao = classificacao;
        this.descricao = descricao;
    }

    public String toJson() {
        JSONObject filmeData = new JSONObject();
        filmeData.put("titulo", titulo);
        filmeData.put("diretor", diretor);
        filmeData.put("ano", ano);
        filmeData.put("duracao", duracao);
        filmeData.put("genero", genero);
        filmeData.put("classificacao", classificacao);
        filmeData.put("descricao", descricao);
        return filmeData.toString();
    }

    public static Filme fromJson(String jsonStr) {
        JSONObject filmeData = new JSONObject(jsonStr);
        return new Filme(
                filmeData.getString("titulo"),
                filmeData.getString("diretor"),
                filmeData.getInt("ano"),
                filmeData.getInt("duracao"),
                filmeData.getString("genero"),
                filmeData.getInt("classificacao"),
                filmeData.getString("descricao")
        );
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public int getAno() {
        return ano;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public String getDescricao() {
        return descricao;
    }
}
