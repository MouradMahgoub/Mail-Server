package app.mailserver.controller;

import app.mailserver.models.*;
import app.mailserver.service.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin
@RequestMapping
public class ContactController {
    @Autowired
    private ContactsService contactService;
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostMapping("/addContact")
    public List<ContactModel> addContact(@RequestBody RequestObject params) {
       
        return contactService.addContact(ToContactModel(params));
    }

    @DeleteMapping("/deleteContact")

    public List<ContactModel> deleteCotact(@RequestBody RequestObject params) {
        return contactService.deleteContact(ToContactModel(params));

    }

    @PostMapping("/searchContact")
    public List<ContactModel> search(@RequestBody RequestObject params) {
      
        return contactService.searchContacts((String)params.get("contactName"));
    }

    @PostMapping("/editContact")
    public List<ContactModel> editContact(@RequestBody RequestObject params) {

        return contactService.editContact((String)params.get("oldName"),ToContactModel(params));
    }
    @GetMapping("/getContacts")
    public List<ContactModel> getContacts(){
        return contactService.getContacts();
    }

    public ContactModel ToContactModel(RequestObject params){
       Map<String, Object> reqContact = (Map<String, Object>) params.get("contact");
       return objectMapper.convertValue(reqContact, ContactModel.class);
    }
 }

