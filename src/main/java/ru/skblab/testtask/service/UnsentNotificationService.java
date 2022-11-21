package ru.skblab.testtask.service;

import java.util.List;

public interface UnsentNotificationService {
    /**
     * Поиск пользователей, по которым уже прошла верификация, но не было отправлено уведомление
     *
     * @return - id пользователей, которым не отправлено уведомление о верификации
     */
    List<Long> findAllUserIdWithUnsentNotification();
}

