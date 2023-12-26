package app.mailserver.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.mailserver.models.UserModel;
import app.mailserver.service.SystemManagement.SystemFolders;

@Service
public class UserService {
    private UserModel curUser;

    private SystemFolders systemFolders; // Field to store the singleton instance

    public UserService() {
        this.systemFolders = SystemFolders.getInstance(); // Initialize the singleton instance
    }

    public Map<String, Object> login(String emailAddress, String password) {
       
        Map<String, Object> response = new HashMap<String, Object>();
        response=systemFolders.loginChecker(emailAddress, password);

        if ((boolean)response.get("isValid")) {

            this.curUser = systemFolders.getCurUser();
            return response;
        }
        return response;
    }

    public Map<String, Object> signUp(String name, String emailAddress, String password) throws IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        response=systemFolders.signUp(name, emailAddress, password);

        if ((boolean)response.get("isValid")) {

            curUser = systemFolders.getCurUser();
        }
        return response;
    }
   
    public void logOut() {

    }

}
