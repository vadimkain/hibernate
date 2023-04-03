package com.kainv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @Embeddable говорит о том, что это встраиваемый компонент
@Embeddable
public class PersonalInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2824366793058175795L;

    private String firstname;
    private String lastname;
    //    @Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
}
