package fun.mysticlands.task.controller;

import fun.mysticlands.task.model.User;
import fun.mysticlands.task.repository.UserRepository;
import fun.mysticlands.task.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private UserRepository userRepository;
    public void UserService(UserService service){
        this.service =service;
    }


    @PostMapping("/new-user")
    public String newUser(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String role, Model model){
        User user = new User();
        user.setLogin(username);
        user.setPassword(password);
        user.setName(name);
        user.setRoles(role);
        service.addUser(user);
        if(user != null) {
            model.addAttribute("task-name", "Все ок");
        }
        return "new-user";
    }
    @GetMapping("/reg")
    public String reg(){
        return "new-user";
    }
}
