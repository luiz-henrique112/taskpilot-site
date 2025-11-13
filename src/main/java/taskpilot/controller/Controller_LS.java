package taskpilot.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import taskpilot.dao.users_data_manipulation.DBActions_U;
import taskpilot.dao.users_data_manipulation.DataDBValidation_U;
import taskpilot.utils.DataFormatValidation;


public class Controller_LS extends HttpServlet {  
   private static DataFormatValidation dataValidation = new DataFormatValidation();
   private static DataDBValidation_U DBvalidation = new DataDBValidation_U();
   private static DBActions_U DBactions = new DBActions_U();

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String screen = request.getServletPath();

      switch (screen) {
         case "/login" -> request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
         case "/signIn" -> request.getRequestDispatcher("/WEB-INF/views/signIn.jsp").forward(request, response);
         default -> {
         }
      }
   }
   @SuppressWarnings("static-access")
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String action = request.getParameter("action");
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      String email = request.getParameter("email");

      switch (action) {
         case "signIn" -> {
            if (dataValidation.validation(email, username, password)) {
               if (DBvalidation.isValid(email, username, password, action)) {
                  if (DBactions.CREATE(email, username, password, request)) {
                     if (DBactions.setCurrentUser(email, username, password, request)) {
                        response.sendRedirect("/taskpilot/tasks");
                     } else {
                        String errorMessage ="An unexpected error occurred while processing the request. Please try again later.";
                        response.sendRedirect("/taskpilot/signIn?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                     }
                  } else {
                     String errorMessage ="Your data could not be registered. Try again later.";
                     response.sendRedirect("/taskpilot/signIn?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                  }
               } else {
                  String errorMessage = "Users with this E-mail or username already registered.";
                  response.sendRedirect("/taskpilot/signIn?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
               }
            } else {
               String errorMessage = "Make sure the email is in a valid format, the username is at least 4 characters long, and the password is at least 8 characters long.";
               response.sendRedirect("/taskpilot/signIn?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
            }
         }

         case "login" -> {
            if (dataValidation.validation(email, username, password)) {
               if (DBvalidation.isValid(email, username, password, action)) {
                  if (DBactions.setCurrentUser(email, username, password, request)) {
                     response.sendRedirect("/taskpilot/tasks");
                  } else {
                     String errorMessage ="An unexpected error occurred while processing the request. Please try again later.";
                     response.sendRedirect("/taskpilot/signIn?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                  }
               } else {
                  String errorMessage = "No users with this data found.";
                  response.sendRedirect("/taskpilot/login?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
               }
            } else {
               String errorMessage = "Make sure the email is in a valid format, the username is at least 4 characters long, and the password is at least 8 characters long.";
               response.sendRedirect("/taskpilot/signIn?error=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
            }
         }

         default -> {
         } 
      }
   }
}
