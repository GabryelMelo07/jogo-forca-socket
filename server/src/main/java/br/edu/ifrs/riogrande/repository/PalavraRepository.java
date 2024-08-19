package br.edu.ifrs.riogrande.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.ifrs.riogrande.entity.Palavra;

public class PalavraRepository {

    private final Connection conexao;
    private final String tableName;

    public PalavraRepository(Connection conexao, String tableName) {
        this.conexao = conexao;
        this.tableName = tableName;
    }

    public Palavra findPalavraById(int id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();

            Palavra p = new Palavra();
            
            if (resultSet.next()) {
                p.setConteudo(resultSet.getString("conteudo"));
                p.setDica(resultSet.getString("dica"));
            }

            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
