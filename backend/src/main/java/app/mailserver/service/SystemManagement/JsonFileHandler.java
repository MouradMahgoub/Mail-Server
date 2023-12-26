package app.mailserver.service.SystemManagement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import app.mailserver.models.UserModel;

public class JsonFileHandler {
    private static final String usersDataFilePath =  Paths.get("").toAbsolutePath().resolve("backend/src/main/resources/users").toString();    
  
    public static boolean isUserExist(String emailAddress){
      String userfile=usersDataFilePath+"/"+emailAddress+".json";
      File file=new File(userfile);
      return file.exists();
    }
    
    //read from all_usersfile    
    public static UserModel fetchUser(String emailAddress){
     
      String userfile=usersDataFilePath+'/'+emailAddress+".json";
      ObjectMapper objectmapper=new ObjectMapper();
      try{
          File file=new File(userfile);
        UserModel usersData=objectmapper.readValue(file, new TypeReference<UserModel>(){});
        
        return usersData;
      }
      catch(Exception e){
        return new UserModel();
          // e.printStackTrace();
      }
    } 
   
    //write in all_usersfile
     public static void writeUserModel(UserModel updatedList) throws IOException{
      String userfile=usersDataFilePath+"/"+updatedList.getEmailAddress()+".json";
      ObjectMapper objectmapper=new ObjectMapper();
        // System.out.println("Values read from Json");
        //   for(var x:updatedList){
        //       System.out.println(x.toString());
        //   }
        objectmapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectmapper.writeValue(new File(userfile), updatedList);
     } 

}
