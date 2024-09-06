package org.acme.employer.domain.model;

import lombok.*;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDomainModel {

    private UUID id;
    private String code;
    private String name;
    private Boolean isActive;

    public void setIsActive(Boolean isActive) {
        this.isActive = Objects.requireNonNullElse(isActive, true);
    }
}
