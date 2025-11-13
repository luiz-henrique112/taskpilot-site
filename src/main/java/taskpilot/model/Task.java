package taskpilot.model;

public class Task {
   int taskID;
   String name;
   String description;
   String term;
   String status;

   public Task(int taskID, String name, String description, String term, String status){
      this.taskID = taskID;
      this.name = name;
      this.description = description;
      this.term = term;
      this.status = status;
   }

   public int getTaskID(){
      return this.taskID;
   }   

   public String getName(){
      return this.name;
   }   

   public String getDescription(){
      return this.description;
   }   

   public String getTerm(){
      return this.term;
   }   

   public String getStatus(){
      return this.status;
   }   
}
