package app.mailserver.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Builder;
import java.util.Objects;


public class MailModel implements Cloneable {
    private String from;
    private List<String> to;//change it to reciver in all program 
    private String subject;
    private String body;
    private String attachment;
    private String importance;
    private String date=new String();
    
    
    public List<String> getTo() {
        return to;
    }

    public MailModel() {
      
    }

    @Builder
    public MailModel(List<String> to, String from, String subject, String body, String attachment, String importance, String date) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.attachment = attachment;
        this.importance = importance;
        this.date = date;
    }

    @Override
    public MailModel clone() {
        try {
            return (MailModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
    }
    public String getAttachment() {
        return this.attachment;
    }

    public void setattachment(String attachment) {
        this.attachment = attachment;
    }

    public String getfrom() {
        return this.from;
    }

    public void setfrom(String from) {
        this.from = from;
    }

    

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getsubject() {
        return this.subject;
    }

    public void setsubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public String getDate() {
        return date; 
    }
  
    public void setDate(String date) {
        this.date=date;
    }
    public void setnewDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        date=LocalDateTime.now().format(formatter);
    }

  

    public String getImportance() {
        return this.importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

 

    // public MailModel(String attachment, String from, List<String> to, List<String> recivers, String subject, String body, LocalDateTime date, String type, String importance) {
    //     this.attachment = attachment;
    //     this.from = from;
    //     this.to = to;
    //     this.subject = subject;
    //     this.body = body;
    //     this.date = date;
        
    //     this.importance = importance;
    // }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MailModel)) {
            return false;
        }
        MailModel mailModel = (MailModel) o;
        return Objects.equals(attachment, mailModel.attachment) && Objects.equals(from, mailModel.from) && Objects.equals(to, mailModel.to) &&  Objects.equals(subject, mailModel.subject) && Objects.equals(body, mailModel.body) && Objects.equals(date, mailModel.date) && Objects.equals(importance, mailModel.importance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachment, from, to, subject, body, date, importance);
    }

    @Override
    public String toString() {
        return "{" +
        " from='" + getfrom() + "'" +
        ", to='" + getTo() + "'" +
        ", subject='" + getsubject() + "'" +
        ", body='" + getBody() + "'" +
        ", attachment='" + getAttachment() + "'" +
        ", importance='" + getImportance() + "'" +
        ", date='" + getDate() + "'" +
            "}";
    }

 
   
}

