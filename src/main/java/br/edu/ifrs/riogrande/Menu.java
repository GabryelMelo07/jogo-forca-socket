package br.edu.ifrs.riogrande;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.edu.ifrs.riogrande.database.DatabaseConnection;
import br.edu.ifrs.riogrande.repository.PalavraRepository;

public class Menu {

    public static void iniciarJogo(Scanner scanner) throws SQLException {
        Connection conexao = DatabaseConnection.getConnection();
        PalavraRepository palavraRepository = new PalavraRepository(conexao, "palavras");

        Random random = new Random();

        String palavraSecreta = palavraRepository.findPalavraById(random.nextInt(1, 100)).toUpperCase();
        System.out.println(palavraSecreta);

        char[] palavraAdivinhada = new char[palavraSecreta.length()];

        Arrays.fill(palavraAdivinhada, '_');

        for (int i = 0; i < palavraAdivinhada.length; i++) {
            palavraAdivinhada[i] = '_';
        }

        int tentativasRestantes = 6;
        boolean palavraCompletada = false;
        Set<Character> letrasTentadas = new HashSet<>();

        while (tentativasRestantes >= 0 && !palavraCompletada) {
            String exibirPalavra = IntStream.range(0, palavraAdivinhada.length)
                    .mapToObj(i -> String.valueOf(palavraAdivinhada[i]))
                    .collect(Collectors.joining(" "));

            if (tentativasRestantes == 0) {
                System.out.println(exibirForca(tentativasRestantes));
                System.out.println(exibirPalavra);
                System.out.println("Fim de jogo, você perdeu.");
                return;
            }

            System.out.println(exibirForca(tentativasRestantes));
            System.out.println(exibirPalavra);

            System.out.println("\nTentativas restantes: " + tentativasRestantes);
            System.out.print("Digite uma letra: ");

            String input = scanner.nextLine().toUpperCase();

            if (input.length() == 0 || input.length() > 1) {
                System.out.println("Entrada inválida. Tente novamente.");
                continue;
            }
            
            char letra = input.charAt(0);

            if (letrasTentadas.contains(letra)) {
                System.out.println("Você já tentou a letra " + letra + ". Tente outra.");
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
                System.out.println("Parabéns! Você adivinhou a palavra: " + palavraSecreta);
            } else if (tentativasRestantes == 0) {
                System.out.println("Você perdeu! A palavra era: " + palavraSecreta);
            }
        }

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
