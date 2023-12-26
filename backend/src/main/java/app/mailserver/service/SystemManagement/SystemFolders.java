package app.mailserver.service.SystemManagement;
import app.mailserver.models.MailModel;
import app.mailserver.models.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SystemFolders {
    private static SystemFolders instance; 
    private static  UserModel curUser;


    private SystemFolders() {}

    public static  SystemFolders getInstance() {
        if (instance == null) {
            instance = new SystemFolders();
        }
        return instance;
    }

    public  UserModel getCurUser() {
            return curUser;
        }

    public  Map<String, Object> signUp(String name,String emailAddress, String password) throws IOException{
       
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("isValid",false);
        response.put("message","done");


        if(name.equals("")){
            response.put("message","please enter user name!");
                return response;
        }   
        else if(JsonFileHandler.isUserExist(emailAddress)){
            response.put("message","email address is already registered!");
                return response;
        }
        else if(!isValidEmail(emailAddress)){
            response.put("message","email format is not valid!");
             return response;
        }
        else if(password.length()<6){
        response.put("message","password is too short! (enter at least 6 chars)");
                return response;
        }
        else{
        response.put("isValid",true);
        UserModel newUser=new UserModel(name,emailAddress,password);
     
        curUser=newUser;
        JsonFileHandler.writeUserModel(curUser);
        
        return response; 
        }
    }

    public  Map<String, Object> loginChecker(String emailAddress,String password){
        
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("isValid",false);
        response.put("message","done");

        if(!JsonFileHandler.isUserExist(emailAddress)){
         response.put("message","user not found!");
            return response;
        }
        else{
            UserModel tempUser= JsonFileHandler.fetchUser(emailAddress);
             if(tempUser.getPassword().equals(password)){
                curUser= tempUser;
                response.put("isValid",true);
                 return response;
             }
             else{
               response.put("message","password is not correct!");
               return response;
             }
        }
    
    }
    
    public  void updateUser(UserModel changedUser) {
       try {
           JsonFileHandler.writeUserModel(changedUser);
       } catch (Exception e) {
        
       }
    }
   
    public  boolean isUserExist(String emailAddress) {
        return JsonFileHandler.isUserExist(emailAddress);
    }
    
    public  void sendEmailTo(MailModel email ,String receiverAddress) throws IOException{
           
          UserModel receiver= JsonFileHandler.fetchUser(receiverAddress);
          receiver.getFolders().addEmailTo("inbox", email); 
          JsonFileHandler.writeUserModel(receiver);
    }

    public  boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+\\.com)$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }
}
