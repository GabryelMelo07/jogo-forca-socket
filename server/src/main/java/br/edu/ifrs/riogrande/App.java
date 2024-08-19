package br.edu.ifrs.riogrande;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import br.edu.ifrs.riogrande.entity.Jogador;

public class App {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        @SuppressWarnings("all") // Suprimindo o warning pois o servidor não será fechado (server.close())
        ServerSocket server = new ServerSocket(10000);

        System.out.println("Servidor rodando, aguardando jogadores...");

        while (true) {
            Socket socketJogador1 = server.accept();
            System.out.println("Jogador 1 conectado.");
    
            PrintWriter saidaJogador1 = new PrintWriter(socketJogador1.getOutputStream(), true);
            saidaJogador1.println("Seja bem vindo ao jogo da forca online.\nAguardando oponente...");
            
            Socket socketJogador2 = server.accept();
            System.out.println("Jogador 2 conectado.");
            
            PrintWriter saidaJogador2 = new PrintWriter(socketJogador2.getOutputStream(), true);
            saidaJogador2.println("Seja bem vindo ao jogo da forca online.\nA partida já vai começar.");
            
            BufferedReader entradaJogador1 = new BufferedReader(new InputStreamReader(socketJogador1.getInputStream()));
            BufferedReader entradaJogador2 = new BufferedReader(new InputStreamReader(socketJogador2.getInputStream()));
    
            saidaJogador1.println("Informe seu nickname: ");
            String nomeJogador1 = entradaJogador1.readLine();
            Jogador jogador1 = new Jogador(nomeJogador1, saidaJogador1, entradaJogador1);
    
            saidaJogador2.println("Informe seu nickname: ");
            String nomeJogador2 = entradaJogador2.readLine();
            Jogador jogador2 = new Jogador(nomeJogador2, saidaJogador2, entradaJogador2);
            
            for (int i = 5; i > 0 ; i--) {
                System.out.println("Jogo iniciará em: " + i);
                jogador1.enviarMensagem("Jogo iniciará em: " + i, jogador2);
                Thread.sleep(1000);
            }
            
            boolean jogarNovamente = true;
            
            while (jogarNovamente) {
                Menu.iniciarJogo(jogador1, jogador2);
    
                jogador1.enviarMensagens("Jogar novamente? S/N", "Digite uma letra:");
                boolean respostaJogador1 = jogador1.lerMensagem().trim().toUpperCase().equals("S");
    
                jogador2.enviarMensagens("Jogar novamente? S/N", "Digite uma letra:");
                boolean respostaJogador2 = jogador2.lerMensagem().trim().toUpperCase().equals("S");
                
                if (!respostaJogador1 || !respostaJogador2) {
                    jogarNovamente = false;
    
                    if (!respostaJogador1) {
                        jogador2.enviarMensagem("Jogador " + jogador1.getNome() + " desistiu do jogo.");
                    } else if (!respostaJogador2) {
                        jogador1.enviarMensagem("Jogador " + jogador2.getNome() + " desistiu do jogo.");
                    }
                }
    
                if (jogarNovamente == true) {
                    jogador1.enviarMensagem("Novo jogo começando!", jogador2);
                }
            }
    
            jogador1.enviarMensagem("Jogo encerrado, obrigado por jogar!", jogador2);
            
            socketJogador1.close();
            socketJogador2.close();
    
            System.out.println("Preparando para aceitar novos jogadores...");
        }
        
    }

}
