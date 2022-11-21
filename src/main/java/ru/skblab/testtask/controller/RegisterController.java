package ru.skblab.testtask.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.exeption.EmailExistException;
import ru.skblab.testtask.exeption.LoginExistException;
import ru.skblab.testtask.service.RegistrationService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterController {

    final RegistrationService registrationService;
    @Value("${app.register.message.email-existing}")
    String emailExistingMessage;
    @Value("${app.register.message.login-existing}")
    String loginExistingMessage;


    // для формы регистрации использовала Spring MVC и шаблонизатор Thymeleaf

    // тк благодаря Spring Boot и Spring MVC для создания контроллера мне необходимо всего лишь
    // создать класс с аннотацией @Controller, метод с несколькими аннотациями в зависимости от запроса,
    // выполнить нужную мне логику для заполнения модели
    // и вернуть из метода строку с именем нужного HTML шаблона для отображения


    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/success")
    public String successRegistration() {
        return "success";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserRegistrationInfo user = new UserRegistrationInfo();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserRegistrationInfo userRegistrationInfo,
                               BindingResult result,
                               Model model) {

        try {
            registrationService.registerUser(userRegistrationInfo);
        } catch (LoginExistException e) {
            result.rejectValue("login", null,
                    loginExistingMessage);
        } catch (EmailExistException e) {
            result.rejectValue("email", null,
                    loginExistingMessage);
        }

        if(result.hasErrors()){
            model.addAttribute("user", userRegistrationInfo);
            return "/register";
        }

        return "redirect:/success";
    }

}
