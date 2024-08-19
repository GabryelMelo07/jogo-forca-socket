package br.edu.ifrs.riogrande.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Jogador {
    private String nome;
    private PrintWriter saida;
    private BufferedReader reader;

    public String getNome() {
        return nome;
    }

    public Jogador(String nome, PrintWriter saida, BufferedReader reader) {
        this.nome = nome;
        this.saida = saida;
        this.reader = reader;
    }

    public void enviarMensagem(String msg) {
        this.saida.println(msg);
    }

    public void enviarMensagem(String msg, Jogador jogador2) {
        this.saida.println(msg);
        jogador2.enviarMensagem(msg);
    }

    public void enviarMensagens(String... msg) {
        for (String string : msg) {
            this.enviarMensagem(string);
        }
    }

    public String lerMensagem() throws IOException {
        return this.reader.readLine();
    }

    @Override
    public String toString() {
        return "Jogador [nome=" + nome + "]";
    }
}
