package ru.skblab.testtask.common;

import org.mapstruct.Mapper;
import ru.skblab.testtask.dto.NameInfo;
import ru.skblab.testtask.dto.UserRegistrationInfo;
import ru.skblab.testtask.jpa.entity.valueType.Name;

@Mapper(componentModel = "spring")
public interface NameMapper {

    Name mapUserRegistrationInfoToName(UserRegistrationInfo user);
    NameInfo mapNameToNameInfo(Name name);
}
