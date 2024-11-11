package org.acme.employer.adapters.database;

import org.acme.employer.domain.model.EmployerDomainModel;

public final class EmployerDataModelConverter {

    private EmployerDataModelConverter() {}

    public static EmployerDataModel toEmployerDataModel(EmployerDomainModel employerDomainModel) {
        if (employerDomainModel == null) {
            return null;
        }

        EmployerDataModel employerWriterDataModel = new EmployerDataModel();

        employerWriterDataModel.setCode(employerDomainModel.getCode());
        employerWriterDataModel.setName(employerDomainModel.getName());
        employerWriterDataModel.setIsActive(employerDomainModel.getIsActive()!= null ? employerDomainModel.getIsActive() : Boolean.TRUE);

        return employerWriterDataModel;
    }

    public static EmployerDomainModel toEmployerDomainModel(EmployerDataModel employerWriterDataModel) {
        if (employerWriterDataModel == null) {
            return null;
        }

        EmployerDomainModel.EmployerDomainModelBuilder employerDomainModelBuilder = EmployerDomainModel.builder();

        return employerDomainModelBuilder
                .id(employerWriterDataModel.getId())
                .code(employerWriterDataModel.getCode())
                .name(employerWriterDataModel.getName())
                .isActive(employerWriterDataModel.getIsActive())
                .build();
    }
}
