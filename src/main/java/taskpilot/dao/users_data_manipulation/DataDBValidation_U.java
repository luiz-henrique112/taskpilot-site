package taskpilot.dao.users_data_manipulation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import taskpilot.dao.ConnectionBD;
import taskpilot.model.User;

public class DataDBValidation_U {
   public static int CHECK_INVALID = 1;
   public static int CHECK_NO_RESULT = 2;
   public static int CHECK_SUCCESS = 3;

   static {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
   }
   
   public static boolean isValid(String email, String username, String password, String action) {
      int attempt = 1;
      ArrayList<User> list = new ArrayList<>();
      boolean retry = true;

      try {
         boolean isValid = false;
      
         while (retry) {
            String sql = SQLquery(attempt);

            try (PreparedStatement stmt = ConnectionBD.connect().prepareStatement(sql);            
                  ResultSet rs = stmt.executeQuery()) {
               list.clear();

               while (rs.next()) {
                  String emailDB = rs.getString("email");
                  String passwordDB = rs.getString("password");
                  String usernameDB = rs.getString("username");
                  
                  list.add(new User(emailDB, passwordDB, usernameDB));
               }

               int checkResult = listChecking(email, username, password, action, list);
               if (checkResult == CHECK_INVALID) {
                  isValid = false;
                  retry = false;
               } else if (checkResult == CHECK_SUCCESS) {
                  isValid = true;
                  retry = false;
               } else if (checkResult == CHECK_NO_RESULT) {
                  attempt++;
               }
            }
         }
         return isValid;
      } catch (SQLException e) {
         return false;
      }
   }

   private static int listChecking(String email, String username, String password, String action, ArrayList<User> list){
      boolean isThereNext = (list.size() == 3);
      boolean canSignUp = true;

      if (!list.isEmpty()) {
         for (User user : list) {
            switch (action) {
               case "login" -> {
                     if(email.equals(user.getEmail()) &&
                        password.equals(user.getPassword()) &&
                        username.equals(user.getUsername())){
                           return CHECK_SUCCESS;
                     } 
                  }

               case "signIn" -> {
                     if(email.equals(user.getEmail()) ||
                        username.equals(user.getUsername())){
                           return CHECK_INVALID;
                     } 
                     canSignUp = canSignUp && (!email.equals(user.getEmail()) && !username.equals(user.getUsername()));
                  }
            
               default -> {
                  }
            }
         }
      }

      if (action.equals("signIn") && canSignUp && !isThereNext) {
         return CHECK_SUCCESS; 
      }

      return isThereNext? CHECK_NO_RESULT : CHECK_INVALID;
   }
   
   private static String SQLquery(int attempt){
      String query = "SELECT * FROM Users LIMIT 3 OFFSET " + setOFFSETValue(attempt) + ";";
      return query;
   }

   private static int setOFFSETValue(int attempt){
      return (attempt * 3) - 3;
   }
}
