package com.eazyschool.entity;

import com.eazyschool.audit.Auditable;
import com.eazyschool.constants.HolidayType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "holidays")
public class Holiday extends Auditable<String> {

    @Id
    private String holiday;

    private String reason;

    @Enumerated(EnumType.STRING)
    private HolidayType type;
}
