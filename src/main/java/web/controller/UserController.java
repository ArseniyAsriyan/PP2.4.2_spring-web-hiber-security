package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getUsername(Principal principal, ModelMap model) {
        String name;
        try {
            name = principal.getName();
        } catch (NullPointerException e){
            name = "";
        }
        model.addAttribute("current_user", "Hello, " + name);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {

        if(roleService.findAll().isEmpty()){
            roleService.add(new Role("ROLE_USER"));
            roleService.add(new Role("ROLE_ADMIN"));
        }
        return "login";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user){
        return "admin/user-create";
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String userPage(Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("current_user", userService.findByLogin(principal.getName()));
        return "user";
    }

    @PostMapping(value = "/user/deleteAcc")
    public String deleteUser(Principal principal){
        userService.deleteById(userService.findByLogin(principal.getName()).getId());
        return "/login";
    }




}