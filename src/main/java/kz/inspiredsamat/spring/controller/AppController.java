package kz.inspiredsamat.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kz.inspiredsamat.spring.model.User;
import kz.inspiredsamat.spring.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("itransition")
@AllArgsConstructor
public class AppController {

    private final UserService userService;

    @GetMapping
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterationPage() {
        return "register";
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        userService.updateLoginTime();
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/block/{id}")
    public String block(@PathVariable Long id) {
        return userService.block(id);
    }

    @GetMapping("/unblock/{id}")
    public String unblock(@PathVariable Long id) {
        userService.unblock(id);
        return "redirect:/itransition/users";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User newUser, RedirectAttributes redirectAttributes) {
        userService.save(newUser);
        return "register_success";
    }
}