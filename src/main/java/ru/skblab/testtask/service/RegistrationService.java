package ru.skblab.testtask.service;

import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.exeption.EmailExistException;
import ru.skblab.testtask.exeption.LoginExistException;

public interface RegistrationService {
    /**
     * Регистрирует пользователя в БД
     *
     * @param userRegistrationInfo - информация о пользователе о регистрации
     * @throws LoginExistException - пользователь с таким логином уже существует
     * @throws EmailExistException - пользователь с такой почтой уже существует
     */
    void registerUser(UserRegistrationInfo userRegistrationInfo) throws LoginExistException, EmailExistException;
}
