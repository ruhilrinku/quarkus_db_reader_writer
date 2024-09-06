package org.acme.employer.adapters.database.reader;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class EmployerReaderDatabaseRepository implements PanacheRepositoryBase<EmployerReaderDataModel, UUID> {

    public String getName() {
        return "Reader Repo";
    }
}
