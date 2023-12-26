package app.mailserver.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import app.mailserver.models.ContactModel;
import app.mailserver.models.UserModel;
import app.mailserver.service.SystemManagement.SystemFolders;
@Service
public class ContactsService {

  private SystemFolders systemFolders; // Field to store the singleton instance

  public ContactsService() {
      this.systemFolders = SystemFolders.getInstance(); // Initialize the singleton instance
  }
 
  public List<ContactModel> addContact(ContactModel newContact) {
    UserModel curUser=systemFolders.getCurUser();
    List<ContactModel> curContacts = curUser.getContacts();
    curUser.setContacts(curContacts);
    curContacts.add(newContact);
    sort();
    systemFolders.updateUser(curUser);
    return curUser.getContacts();
  }

  public List<ContactModel> deleteContact(ContactModel contact) {
    UserModel curUser=systemFolders.getCurUser();
    List<ContactModel> curContacts = curUser.getContacts();

    for (int i = 0; i < curContacts.size(); i++) {

      if (curContacts.get(i).getPhoneNums().get(0).equals(contact.getPhoneNums().get(0))
          && curContacts.get(i).getName().toLowerCase().equals(contact.getName().toLowerCase())) {

        curContacts.remove(i);
        break;
        
      }
    }
    curUser.setContacts(curContacts);
    systemFolders.updateUser(curUser);
    return curUser.getContacts();
    
  }
  public List<ContactModel> editContact(String oldName,ContactModel updatedContactModel){
    UserModel curUser=systemFolders.getCurUser();
    List<ContactModel> curContacts = curUser.getContacts();
    for(int i=0;i<curContacts.size();i++){
      if(curContacts.get(i).getName().equalsIgnoreCase(oldName)){
        curContacts.get(i).setName(updatedContactModel.getName());
        curContacts.get(i).setEmailAddresses(updatedContactModel.getEmailAddresses());
        curContacts.get(i).setPhoneNums(updatedContactModel.getPhoneNums());
        curContacts.get(i).setImportance(updatedContactModel.getImportance());
        break;
      }
    }
    curUser.setContacts(curContacts);
    sort();
    systemFolders.updateUser(curUser);
    return curUser.getContacts();


  }
  public List<ContactModel> searchContacts(String Name) {
    UserModel curUser=systemFolders.getCurUser();
    List<ContactModel> curContacts =new ArrayList<>();
    curContacts=curUser.getContacts();
    return curContacts.stream()
        .filter(contact -> contact.getName().toLowerCase().contains(Name.toLowerCase()))
        .collect(Collectors.toList());
  }
  

  public List<ContactModel> sort() {
    UserModel curUser=systemFolders.getCurUser();
    List<ContactModel> curContacts = curUser.getContacts();
    Collections.sort(curContacts, new Comparator<ContactModel>() {
      public int compare(ContactModel Contact1, ContactModel Contact2) {
        return Contact1.getName().toLowerCase().compareTo(Contact2.getName().toLowerCase());
      }
    });
    systemFolders.updateUser(curUser);
    return curUser.getContacts();
  }

  public  List<ContactModel> getContacts(){
     UserModel curUser=systemFolders.getCurUser();
     return curUser.getContacts();
  }

}
