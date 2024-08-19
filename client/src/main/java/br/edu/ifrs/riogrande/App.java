package br.edu.ifrs.riogrande;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
    private static final String SERVIDOR = "localhost";
    private static final int PORTA = 10000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVIDOR, PORTA);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            String mensagem;

            while ((mensagem = entrada.readLine()) != null) {
                System.out.println(mensagem);

                if (mensagem.contains("Digite uma letra:") || mensagem.contains("Informe seu nickname:")) {
                    String input = teclado.readLine();
                    saida.println(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
