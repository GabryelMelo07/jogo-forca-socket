package br.edu.ifrs.riogrande.entity;

public class Palavra {
    private Integer id;
    private String conteudo;
    private String dica;

    public Palavra(){
    }

    public Palavra(Integer id, String conteudo, String dica) {
        this.id = id;
        this.conteudo = conteudo;
        this.dica = dica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }    

    @Override
    public String toString() {
        return "Palavra [conteudo=" + conteudo + ", dica=" + dica + "]";
    }
}
