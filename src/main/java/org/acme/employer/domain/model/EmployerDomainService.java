package org.acme.employer.domain.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.employer.domain.port.EmployerRepository;

import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class EmployerDomainService {

    EmployerRepository employerRepository;
    
    public EmployerDomainService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public String createEmployer(EmployerDomainModel employerDomainModel) {
        try {
            employerDomainModel = employerRepository.createEmployer(employerDomainModel);
            return employerDomainModel.getCode();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public EmployerDomainModel getEmployerByCode(String code) {
        return employerRepository.getEmployerByCode(code);
    }

    public EmployerDomainModel getEmployerByName(String name) {
        return employerRepository.getEmployerByName(name);
    }

    public String updateEmployer(EmployerDomainModel employerDomainModel) {
        try {
            employerDomainModel = employerRepository.updateEmployer(employerDomainModel);

            return employerDomainModel.getCode();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<EmployerDomainModel> getEmployers(){
        return employerRepository.getEmployers();
    }

    public List<EmployerDomainModel> getAllActiveEmployers() {
        try {
            return employerRepository.getAllActiveEmployers();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean deleteEmployer(String employerCode) {
        try {
            UUID employerId = employerRepository.deleteEmployer(employerCode);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
