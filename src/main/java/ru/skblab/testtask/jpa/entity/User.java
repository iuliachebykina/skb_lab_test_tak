package ru.skblab.testtask.jpa.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;
import ru.skblab.testtask.jpa.entity.valueType.Name;

import javax.persistence.*;

@Table(schema = "test_task", name = "users")
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
@Where(clause="deleted=false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String login;

    String email;

    String password;

    @Embedded
    Name name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_id", referencedColumnName = "id")
    UserVerification verification;

    // Я решила добавить soft deletion, осуществляется оно с помощью @Where(clause="deleted=false"). Удобно аудита и
    // для восстановления удаленных записей. Из базы никогда ничего не удаляется.
    //
    // Но могут начаться проблемы со связями в удаленных сущностях, потому что в таких случаях дочерние сущности и сами связи не удаляются,
    // Для примера возьмем отношение oneToMany Article - Comments (текущий пример не очень подходит для описания проблемы):
    // если удалить Article (deleted=false), а после добавить Comment, ссылающийся на удаленный Article и позже обратиться к нему (с ленивой загрузкой),
    // то вылетит ошибка EntityNotFoundException
    //
    // Если же использовать жадную загрузку, то вообще мы без проблем получим доступ к этому удаленному Article
    // (информация взята отсюда https://habr.com/ru/company/haulmont/blog/579386/)
    //
    //
    // Про эти проблемы я написала просто для понимания о том, что я не считаю мягкое удаления прекрасной идеей, без проблем в реализации,
    // но как хорошо сказано в приведенной выше статье, это бизнес операция и решать, связанные с ней проблемы, надо так же
    // (если брать пример, то делать проверки перед вставкой Comment или при мягком удалении Article так же удалять и Comments)
    @JsonIgnore
    @Builder.Default
    Boolean deleted = Boolean.FALSE;
}