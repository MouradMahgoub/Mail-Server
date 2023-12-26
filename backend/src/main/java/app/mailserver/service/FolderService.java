package app.mailserver.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import app.mailserver.models.FolderModel;
import app.mailserver.models.MailModel;
import app.mailserver.models.UserModel;
import app.mailserver.service.Sorting.EmailSort;
import app.mailserver.service.SystemManagement.SystemFolders;
import app.mailserver.service.Filter.EmailFilter;
import app.mailserver.service.Filter.EmailSearch;


@Service
public class FolderService {
   private SystemFolders systemFolders; // Field to store the singleton instance

   public FolderService() {
       this.systemFolders = SystemFolders.getInstance(); // Initialize the singleton instance
   }
  
    public Map<String, List<String>> addLabel(String labelName){
       UserModel curUserModel=systemFolders.getCurUser();
       curUserModel.getFolders().addLabel(labelName);

       systemFolders.updateUser(curUserModel);
       return getlabelsNames(curUserModel.getFolders().getLabels());
    } 
    
    public Map<String, List<String>> renameLabel(String oldName,String newName){
       UserModel curUserModel=systemFolders.getCurUser();
       curUserModel.getFolders().renameLabel(oldName, newName);
       systemFolders.updateUser(curUserModel);
       return getlabelsNames(curUserModel.getFolders().getLabels());
    }
    
    public Map<String, List<String>> deleteLabel(String labelName){
       UserModel curUserModel=systemFolders.getCurUser();
       curUserModel.getFolders().deleteLabel(labelName);
       systemFolders.updateUser(curUserModel);
       return getlabelsNames(curUserModel.getFolders().getLabels());
    } 
   
    public List<MailModel> deleteEmails(List<MailModel> emails,String from){
       
      UserModel curUserModel=systemFolders.getCurUser();
      curUserModel.getFolders().deleteEmails(emails,from);
      systemFolders.updateUser(curUserModel);
      return curUserModel.getFolders().findFolder(from).getEmails();
    } 
    
    public List<MailModel> moveEmailsFromTo(List<MailModel> emails,String from,String to){
      UserModel curUserModel=systemFolders.getCurUser();
      curUserModel.getFolders().moveEmailsFromTo(emails, from, to);
      systemFolders.updateUser(curUserModel);
      return curUserModel.getFolders().findFolder(from).getEmails();
    }
    
    public List<MailModel> filterEmails(String folderName,String subjectFilter,String senderFilter,String sortLogic,String searchWord){
   
         List<MailModel> folderEmails=systemFolders.getCurUser().getFolders().findFolder(folderName).getEmails();
        
         List<MailModel> filteredEmails =EmailFilter.filterEmails(folderEmails, subjectFilter, senderFilter);
         
         filteredEmails=EmailSearch.searchEmails(filteredEmails, searchWord);

         filteredEmails=EmailSort.SortEmails(filteredEmails, sortLogic);
      
     return filteredEmails;
   }
   
    public List<MailModel> getEmails(String folderName){
      return systemFolders.getCurUser().getFolders().findFolder(folderName).getEmails();
   }
   
   public Map<String, List<String>> getlabelsNames(List<FolderModel> labels) {
      Map<String, List<String>> labelsNames = new HashMap<>();
      List<String> namesList = new ArrayList<>();
      
      for (var x : labels) {
          namesList.add(x.getName());
      }
      labelsNames.put("labelsNames", namesList);
      
      return labelsNames;
  }
  
}