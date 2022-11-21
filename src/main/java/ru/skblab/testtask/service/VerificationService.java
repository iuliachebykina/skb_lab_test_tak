package ru.skblab.testtask.service;

public interface VerificationService {
    /**
     * Верификация пользователя
     *
     * @param userId - id пользователя
     */
    void verifyUser(Long userId);
}
