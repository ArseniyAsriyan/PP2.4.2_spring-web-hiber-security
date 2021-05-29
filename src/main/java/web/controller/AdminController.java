package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private Long newUserId;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/admin/user-list";
    }


    @GetMapping("/admin/user-create")
    public String createUserForm(User user){
        return "/admin/user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(User user) {
        Set<Role> setRoles = new HashSet<>();
        setRoles.add(roleService.getByName("ROLE_USER"));
        User temp = new User(
                user.getLogin(), user.getPassword(),
                user.getName(), user.getSurname(),
                user.getAge(), user.getEmail(),
                setRoles);

        userService.saveUser(temp);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(User user, long id) {
        userService.update(id, user);
        return "redirect:/admin/";
    }

}
