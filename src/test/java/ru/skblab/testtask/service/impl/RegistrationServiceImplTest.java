package ru.skblab.testtask.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.exeption.EmailExistException;
import ru.skblab.testtask.exeption.LoginExistException;
import ru.skblab.testtask.jpa.entity.User;
import ru.skblab.testtask.jpa.entity.UserVerification;
import ru.skblab.testtask.jpa.entity.valueType.Name;
import ru.skblab.testtask.jpa.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



@SpringBootTest
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
class RegistrationServiceImplTest {

    @MockBean
    UserRepository userRepository;
    @MockBean
    PasswordEncoder passwordEncoder;


    @Autowired
    RegistrationServiceImpl registrationService;
    final static String email = "iulia@gmail.com";
    final static String login = "iulia";
    final static String password = "qwerty";




    final static UserVerification userVerification = new UserVerification();


    final static UserRegistrationInfo userRegistrationInfo = UserRegistrationInfo.builder()
            .login(login)
            .password(password)
            .email(email)
            .lastName("Чебыкина")
            .firstName("Юлия")
            .patronymic("Владимировна")
            .build();

    final static User userWithSameLogin = new User(1L,
            login,
            "smth else",
            password,
            new Name(userRegistrationInfo.getFirstName(), userRegistrationInfo.getLastName(), userRegistrationInfo.getLastName()),
            userVerification,
            false);

    final static User userWithSameEmail = new User(1L,
            "smth else",
            email,
            password,
            new Name(userRegistrationInfo.getFirstName(), userRegistrationInfo.getLastName(), userRegistrationInfo.getLastName()),
            userVerification,
            false);

    @Test
    public void successRegisterUserTest() throws EmailExistException, LoginExistException {
        when(passwordEncoder.encode(userRegistrationInfo.getPassword())).thenReturn(userRegistrationInfo.getPassword() + "salt))");
        registrationService.registerUser(userRegistrationInfo);
    }

    @Test
    public void unsuccessfulRegisterUserWithEmailExistingTest() {
        when(passwordEncoder.encode(userRegistrationInfo.getPassword())).thenReturn(userRegistrationInfo.getPassword() + "salt))");
        when(userRepository.findByEmail(userRegistrationInfo.getEmail())).thenReturn(Optional.of(userWithSameEmail));
        assertThrows(EmailExistException.class, () -> registrationService.registerUser(userRegistrationInfo));
    }

    @Test
    public void unsuccessfulRegisterUserWithLoginExistingTest() {
        when(passwordEncoder.encode(userRegistrationInfo.getPassword())).thenReturn(userRegistrationInfo.getPassword() + "salt))");
        when(userRepository.findByLogin(userRegistrationInfo.getLogin())).thenReturn(Optional.of(userWithSameLogin));
        assertThrows(LoginExistException.class, () -> registrationService.registerUser(userRegistrationInfo));
    }

}