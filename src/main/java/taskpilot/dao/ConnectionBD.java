package taskpilot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
   private static final String URL = "jdbc:mysql://localhost:3306/taskpilotdb";
   private static final String USER = "root";
   private static final String PASSWORD = "@Luiz09072009";

   public static Connection connect() {
      try {
         return DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (SQLException e) {
         e.printStackTrace();
         throw new RuntimeException("Erro ao conectar ao banco de dados", e);
      }
   }
}
