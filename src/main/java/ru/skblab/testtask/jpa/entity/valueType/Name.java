package ru.skblab.testtask.jpa.entity.valueType;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Name {
    String firstName;

    String lastName;

    String patronymic;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", lastName, firstName, patronymic);
    }
}
