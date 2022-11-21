package ru.skblab.testtask.service;

import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.exeption.EmailExistException;
import ru.skblab.testtask.exeption.LoginExistException;

public interface RegistrationService {
    void registerUser(UserRegistrationInfo userRegistrationInfo) throws LoginExistException, EmailExistException;
}
