import com.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class ToDoRunner {
    public static void main(String[] args) {
        try (Connection open = ConnectionManager.open()) {
            open.getAutoCommit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
