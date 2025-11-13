package taskpilot.model;

public class User {

   String username;
   String email;
   String password;

   public User(String email, String password, String username){
      this.email = email;
      this.password = password;
      this.username = username;
   }

   public String getEmail(){
      return this.email;
   }
   public String getPassword(){
      return this.password;
   }
   public String getUsername(){
      return this.username;
   }
}
