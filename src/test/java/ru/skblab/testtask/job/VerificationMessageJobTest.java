package ru.skblab.testtask.job;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skblab.testtask.service.UnsentVerificationMessageService;
import ru.skblab.testtask.service.VerificationService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
class VerificationMessageJobTest {

    @MockBean
    VerificationService verificationService;
    @MockBean
    UnsentVerificationMessageService unsentVerificationMessageService;
    @Autowired
    VerificationMessageJob verificationMessageJob;


    @Test
    public void sendAllUnsentVerificationMessagesTest() {
        when(unsentVerificationMessageService.findAllUserIdWithUnsentVerificationMessage()).thenReturn(List.of(1L, 2L, 3L));
        verificationMessageJob.sendAllUnsentVerificationMessages();
        Mockito.verify(verificationService, Mockito.times(3)).verifyUser(any());
    }
}