<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Inter:wght@400&family=Poppins:wght@400&display=swap" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/assets/css/style-tasks.css" rel="stylesheet">
      <title>Tasks</title>
   </head>
   <body>
      <div class="container">
         <!-- Barra superior fixa -->
         <div class="top-bar">
            <div>Hello, ${sessionScope.current_user["_username"]}</div>
            <a href="${pageContext.request.contextPath}/login"><button class="logout-btn" title="Logout">⎋</button></a>
         </div>

         <header>
            <h1>Tasks</h1>
            <div class="icons">
               <button title="Add Task" class="top-btns" id="btn-plus">+</button>
               <!-- <button title="Statistics" class="top-btns">&#128202;</button> -->
               <button title="Settings" class="top-btns" id="settings">&#9881;</button>
            </div>
         </header>

         <div class="form-background invisible" id="form-background">
            <form action="${pageContext.request.contextPath}/Controller_T" class="form-addTask" method="post">
               <div class="form-container">
                  <input type="hidden" name="action" value="add_task">

                  <div class="btn-remove-container">
                     <div class="fa fa-remove fa-2x btn-remove" id="btn-remove-form"></div>
                     <p class="form-upper-text">Add a new task</p>
                  </div>
                  
                  <div class="input-label">
                     <label for="name">Name:</label>
                     <input type="text" name="name" maxlength="75" required>
                  </div>
                  <div class="input-label">
                     <label for="description">Description:</label>
                     <input type="text" name="description" maxlength="375">
                  </div>
                  <div class="input-label">
                     <label for="term">Deadline:</label>
                     <input type="date" name="term" id="term-input">
                  </div>

                  <div class="btn">
                     <button type="submit" id="submit-button" class="submit_button">Add Task</button>
                  </div>
               </div>
            </form>
         </div>

            <div class="task-list">
               <c:forEach var="task" items="${taskList}">
                  <div class="task-card" id="${task.taskID}">
                     <p class="status">${task.status}</p>
                     <p class="name">${task.name}</p>
                     <p class="description">${task.description}</p>
                     <p class="term">${task.term}</p>
                     <button class="menu-icon" title="Options">⋮</button>
                  </div>
               </c:forEach>
            </div>

            <div class="task-data-background invisible" id="ETD_background">
               <form action="${pageContext.request.contextPath}/Controller_T" class="form-EditTask" id="form-EditTask" method="post">
                  <div class="task-data__container" >
                     <input type="hidden" name="action" value="edit-task__data" id="actionInput">
                     <input type="hidden" name="taskID" id="taskID">
                     <input type="hidden" name="list" id="list">
   
                     <div class="btn-remove-container">
                        <div class="fa fa-remove fa-2x btn-remove" id="btn-remove-form-ETD"></div>
                        <p class="form-upper-text">Edit this task's data</p>
                     </div>
                  
                     <div id="task-data__name" class="input-label">
                        <label for="name"> Name: </label>
                        <br>
                        <input type="text" name="name" placeholder="Type here the new name of this task" id="input_name">
                     </div>
                     <div id="task-data__description" class="input-label">
                        <label for="description"> Description: </label>
                        <br>
                        <input type="text" name="description" placeholder="Type here the new description of this task" id="input_desc">
                     </div>
                     <div id="task-data__term" class="input-label">
                        <label for="term"> Term: </label>
                        <br>
                        <input type="date" name="term" id="input_term">
                     </div>
                     <div id="task-data__status" class="input-label">
                        <label for="status">New status:</label>
                        <br>
                        <select name="status" id="input_status">
                           <option value="Pending"> Pending </option>
                           <option value="On progress"> On progress </option>
                           <option value="Finished"> Finished </option>
                        </select>
   
                        <div class="btn">
                           <button type="submit" id="submit-button_ETD" class="submit_button">Save Changes</button>
                           <button type="submit" id="submit-button_DT" class="submit_button">Delete task</button>
                        </div>
                     </div>
                  </div>
               </form>
            </div>
         </div>


         <div class="user-data-background invisible" id="settings-background">
            <form action="${pageContext.request.contextPath}/Controller_T" class="form-settings" method="post">
               <div class="form-container">
                  <input type="hidden" name="action" value="edit-user_data">

                  <div class="btn-remove-container">
                     <div class="fa fa-remove fa-2x btn-remove" id="btn-remove-settings"></div>
                     <p class="form-upper-text">Edit your data</p>
                  </div>
                  
                  <div class="input-label">
                     <label for="email">Email:</label>
                     <input type="email" name="email" >
                  </div>
                  <div class="input-label">
                     <label for="username">Username:</label>
                     <input type="text" name="username">
                  </div>
                  <div class="input-label">
                     <label for="password">New Password:</label>
                     <input type="password" name="password">
                  </div>

                  <div class="btn">
                     <button type="submit" id="submit-button-UDE" class="submit_button">Save Changes</button>
                  </div>
               </div>
            </form>
         </div>

         <div class="password-verifying__background invisible" id="password-verifyingBackground">
            <form action="${pageContext.request.contextPath}/Controller_T" method="post" class="form-PV" id="form-PV">
               <input type="hidden" name="action" value="verify-password">
               <div class="form-container">

                  <div class="btn-remove-container">
                     <div class="fa fa-remove fa-2x btn-remove" id="btn-remove-PV"></div>
                     <p class="form-upper-text">Verify your password</p>
                  </div>

                  <div class="input-label">
                     <label for="password">Password:</label>
                     <input type="password" name="password">
                  </div>

                  <div class="btn-PV">
                     <button type="submit" id="submit-button-PV" class="submit_button">Verify</button>
                  </div>
                  
               </div>
            </form>
         </div>
      </div>

      <script src="${pageContext.request.contextPath}/assets/js/script-task.js"></script>
      <%
         String isPasswordValid = (String) request.getParameter("isPasswordValid");
         String errorMessage = (String) request.getParameter("message");
         if (errorMessage != null) {
      %>
         <script>
            window.onload = function () {
               alert("<%= errorMessage %>");
            };
         </script>
      <%
         }

         if (isPasswordValid != null){
            %> 
            <script>
               document.getElementById("password-verifyingBackground").classList.add("invisible")
               document.getElementById("settings-background").classList.remove("invisible")
            </script>
            <%
         }
      %>
   </body>
</html>
