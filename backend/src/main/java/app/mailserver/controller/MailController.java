package app.mailserver.controller;


import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.mailserver.models.MailModel;
import app.mailserver.models.RequestObject;
import app.mailserver.service.MailService;

@RestController
@CrossOrigin
@RequestMapping
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private ObjectMapper objectMapper;

    @PutMapping("/sendEmail")
    public String sendEmail(@RequestBody RequestObject params) throws IOException {
        
       Map<String, Object> emailParams = (Map<String, Object>) params.get("email");
       MailModel newEmail = objectMapper.convertValue(emailParams, MailModel.class);

      newEmail.setnewDate();
      mailService.sendEmail(newEmail);
      return "done";
        
    }
    

    @PutMapping("/toDraft")
    public String draft(@RequestBody RequestObject params) {

       Map<String, Object> emailParams = (Map<String, Object>) params.get("email");
       MailModel newEmail = objectMapper.convertValue(emailParams, MailModel.class);
       
      newEmail.setnewDate();
      mailService.addToDraft(newEmail);
     return "done"; 
        
    }
}
