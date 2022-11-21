package ru.skblab.testtask.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.skblab.testtask.dto.*;
import ru.skblab.testtask.exeption.UserNotFoundException;
import ru.skblab.testtask.jpa.entity.User;
import ru.skblab.testtask.jpa.entity.UserVerification;
import ru.skblab.testtask.jpa.entity.valueType.Name;
import ru.skblab.testtask.service.MessagingService;
import ru.skblab.testtask.service.NotificationService;
import ru.skblab.testtask.service.UserService;
import ru.skblab.testtask.service.UserVerificationService;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;



@SpringBootTest
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
class VerificationServiceImplTest {

    @MockBean
    MessagingService messagingService;
    @MockBean
    NotificationService notificationService;
    @MockBean
    UserVerificationService userVerificationService;
    @MockBean
    UserService userService;

    @Autowired
    VerificationServiceImpl verificationService;

    final static UserVerifiedAnswerMessage unsuccessfullyAnswer = UserVerifiedAnswerMessage.builder()
            .isVerified(false)
            .build();

    final static UserVerifiedAnswerMessage successfullyAnswer = UserVerifiedAnswerMessage.builder()
            .isVerified(true)
            .build();

    final static UserVerification userVerification = new UserVerification();


    final static NameInfo name = NameInfo.builder()
            .firstName("Юлия")
            .lastName("Чебыкина")
            .patronymic("Владимировна")
            .build();

    final static String email = "iulia@gmail.ru";

    final static String login = "iulia";
    final static String password = "qwerty";


    final static User user = new User(2L,
            login,
            email,
            password,
            new Name(name.getFirstName(), name.getLastName(), name.getLastName()),
            userVerification,
            false);

    @Test
    public void userNotFoundException(){
        when(userService.getUser(1L)).thenReturn(Optional.empty());
        UndeclaredThrowableException exception = assertThrows(UndeclaredThrowableException.class, () -> verificationService.verifyUser(1L));
        assertEquals(exception.getCause().getClass(), UserNotFoundException.class);

    }

    @Test
    public void notifyUserWithThrowTest() throws TimeoutException {
        Long userId = 2L;

        when(userService.getUser(userId)).thenReturn(Optional.of(user));
        Mockito.doThrow(new TimeoutException("Timeout")).when(messagingService).doRequest(any());
        verificationService.verifyUser(userId);

        Mockito.verify(userVerificationService, Mockito.times(1)).setVerificationMessageStatus(userId, false);
        Mockito.verify(userVerificationService, Mockito.times(0)).setVerificationMessageStatus(userId, true);

        Mockito.verify(userVerificationService, Mockito.times(0)).setVerificationResult(eq(userId), any());
        Mockito.verify(notificationService, Mockito.times(0)).notifyUserAboutVerification(userId);


    }

    @Test
    public void notifyUserWithoutThrowTest() throws TimeoutException {
        Long userId = 2L;
        when(messagingService.doRequest(any())).thenReturn(unsuccessfullyAnswer);

        when(userService.getUser(userId)).thenReturn(Optional.of(user));
        verificationService.verifyUser(userId);

        Mockito.verify(userVerificationService, Mockito.times(0)).setVerificationMessageStatus(userId, false);
        Mockito.verify(userVerificationService, Mockito.times(1)).setVerificationMessageStatus(userId, true);
        Mockito.verify(userVerificationService, Mockito.times(1)).setVerificationResult(eq(userId), any());
        Mockito.verify(notificationService, Mockito.times(1)).notifyUserAboutVerification(userId);


    }

    @Test
    public void unsuccessfullyVerifiedTest() throws TimeoutException {
        Long userId = 2L;

        when(messagingService.doRequest(any())).thenReturn(unsuccessfullyAnswer);
        when(userService.getUser(userId)).thenReturn(Optional.of(user));

        verificationService.verifyUser(userId);
        Mockito.verify(userVerificationService, Mockito.times(1)).setVerificationResult(userId, false);


    }

    @Test
    public void successfullyVerifiedTest() throws TimeoutException {
        Long userId = 2L;

        when(messagingService.doRequest(any())).thenReturn(successfullyAnswer);
        when(userService.getUser(userId)).thenReturn(Optional.of(user));

        verificationService.verifyUser(userId);
        Mockito.verify(userVerificationService, Mockito.times(1)).setVerificationResult(userId, true);
    }


}