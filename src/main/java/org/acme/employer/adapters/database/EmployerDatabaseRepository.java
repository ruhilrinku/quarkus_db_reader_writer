package org.acme.employer.adapters.database;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class EmployerDatabaseRepository implements PanacheRepositoryBase<EmployerDataModel, UUID> {

    public String getName() {
        return "Writer Repo";
    }
}
