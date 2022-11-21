package ru.skblab.testtask.job;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.skblab.testtask.service.NotificationService;
import ru.skblab.testtask.service.UnsentNotificationService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
class NotificationSendingJobTest {

    @MockBean
    NotificationService notificationService;
    @MockBean
    UnsentNotificationService unsentNotificationService;
    @Autowired
    NotificationSendingJob notificationSendingJob;

    @Test
    public void sendAllUnsentNotificationsTest() {
        when(unsentNotificationService.findAllUserIdWithUnsentNotification()).thenReturn(List.of(1L, 2L, 3L));
        notificationSendingJob.sendAllUnsentNotifications();
        Mockito.verify(notificationService, Mockito.times(3)).notifyUserAboutVerification(any());
    }
}