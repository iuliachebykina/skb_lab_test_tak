package ru.skblab.testtask.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.skblab.testtask.aop.annotation.Loggable;
import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.exeption.EmailExistException;
import ru.skblab.testtask.exeption.LoginExistException;
import ru.skblab.testtask.service.RegistrationService;
import ru.skblab.testtask.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationServiceImpl implements RegistrationService {
    final UserService userService;

    @Override
    @Loggable
    public void registerUser(UserRegistrationInfo userRegistrationInfo) throws LoginExistException, EmailExistException {
        if (userService.isExistLogin(userRegistrationInfo.getLogin())) {
            throw new LoginExistException(userRegistrationInfo.getLogin());
        }

        if (userService.isExistEmail(userRegistrationInfo.getEmail())) {
            throw new EmailExistException(userRegistrationInfo.getEmail());
        }
        userService.createUser(userRegistrationInfo);
    }


}
