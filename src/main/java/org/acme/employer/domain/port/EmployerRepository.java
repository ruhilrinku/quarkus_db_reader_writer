package org.acme.employer.domain.port;


import org.acme.employer.adapters.database.reader.EmployerReaderDataModel;
import org.acme.employer.domain.model.EmployerDomainModel;

import java.util.List;
import java.util.UUID;

public interface EmployerRepository {

    EmployerDomainModel createEmployer(EmployerDomainModel employerDomainModel);

    EmployerDomainModel updateEmployer(EmployerDomainModel employerDomainModel);

    EmployerDomainModel getEmployerByCode(String code);

    EmployerDomainModel getEmployerByName(String name);

    List<EmployerDomainModel> getEmployers();

    List<EmployerDomainModel> getAllActiveEmployers();

    UUID deleteEmployer(String code);

    EmployerReaderDataModel getEmployerDataModelByCode(String code);

}