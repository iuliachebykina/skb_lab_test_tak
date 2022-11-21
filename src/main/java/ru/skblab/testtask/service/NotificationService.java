package ru.skblab.testtask.service;

public interface NotificationService {
    /**
     * Отправляет уведомление о верификации
     *
     * @param userId - id пользователя
     */
    void notifyUserAboutVerification(Long userId);
}
