package org.acme.employer.adapters.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.employer.adapters.database.reader.EmployerReaderDataModel;
import org.acme.employer.adapters.database.reader.EmployerReaderDatabaseRepository;
import org.acme.employer.adapters.database.writer.EmployerWriterDataModel;
import org.acme.employer.adapters.database.writer.EmployerWriterDatabaseRepository;
import org.acme.employer.domain.model.EmployerDomainModel;
import org.acme.employer.domain.port.EmployerRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EmployerRepositoryImpl implements EmployerRepository {
    @Inject
    EmployerWriterDatabaseRepository employerWriterDatabaseRepository;

    @Inject
    EmployerReaderDatabaseRepository employerReaderDatabaseRepository;

    @Override
    public EmployerDomainModel createEmployer(EmployerDomainModel employerDomainModel) {
        EmployerWriterDataModel employerDataModel = EmployerDataModelConverter.toEmployerDataModel(employerDomainModel);
        System.out.println("Repository Identifier: " + employerWriterDatabaseRepository.getName());
        try {
            employerWriterDatabaseRepository.persistAndFlush(employerDataModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public EmployerDomainModel updateEmployer(EmployerDomainModel employerDomainModel) {
        EmployerWriterDataModel employerDataModel = getEmployerDataModel(employerDomainModel.getCode());

        if (employerDataModel == null)
            throw new RuntimeException("Employer not found with code !!");

        employerDataModel.setName(employerDomainModel.getName());

        if (employerDomainModel.getIsActive() != null)
            employerDataModel.setIsActive(employerDomainModel.getIsActive());

        employerWriterDatabaseRepository.persistAndFlush(employerDataModel);

        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public EmployerDomainModel getEmployerByCode(String code) {
        EmployerReaderDataModel employerDataModel = employerReaderDatabaseRepository
                .find(Query.SELECT_EMPLOYER_BY_CODE, code).firstResult();

        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public EmployerDomainModel getEmployerByName(String name) {
        EmployerReaderDataModel employerDataModel = getEmployerDataModelByName(name);
        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public List<EmployerDomainModel> getEmployers() {
        System.out.println("Repository Identifier: " + employerReaderDatabaseRepository.getName());

        try {
            List<EmployerReaderDataModel> employerReaderDataModels = employerReaderDatabaseRepository.findAll().stream().toList();

            return employerReaderDataModels.stream().map(EmployerDataModelConverter::toEmployerDomainModel).toList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<EmployerDomainModel> getAllActiveEmployers() {
        System.out.println("Repository Identifier: " + employerReaderDatabaseRepository.getName());
        return employerReaderDatabaseRepository.find(Query.SELECT_ALL_ACTIVE_EMPLOYERS)
                .stream()
                .map(EmployerDataModelConverter::toEmployerDomainModel)
                .toList();
    }

    @Override
    public UUID deleteEmployer(String code) {
        EmployerWriterDataModel employerDataModel = employerWriterDatabaseRepository.find("code", code)
                .firstResult();

        if (employerDataModel == null)
            throw new RuntimeException("Employer not found with code !!");

        employerDataModel.setDeletedId(employerDataModel.getId());
        employerWriterDatabaseRepository.persistAndFlush(employerDataModel);
        return employerDataModel.getId();
    }

    @Override
    public EmployerReaderDataModel getEmployerDataModelByCode(String code) {
        return employerReaderDatabaseRepository.find(Query.SELECT_EMPLOYER_BY_CODE, code).firstResult();
    }

    private EmployerWriterDataModel getEmployerDataModel(String code) {
        return employerWriterDatabaseRepository.find(Query.SELECT_ALL_ACTIVE_EMPLOYERS, code).firstResult();
    }

    private EmployerReaderDataModel getEmployerDataModelByName(String name) {
        return employerReaderDatabaseRepository
                .find("name = ?1", name).firstResult();
    }

}