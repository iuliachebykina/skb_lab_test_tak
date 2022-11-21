package ru.skblab.testtask.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.exeption.EmailExistException;
import ru.skblab.testtask.exeption.LoginExistException;
import ru.skblab.testtask.jpa.entity.User;
import ru.skblab.testtask.jpa.entity.UserVerification;
import ru.skblab.testtask.jpa.entity.valueType.Name;
import ru.skblab.testtask.jpa.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
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


    final static UserRegistrationInfo USER_REGISTRATION_INFO = UserRegistrationInfo.builder()
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
            new Name(USER_REGISTRATION_INFO.getFirstName(), USER_REGISTRATION_INFO.getLastName(), USER_REGISTRATION_INFO.getLastName()),
            userVerification,
            false);

    final static User userWithSameEmail = new User(1L,
            "smth else",
            email,
            password,
            new Name(USER_REGISTRATION_INFO.getFirstName(), USER_REGISTRATION_INFO.getLastName(), USER_REGISTRATION_INFO.getLastName()),
            userVerification,
            false);

    @Test
    public void successRegisterUserTest() throws EmailExistException, LoginExistException {
        when(passwordEncoder.encode(USER_REGISTRATION_INFO.getPassword())).thenReturn(USER_REGISTRATION_INFO.getPassword() + "salt))");
        registrationService.registerUser(USER_REGISTRATION_INFO);
    }

    @Test
    public void unsuccessfulRegisterUserWithEmailExistingTest() {
        when(passwordEncoder.encode(USER_REGISTRATION_INFO.getPassword())).thenReturn(USER_REGISTRATION_INFO.getPassword() + "salt))");
        when(userRepository.findByEmail(USER_REGISTRATION_INFO.getEmail())).thenReturn(Optional.of(userWithSameEmail));
        assertThrows(EmailExistException.class, () -> registrationService.registerUser(USER_REGISTRATION_INFO));
    }

    @Test
    public void unsuccessfulRegisterUserWithLoginExistingTest() {
        when(passwordEncoder.encode(USER_REGISTRATION_INFO.getPassword())).thenReturn(USER_REGISTRATION_INFO.getPassword() + "salt))");
        when(userRepository.findByLogin(USER_REGISTRATION_INFO.getLogin())).thenReturn(Optional.of(userWithSameLogin));
        assertThrows(LoginExistException.class, () -> registrationService.registerUser(USER_REGISTRATION_INFO));
    }

}