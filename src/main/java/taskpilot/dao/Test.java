package taskpilot.dao;

import java.sql.SQLException;

public class Test {
   public static void main(String[] args) throws SQLException {
      try {
         if (ConnectionBD.connect() != null) {
            System.out.println("funciona");
         } else {
            System.out.println("não funciona");
         }
      } catch (RuntimeException e) {
         System.out.println(e);
      }
   }
}
