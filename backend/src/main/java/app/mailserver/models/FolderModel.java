package app.mailserver.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FolderModel {

    private String name;
    private List <MailModel> emails;
    private List<FolderModel> subFolders;
    
    
    public List<FolderModel> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<FolderModel> subFolders) {
        this.subFolders = subFolders;
    }

    public void SetName(String name ){
        this.name=name;
    }

    public List<MailModel> getEmails() {
        return this.emails;
    }
    public String getName() {
        return this.name;
    }    
    public FolderModel(){
        this.subFolders=new ArrayList<>();
    }

    public FolderModel(String name, List<MailModel> emails, List<FolderModel> subFolders) {
        this.name = name;
        this.emails = emails;
        this.subFolders = subFolders;
    }

    public void addEmail(MailModel newEmail){
        emails.add(0, newEmail);
    }

    public void deleteEmail( MailModel deletedEmail){
       System.out.println(deletedEmail);
        emails.remove(deletedEmail);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FolderModel)) {
            return false;
        }
        FolderModel folderModel = (FolderModel) o;
        return Objects.equals(name, folderModel.name) && Objects.equals(emails, folderModel.emails) && Objects.equals(subFolders, folderModel.subFolders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, emails, subFolders);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", emails='" + getEmails() + "'" +
            ", subFolders='" + getSubFolders() + "'" +
            "}";
    }
    
    // public List <MailModel> filterEmails(filterParameter){
       
    // }
    // public void sortEmails(sortParameter){
       
    // }
    // public void searchEmails(sortParameter){
       
    // }
   
}
