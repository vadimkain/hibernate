package com.kainv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @Embeddable говорит о том, что это встраиваемый компонент
@Embeddable
public class PersonalInfo {
    private String firstname;
    private String lastname;
    //    @Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
}
