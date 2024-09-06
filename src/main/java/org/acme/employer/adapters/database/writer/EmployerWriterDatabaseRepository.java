package org.acme.employer.adapters.database.writer;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class EmployerWriterDatabaseRepository implements PanacheRepositoryBase<EmployerWriterDataModel, UUID> {

    public String getName() {
        return "Writer Repo";
    }
}
