package br.edu.ifrs.riogrande.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Repository<T> {

    private final Connection connection;
    private final String tableName;

    public Repository(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
    protected abstract void mapEntityToStatement(T entity, PreparedStatement statement) throws SQLException;

    public Optional<T> findById(int id) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        }

        return Optional.empty();
    }

    public List<T> findAll() throws SQLException {
        List<T> entities = new ArrayList<>();

        String query = "SELECT * FROM " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        }

        return entities;
    }

    public void save(T entity, String insertQuery) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            mapEntityToStatement(entity, statement);
            statement.executeUpdate();
        }
    }

    public void update(T entity, String updateQuery) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            mapEntityToStatement(entity, statement);
            statement.executeUpdate();
        }
    }

    public void deleteById(int id, String tableName, String idColumn) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
