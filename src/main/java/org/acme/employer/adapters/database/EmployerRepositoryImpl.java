package org.acme.employer.adapters.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.employer.domain.model.EmployerDomainModel;
import org.acme.employer.domain.port.EmployerRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EmployerRepositoryImpl implements EmployerRepository {
    @Inject
    EmployerDatabaseRepository employerDatabaseRepository;

    @Override
    public EmployerDomainModel createEmployer(EmployerDomainModel employerDomainModel) {
        EmployerDataModel employerDataModel = EmployerDataModelConverter.toEmployerDataModel(employerDomainModel);
        System.out.println("Repository Identifier: " + employerDatabaseRepository.getName());
        try {
            employerDatabaseRepository.persistAndFlush(employerDataModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public EmployerDomainModel updateEmployer(EmployerDomainModel employerDomainModel) {
        EmployerDataModel employerDataModel = getEmployerDataModel(employerDomainModel.getCode());

        if (employerDataModel == null)
            throw new RuntimeException("Employer not found with code !!");

        employerDataModel.setName(employerDomainModel.getName());

        if (employerDomainModel.getIsActive() != null)
            employerDataModel.setIsActive(employerDomainModel.getIsActive());

        employerDatabaseRepository.persistAndFlush(employerDataModel);

        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public EmployerDomainModel getEmployerByCode(String code) {
        EmployerDataModel employerDataModel = employerDatabaseRepository
                .find(Query.SELECT_EMPLOYER_BY_CODE, code).firstResult();

        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public EmployerDomainModel getEmployerByName(String name) {
        EmployerDataModel employerDataModel = getEmployerDataModelByName(name);
        return EmployerDataModelConverter.toEmployerDomainModel(employerDataModel);
    }

    @Override
    public List<EmployerDomainModel> getEmployers() {
        System.out.println("Repository Identifier: " + employerDatabaseRepository.getName());

        try {
            List<EmployerDataModel> employerReaderDataModels = employerDatabaseRepository.findAll().stream().toList();

            return employerReaderDataModels.stream().map(EmployerDataModelConverter::toEmployerDomainModel).toList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<EmployerDomainModel> getAllActiveEmployers() {
        System.out.println("Repository Identifier: " + employerDatabaseRepository.getName());
        return employerDatabaseRepository.find(Query.SELECT_ALL_ACTIVE_EMPLOYERS)
                .stream()
                .map(EmployerDataModelConverter::toEmployerDomainModel)
                .toList();
    }

    @Override
    public UUID deleteEmployer(String code) {
        EmployerDataModel employerDataModel = employerDatabaseRepository.find("code", code)
                .firstResult();

        if (employerDataModel == null)
            throw new RuntimeException("Employer not found with code !!");

        employerDataModel.setDeletedId(employerDataModel.getId());
        employerDatabaseRepository.persistAndFlush(employerDataModel);
        return employerDataModel.getId();
    }

    @Override
    public EmployerDataModel getEmployerDataModelByCode(String code) {
        return employerDatabaseRepository.find(Query.SELECT_EMPLOYER_BY_CODE, code).firstResult();
    }

    private EmployerDataModel getEmployerDataModel(String code) {
        return employerDatabaseRepository.find(Query.SELECT_ALL_ACTIVE_EMPLOYERS, code).firstResult();
    }

    private EmployerDataModel getEmployerDataModelByName(String name) {
        return employerDatabaseRepository
                .find("name = ?1", name).firstResult();
    }

}