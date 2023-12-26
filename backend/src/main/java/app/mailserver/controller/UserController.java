package app.mailserver.controller;


import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import app.mailserver.models.RequestObject;
import app.mailserver.service.UserService;


@RestController
@CrossOrigin
@RequestMapping
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody RequestObject params) {

        // return userService.login(requestUser.emailAddress, requestUser.password);
        return userService.login((String)params.get("emailAddress"),(String)params.get("password"));

    }

    @PostMapping("/signUp")
    public Map<String, Object> signUp(@RequestBody RequestObject params) throws IOException {

        return userService.signUp((String)params.get("userName"), (String)params.get("emailAddress"),(String)params.get("password"));
    }
    
}
