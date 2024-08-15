package br.edu.ifrs.riogrande;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean jogarNovamente = true;

        while (jogarNovamente) {
            System.out.println("Bem-vindo ao Jogo da Forca!");
            System.out.println("1. Iniciar Jogo");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // nextInt() deixa uma linha em baixo, precisa desta linha para evitar bugs.

            switch (opcao) {
                case 1:
                    Menu.iniciarJogo(scanner);
                    break;
                case 2:
                    jogarNovamente = false;
                    System.out.println("Obrigado por jogar!");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }

}
