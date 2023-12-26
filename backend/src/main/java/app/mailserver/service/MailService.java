package app.mailserver.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import app.mailserver.models.MailModel;
import app.mailserver.models.UserModel;
import app.mailserver.service.SystemManagement.SystemFolders;


@Service
public class MailService {
   private SystemFolders systemFolders; // Field to store the singleton instance

   public MailService() {
       this.systemFolders = SystemFolders.getInstance(); // Initialize the singleton instance
   }
  

   public UserModel sendEmail(MailModel newEmail) throws IOException{

       UserModel curUser=systemFolders.getCurUser();
       curUser.getFolders().addEmailTo("sentEmails",newEmail);
       systemFolders.updateUser(curUser);

       //we can make it as a queue 
       for(var x : newEmail.getTo()){

         if(systemFolders.isUserExist(x)){
            List<String>singleReceiver=new ArrayList<>();
            singleReceiver.add(x);
            MailModel sentMail=newEmail.clone();
            sentMail.setTo(singleReceiver);
            
            systemFolders.sendEmailTo(sentMail, x);
          }

       }
        return curUser;
   }

   public UserModel addToDraft(MailModel newEmail){
       UserModel curUser=systemFolders.getCurUser();
       curUser.getFolders().addEmailTo("draft",newEmail);
       systemFolders.updateUser(curUser);
      return curUser; 
   }
    
}