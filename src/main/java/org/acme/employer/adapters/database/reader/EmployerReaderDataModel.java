package org.acme.employer.adapters.database.reader;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "employer_test")
@Getter
@Setter
@NoArgsConstructor
public class EmployerReaderDataModel implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid-generator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid-generator", type = UUIDGenerator.class)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault(value = "true")
    private Boolean isActive;

    @Column(name = "deleted_id")
    private UUID deletedId;
}
