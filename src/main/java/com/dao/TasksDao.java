package com.dao;

import com.entity.CategoriesEntity;
import com.entity.TasksEntity;
import com.util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public final class TasksDao {

    private static final TasksDao INSTANCE = new TasksDao();
    private static final String SELECT_SQL = """
            SELECT task_id,
                    task_name,
                    due_date,
                    category_id
            FROM to_do_repository.to_do_app.tasks
            WHERE task_id = ?;
            """;

    private static final String CREATE_SQL = """
            INSERT INTO to_do_repository.to_do_app.tasks (task_name, due_date, category_id) 
            VALUES (?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE to_do_repository.to_do_app.tasks
            SET
                task_name = ?,
                due_date = ?,
                category_id = ?
            WHERE task_id = ?  
            """;

    private static final String DELETE_SQL = """
            DELETE FROM to_do_repository.to_do_app.tasks
            WHERE task_id = ?
            """;

    public boolean delete(int id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TasksEntity create(TasksEntity tasksEntity) {

        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, tasksEntity.getTaskName());
            preparedStatement.setDate(2, Date.valueOf(tasksEntity.getDueDate()));
            preparedStatement.setInt(3, tasksEntity.getCategories().getCategoryId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                tasksEntity.setTaskId(generatedKeys.getInt("task_id"));
            }

            return tasksEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(TasksEntity tasks) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setInt(4, tasks.getTaskId());
            preparedStatement.setString(1, tasks.getTaskName());
            preparedStatement.setDate(2, Date.valueOf(tasks.getDueDate()));
            preparedStatement.setInt(3, tasks.getCategories().getCategoryId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<TasksEntity> save(int id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            TasksEntity tasksEntity = null;

            if (resultSet.next()) {
                tasksEntity = new TasksEntity(
                        resultSet.getInt("task_id"),
                        resultSet.getString("task_name"),
                        resultSet.getDate("due_date").toLocalDate(),
                        CategoriesDao.save(resultSet.getInt("category_id")).orElse(null)
                );

            }
            return Optional.ofNullable(tasksEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private TasksDao() {
    }

    public static TasksDao get() {
        return INSTANCE;
    }
}
