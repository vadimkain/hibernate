package com.kainv.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "kainv", typeClass = JsonBinaryType.class)
public class User {
    @Id
//    Указываем sequence генератор и саму стратегию
    @GeneratedValue(generator = "user_generator", strategy = GenerationType.TABLE)
//    указываем любое название sequence, само название sequence в БД и шаг инкремента (для SEQUENCE)
//    @SequenceGenerator(name = "user_generator", sequenceName = "users_id_seq", allocationSize = 1)
//    для TABLE
    @TableGenerator(
            name = "user_generator",
            table = "all_sequence",
            allocationSize = 1,
            pkColumnName = "table_name",
            valueColumnName = "pk_value"
    )
    private Long id;

    @Column(unique = true)
    private String username;
    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;
    @Type(type = "kainv")
    private String info;
    @Enumerated(EnumType.STRING)
    private Role role;
}
