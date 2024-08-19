package br.edu.ifrs.riogrande;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.edu.ifrs.riogrande.database.DatabaseConnection;
import br.edu.ifrs.riogrande.entity.Jogador;
import br.edu.ifrs.riogrande.entity.Palavra;
import br.edu.ifrs.riogrande.repository.PalavraRepository;

public class Menu {

    public static void iniciarJogo(Jogador jogador1, Jogador jogador2) throws SQLException, IOException {
        Connection conexao = DatabaseConnection.getConnection();
        PalavraRepository palavraRepository = new PalavraRepository(conexao, "palavras");

        Random random = new Random();

        Palavra palavra = palavraRepository.findPalavraById(random.nextInt(1, 51));
        String palavraSecreta = palavra.getConteudo().toUpperCase();
        System.out.println(palavraSecreta);

        char[] palavraAdivinhada = new char[palavraSecreta.length()];

        Arrays.fill(palavraAdivinhada, '_');

        for (int i = 0; i < palavraAdivinhada.length; i++) {
            palavraAdivinhada[i] = '_';
        }

        int tentativasRestantes = 6;
        boolean palavraCompletada = false;
        Set<Character> letrasTentadas = new HashSet<>();

        Jogador jogadorAtual = random.nextInt(2) == 0 ? jogador1 : jogador2;
        Jogador proximoJogador = jogadorAtual == jogador1 ? jogador2 : jogador1;

        while (tentativasRestantes >= 0 && !palavraCompletada) {
            jogador1.enviarMensagem("DICA: " + palavra.getDica().toUpperCase(), jogador2);
            
            String exibirPalavra = IntStream.range(0, palavraAdivinhada.length)
                    .mapToObj(i -> String.valueOf(palavraAdivinhada[i]))
                    .collect(Collectors.joining(" "));

            if (tentativasRestantes == 0) {
                jogadorAtual.enviarMensagens(exibirForca(tentativasRestantes), exibirPalavra, "Fim de jogo, você perdeu.");
                proximoJogador.enviarMensagens(exibirForca(tentativasRestantes), exibirPalavra, "Fim de jogo, você perdeu.");
                return;
            }

            jogadorAtual.enviarMensagens(exibirForca(tentativasRestantes), exibirPalavra, "Tentativas restantes: " + tentativasRestantes, "Digite uma letra: ");
            proximoJogador.enviarMensagens(exibirForca(tentativasRestantes), exibirPalavra, "Tentativas restantes: " + tentativasRestantes);

            String input = jogadorAtual.lerMensagem().toUpperCase();

            if (input.length() == 0 || input.length() > 1) {
                jogadorAtual.enviarMensagem("Entrada inválida. Tente novamente.");
                continue;
            }
            
            char letra = input.charAt(0);

            if (letrasTentadas.contains(letra)) {
                jogadorAtual.enviarMensagem("Você já tentou a letra " + letra + ". Tente outra.");
                continue;
            }

            letrasTentadas.add(letra);
            boolean acertou = false;

            for (int i = 0; i < palavraSecreta.length(); i++) {
                if (palavraSecreta.charAt(i) == letra) {
                    palavraAdivinhada[i] = letra;
                    acertou = true;
                }
            }

            if (!acertou) {
                tentativasRestantes--;
            }

            palavraCompletada = String.valueOf(palavraAdivinhada).equals(palavraSecreta);

            if (palavraCompletada) {
                jogadorAtual.enviarMensagem("Parabéns! Você adivinhou a palavra: " + palavraSecreta);
                proximoJogador.enviarMensagem(String.format("Jogador %s, adivinhou a palavra: %s.\nVocê perdeu, boa sorte na próxima!", jogadorAtual.getNome(), palavraSecreta));
            } else if (tentativasRestantes == 0) {
                jogadorAtual.enviarMensagem("Você perdeu! A palavra era: " + palavraSecreta);
                proximoJogador.enviarMensagem("Jogo encerrado, não houve vencedores.\nA palavra era: "+ palavraSecreta);
            }

            Jogador jogadorTemporario = jogadorAtual;
            jogadorAtual = proximoJogador;
            proximoJogador = jogadorTemporario;
        }

        conexao.close();
        return;
    }

    private static String exibirForca(int erros) {
        String forca = "";

        switch (erros) {
            case 6:
                forca = """
                         _______
                         |     |
                         |    ---
                         |
                         |
                         |
                         |
                        ---
                         """;
                break;
            case 5:
                forca = """
                         _______
                         |     |
                         |    ---
                         |     O
                         |
                         |
                         |
                        ---
                         """;
                break;
            case 4:
                forca = """
                         _______
                         |     |
                         |    ---
                         |     O
                         |     |
                         |
                         |
                        ---
                         """;
                break;
            case 3:
                forca = """
                         _______
                         |     |
                         |    ---
                         |     O
                         |    /|
                         |
                         |
                        ---
                         """;
                break;
            case 2:
                forca = """
                         _______
                         |     |
                         |    ---
                         |     O
                         |    /|\\
                         |
                         |
                        ---
                         """;
                break;
            case 1:
                forca = """
                         _______
                         |     |
                         |    ---
                         |     O
                         |    /|\\
                         |    /
                         |
                        ---
                         """;
                break;
            case 0:
                forca = """
                         _______
                         |     |
                         |    ---
                         |     O
                         |    /|\\
                         |    / \\
                         |
                        ---
                         """;
                break;
            default:
                System.out.println("Quantidade de erros inválida.");
                break;
        }

        return forca;
    }

}
