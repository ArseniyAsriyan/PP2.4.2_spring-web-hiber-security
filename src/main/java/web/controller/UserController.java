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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    String message = "Register new user";

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
        } catch (NullPointerException e) {
            name = "";
        }
        model.addAttribute("current_user", "Hello, " + name);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {

        if (roleService.findAll().isEmpty()) {
            roleService.add(new Role("ROLE_USER"));
            roleService.add(new Role("ROLE_ADMIN"));
        }
        if (userService.findByLogin("admin") == null) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleService.getByName("ROLE_ADMIN"));
            adminRoles.add(roleService.getByName("ROLE_USER"));
            User admin = new User("admin", "123", "admin", "admin", 30, "admin@mail.com", adminRoles);
            userService.update(admin);
        }
        return "login";
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String userPage(Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("current_user", userService.findByLogin(principal.getName()));
        return "user";
    }

    @PostMapping(value = "/user/deleteAcc")
    public String deleteUser(Principal principal) {
        userService.deleteById(userService.findByLogin(principal.getName()).getId());
        return "/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model) {

        model.addAttribute("userForm", new User());
        model.addAttribute("message", message);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("userForm") User userForm, ModelMap model) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByName("ROLE_USER"));

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            message = "Incorrect password input";
            return "redirect:/registration";
        }

        userForm.setRoles(roles);
        try {
            userService.update(userForm);
        } catch (Exception e) {
            message = "User with that login already exists";
            return "redirect:/registration";
        }

        return "redirect:/login";
    }


}