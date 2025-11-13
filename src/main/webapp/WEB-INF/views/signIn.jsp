<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <head>
      <title>Sign In</title>
      <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Inter:wght@400&family=Poppins:wght@400&display=swap" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/assets/css/style-register.css" rel="stylesheet">
   </head>
   <body>
      <div class="container">
         <p class="welcome-text">Welcome to</p>
         <form action="/taskpilot/Controller_LS" method="post">
            <div class="container-center">
               <h1>TaskPilot</h1>
               <input type="hidden" name="action" value="signIn">

               <input type="text" class="input-field" placeholder="E-mail" id="email" name="email" required>
               <div class="passwordInputContainer">
                  <input type="password" class="input-field" placeholder="Password" id="password" name="password" minlength="8" required>
                  <p class="helper-text">(at least 8 characters)</p>
               </div>
               <input type="text" class="input-field" placeholder="Username" id="username" name="username" minlength="4" required>
            </div>
            <div class="buttons">
               <button type="submit" class="button" id="signIn">Sign In</button>
               <div class="links">
                  <a href="${pageContext.request.contextPath}/login" id="loginLink">Do you already have an account?</a>
                  <a href="${pageContext.request.contextPath}/redefinition" id="redefinitionLink">Forgot your password?</a>
               </div>
            </div>
         </form>
      </div>
   </body>
</html>
