import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class Connect {
    static Connection connection;
    static public Statement statement;

    static public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

    Connect() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");

        Properties properties=new Properties();
        properties.setProperty("characterEncoding","utf8");
        properties.setProperty("user", "root");
        properties.setProperty("password", "qwerty");
        properties.setProperty("useSSL", "false");

        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/worldskills",properties);
        statement=connection.createStatement();
    }
}