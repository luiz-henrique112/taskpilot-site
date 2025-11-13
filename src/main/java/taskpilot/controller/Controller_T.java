package taskpilot.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import taskpilot.dao.tasks_data_manipulation.DBActions_T;
import taskpilot.dao.users_data_manipulation.DBActions_U;
import taskpilot.model.Task;
import taskpilot.utils.DataFormatValidation;

public class Controller_T extends HttpServlet {
   private static  DBActions_T DBAction = new DBActions_T();
   private static  DBActions_U DBActionU = new DBActions_U();
   private static DataFormatValidation dataValidation = new DataFormatValidation();

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String screen = request.getServletPath();
      HttpSession session = request.getSession();
      
      if (session != null) {
         session.setMaxInactiveInterval(40 * 3600); 
      } else {
         response.sendRedirect("/taskpilot/login?message=session+expired");
      }
      
      switch (screen) {
         case "/tasks" -> request.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(request, response);
         default -> {
         }
      }
   }
   @SuppressWarnings("static-access")
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
      
      String action = request.getParameter("action");
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      String term = request.getParameter("term");  
      String status = request.getParameter("status");
      String task_ID = request.getParameter("taskID");
      String email = request.getParameter("email");  
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      HttpSession session = request.getSession();
      
      if (session != null) {
         switch (action) {
            case "add_task" -> {
               if (DBAction.CREATE(name, description, term, status, request)){
                  Map<String, String> current_userData = get_currentUserList(session);
                  int userID = Integer.parseInt(current_userData.get("_id"));
                  String message = "Task added successfully!";
                  resetList(request, response, session, userID, message);
               } else {
                  response.sendRedirect("/taskpilot/tasks?message=task20%creation20%NOT20%SUCCESSFUL");
               }
            }

            case "edit-task__data" -> {
               if(DBAction.UPDATE(name, description, term, status, Integer.parseInt(task_ID), request)){
                  Map<String, String> current_userData = get_currentUserList(session);
                  int userID = Integer.parseInt(current_userData.get("_id"));

                  String message = "This task's data editing was successfully made!";
                  resetList(request, response, session, userID, message);
               } else {
                  response.sendRedirect("/taskpilot/tasks?message=task20%editing20%NOT20%SUCCESSFUL");
               }
            }

            case "edit-user_data" -> {
               if (dataValidation.validation(email, username, password)) {
                  if (DBActionU.UPDATE(email, username, password, session, request)) {
                     if (username.equals("")) {
                        Map<String, String> current_user = get_currentUserList(session);
                        DBActionU.setCurrentUser(email, current_user.get("_username"), password, request);
                     } else {
                        DBActionU.setCurrentUser(email, username, password, request);
                     }

                     String successMessage = "Your user's data was edited successfully!";
                     response.sendRedirect("/taskpilot/tasks?message=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
                  } else {
                     String successMessage = DBActionU.get_message();
                     response.sendRedirect("/taskpilot/tasks?message=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
                  }
               } else {
                  String successMessage = "Make sure the email is in a valid format, the username is at least 4 characters long, and the password is at least 8 characters long.";
                  response.sendRedirect("/taskpilot/tasks?message=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
               }
            }

            case "delete-task" -> {
               if (DBAction.DELETE(Integer.parseInt(task_ID), request)) {
                  String successMessage = "Task deleted successfully!";
                  Map<String, String> current_user = get_currentUserList(session);
                  resetList(request, response, session, Integer.parseInt(current_user.get("_id")), successMessage);
               } else {
                  String successMessage = DBActionU.get_message();
                  response.sendRedirect("/taskpilot/tasks?message=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
               }
            }

            case "verify-password" -> {
               Map<String, String> current_user = get_currentUserList(session);
               if(DBActionU.SELECT(Integer.parseInt(current_user.get("_id")), request)){
                  String _password = DBActionU.get_password();

                  if (password.equals(_password)) {
                     response.sendRedirect("/taskpilot/tasks?isPasswordValid=true");
                  } else {
                     response.sendRedirect("/taskpilot/tasks?message=Oops!20%something20%went20%wrong");
                  }
               } else {
                  response.sendRedirect("/taskpilot/tasks?message=Oops!20%something20%went20%wrong");
               }
            }
         
            default -> {
            }
         }
      } else {
         response.sendRedirect("/taskpilot/login?message=session+expired");
      }
   }

   private void resetList(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    HttpSession session, 
                                    int userID,
                                    String message) throws IOException {
      ArrayList<Task> list;
      
      try {
            list = DBAction.SELECT(request, userID);
            session.setAttribute("taskList", list);                  
            response.sendRedirect("/taskpilot/tasks?message=" + java.net.URLEncoder.encode(message, "UTF-8"));
      } catch (ServletException | IOException | SQLException e) {                  
         response.sendRedirect("/taskpilot/tasks?message=Oops!20%something20%went20%wrong");
         e.printStackTrace();
      }
   }

   private Map<String, String> get_currentUserList(HttpSession session){
      return (Map<String, String>) session.getAttribute("current_user");
   }
}
