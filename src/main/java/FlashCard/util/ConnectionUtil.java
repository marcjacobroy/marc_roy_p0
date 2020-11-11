package FlashCard.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Connection factory
public class ConnectionUtil {
	
	public Connection createConnection() throws SQLException {
		
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?", "postgres", "Pr1648%%");
		
		return connection;
		
	}
}
