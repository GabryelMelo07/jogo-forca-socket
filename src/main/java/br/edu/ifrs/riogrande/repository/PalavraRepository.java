package br.edu.ifrs.riogrande.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PalavraRepository {

    private final Connection conexao;
    private final String tableName;

    public PalavraRepository(Connection conexao, String tableName) {
        this.conexao = conexao;
        this.tableName = tableName;
    }

    public String findPalavraById(int id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("conteudo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
