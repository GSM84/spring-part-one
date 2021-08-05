package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final RoleRepository roleRepository;

    private final UserService userService;

    @Autowired
    public RegisterController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String registerNewUser(Model model){
        logger.info("New user registration started");

        model.addAttribute("roles", roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet()));
        model.addAttribute("user", new UserDto());

        return "registration_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        logger.info("Going to save new registered user");

        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet()));
            return "registration_form";
        }

        if (!user.getPassword().equals(user.getRepeatPassword())) {
            result.rejectValue("repeatPassword", "", "Repeated password doesn't match with password or empty.");
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet()));
            return "registration_form";
        } else if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            result.rejectValue("password", "", "Password can't be empty.");
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet()));
            return "registration_form";
        }

        userService.save(user);
        return "redirect:/login";
    }

}
