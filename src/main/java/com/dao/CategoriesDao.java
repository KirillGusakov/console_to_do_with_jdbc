package com.dao;

import com.entity.CategoriesEntity;
import com.util.ConnectionManager;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class CategoriesDao {
    private static final CategoriesDao INSTANCE = new CategoriesDao();
    private static final String SAVE_SQL = """
                SELECT category_id,
                        category_name
                FROM to_do_repository.to_do_app.categories
                WHERE category_id = ?;
            """;

    private static final String SAVE_ALL_SQL = """
            SELECT category_id,
                    category_name
            FROM to_do_repository.to_do_app.categories
            """;


    public static Map<Integer, String> getHashMap() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_ALL_SQL)) {
            ResultSet resultSet = prepareStatement.executeQuery();

            Map <Integer, String> hashMap = new HashMap<>();

            while (resultSet.next()) {
                hashMap.put(resultSet.getInt("category_id"), resultSet.getString("category_name"));
            }

            return hashMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Optional<CategoriesEntity> save(int id) {
        try (Connection connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            CategoriesEntity categoriesEntity = null;
            if (resultSet.next()) {
                categoriesEntity = new CategoriesEntity(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );
            }
            return Optional.ofNullable(categoriesEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CategoriesDao() {
    }
}
