package ru.skblab.testtask.service;

import java.util.List;

public interface UnsentVerificationMessageService {
    /**
     * Поиск пользователей, по которым не произведена верификация
     *
     * @return - id пользователей, по которым не произведена верификация
     */
    List<Long> findAllUserIdWithUnsentVerificationMessage();
}

