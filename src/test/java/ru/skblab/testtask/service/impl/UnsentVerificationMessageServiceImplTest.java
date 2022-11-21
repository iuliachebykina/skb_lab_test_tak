package ru.skblab.testtask.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.skblab.testtask.dto.NameInfo;
import ru.skblab.testtask.jpa.entity.User;
import ru.skblab.testtask.jpa.entity.UserVerification;
import ru.skblab.testtask.jpa.entity.valueType.Name;
import ru.skblab.testtask.service.UserVerificationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
class UnsentVerificationMessageServiceImplTest {
    @MockBean
    UserVerificationService userVerificationService;

    @Autowired
    UnsentVerificationMessageServiceImpl unsentVerificationMessageService;
    final static NameInfo name = NameInfo.builder()
            .firstName("Юлия")
            .lastName("Чебыкина")
            .patronymic("Владимировна")
            .build();

    final static String email = "iulia@gmail.ru";

    final static String login = "iulia";
    final static String password = "qwerty";

    final static UserVerification userVerification = new UserVerification();




    final static User user = new User(2L,
            login,
            email,
            password,
            new Name(name.getFirstName(), name.getLastName(), name.getLastName()),
            userVerification,
            false);




    @Test
    public void findAllUserIdWithUnsentVerificationMessageWithEmptyList() {
        when(userVerificationService.findUserWithUnsentVerificationMessage()).thenReturn(List.of());
        List<Long> allUserIdWithUnsentVerificationMessage = unsentVerificationMessageService.findAllUserIdWithUnsentVerificationMessage();
        assertEquals(0, allUserIdWithUnsentVerificationMessage.size());
    }

    @Test
    public void findAllUserIdWithUnsentVerificationMessageWithNotEmptyList() {
        userVerification.setUser(user);
        userVerification.setIsVerificationMessageSending(false);
        when(userVerificationService.findUserWithUnsentVerificationMessage()).thenReturn(List.of(userVerification));
        List<Long> allUserIdWithUnsentVerificationMessage = unsentVerificationMessageService.findAllUserIdWithUnsentVerificationMessage();
        assertEquals(1, allUserIdWithUnsentVerificationMessage.size());
        assertEquals(user.getId(), allUserIdWithUnsentVerificationMessage.get(0));
    }

}