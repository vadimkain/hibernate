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
    private String username;
    private String firstname;
    private String lastname;
//    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthDate;
    @Type(type = "kainv")
    private String info;
    @Enumerated(EnumType.STRING)
    private Role role;
}
