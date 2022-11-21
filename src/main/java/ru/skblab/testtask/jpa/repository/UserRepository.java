package ru.skblab.testtask.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skblab.testtask.jpa.entity.User;

import java.util.Optional;

// Использую паттерн Репозиторий,
// тк в JpaRepository помогает избежать написания огромного кол-ва кода (написание которого влечет за собой огромное кол-во багов)))
// и мне не нужно делать какие-то особо замудренные запросы.
// Многие любят над интерфейсами репозитория ставить аннотацию @Repository
// Но я не знаю, почему они так делают, ведь классы не наследуют аннотации от интерфейсов
// А саму @Repository нужно использовать при реализации ДАО паттерна,
// тк @Repository’s job is to catch persistence-specific exceptions and re-throw them as one of Spring’s unified unchecked exceptions.
// https://www.baeldung.com/java-dao-vs-repository
// https://www.baeldung.com/spring-component-repository-service
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

}
