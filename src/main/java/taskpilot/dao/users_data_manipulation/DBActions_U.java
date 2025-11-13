package taskpilot.dao.users_data_manipulation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import taskpilot.dao.ConnectionBD;
import taskpilot.dao.tasks_data_manipulation.DBActions_T;
import taskpilot.model.Task;

public class DBActions_U {
   
   public static String _message;
   public static String _password;

   public static boolean executeDBCommand(String action, 
                                          String command, 
                                          String email, 
                                          String username, 
                                          String password, 
                                          int ID,
                                          HttpServletRequest request) throws ServletException, IOException {

      try (PreparedStatement stmt = ConnectionBD.connect().prepareStatement(command)) {
         switch (action) {
            case "CREATE" -> {
               stmt.setString(1, email);
               stmt.setString(2, password);
               stmt.setString(3, username);
               stmt.executeUpdate();
            }

            case "setCurrentUser" -> {
               stmt.setString(1, username);
               ResultSet rs = stmt.executeQuery();
               
               while (rs.next()) {
                  DBActions_T DBactsT = new DBActions_T();
                  HttpSession session = request.getSession();
                  int id_DB = rs.getInt("id");
                  String usernameDB = rs.getString("username");
                  String emailDB = rs.getString("email");
                  String passwordDB = rs.getString("password");
                  String id = Integer.toString(id_DB);
                  ArrayList<Task> list_T = DBactsT.SELECT(request, id_DB);
                  Map<String, Object> current_user = new HashMap<>();

                  current_user.put("_email", emailDB);
                  current_user.put("_username", usernameDB);
                  current_user.put("_password", passwordDB);
                  current_user.put("_id", id);

                  session.setAttribute("current_user", current_user);  
                  session.setAttribute("taskList", list_T); 
               }

               return true;
            }

            case "UPDATE" -> {
               stmt.setString(1, email);
               stmt.setString(2, password);
               stmt.setString(3, username);
               stmt.setInt(4, ID);
               stmt.executeUpdate();
            }

            case "DELETE" -> {
               stmt.setInt(1, ID);
            }

            case "SELECT" -> {
               stmt.setInt(1, ID);
               ResultSet rs = stmt.executeQuery();

               while(rs.next()) {
                  _password = rs.getString("password");
               }
            }
            default -> {
            }
         }

      return true;
      } catch (SQLException e) {
         e.printStackTrace();

         _message = e.getMessage();
         return false;
      }
   }

   public static boolean CREATE(String email,
                              String username, 
                              String password, 
                              HttpServletRequest request) throws ServletException, IOException {
      String command = "INSERT INTO Users (email, password, username) VALUES (?,?,?);";
      String action = "CREATE";

      return executeDBCommand(action, command, email, username, password, 0, request);
      }

   public static boolean SELECT(int ID, HttpServletRequest request) throws ServletException, IOException {
      String action = "SELECT";
      String command = "SELECT * from Users WHERE id = ?;";

      return executeDBCommand(action, command, "", "", "", ID, request);
   }

   public static boolean UPDATE(String email_, 
                                 String username_, 
                                 String password_, 
                                 HttpSession session,
                                 HttpServletRequest request) throws ServletException, IOException {
      Map<String, String> current_user = get_currentUserList(session);
      String email = email_.equals("") ? current_user.get("_email") : email_;
      String username = username_.equals("") ? current_user.get("_username") : username_;
      String password = password_.equals("") ? current_user.get("_password") : password_;

      String command = "UPDATE Users SET email = ?, password = ?, username = ? WHERE id = ?;";
      String action = "UPDATE";
      
      return executeDBCommand(action, command, email, username, password, Integer.parseInt(current_user.get("_id")), request);
   }

   public static boolean setCurrentUser(String email, 
                                       String username, 
                                       String password, 
                                       HttpServletRequest request) throws ServletException, IOException {
      String command = "SELECT * FROM Users WHERE username = ?;";
      String action = "setCurrentUser";
      return executeDBCommand(action, command, email, username, password, 0, request);
   }

   private static Map<String, String> get_currentUserList(HttpSession session){
      return (Map<String, String>) session.getAttribute("current_user");
   }

   public static String get_message(){
      return _message;
   }

   public static String get_password() {
      return _password;
   }
}