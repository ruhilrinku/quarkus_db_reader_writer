package org.acme.employer.adapters.database;

import org.acme.employer.adapters.database.reader.EmployerReaderDataModel;
import org.acme.employer.adapters.database.writer.EmployerWriterDataModel;
import org.acme.employer.domain.model.EmployerDomainModel;

public final class EmployerDataModelConverter {

    private EmployerDataModelConverter() {}

    public static EmployerWriterDataModel toEmployerDataModel(EmployerDomainModel employerDomainModel) {
        if (employerDomainModel == null) {
            return null;
        }

        EmployerWriterDataModel employerWriterDataModel = new EmployerWriterDataModel();

        employerWriterDataModel.setCode(employerDomainModel.getCode());
        employerWriterDataModel.setName(employerDomainModel.getName());
        employerWriterDataModel.setIsActive(employerDomainModel.getIsActive()!= null ? employerDomainModel.getIsActive() : Boolean.TRUE);

        return employerWriterDataModel;
    }

    public static EmployerDomainModel toEmployerDomainModel(EmployerWriterDataModel employerWriterDataModel) {
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

    public static EmployerDomainModel toEmployerDomainModel(EmployerReaderDataModel employerReaderDataModel) {
        if (employerReaderDataModel == null) {
            return null;
        }

        EmployerDomainModel.EmployerDomainModelBuilder employerDomainModelBuilder = EmployerDomainModel.builder();

        return employerDomainModelBuilder
                .id(employerReaderDataModel.getId())
                .code(employerReaderDataModel.getCode())
                .name(employerReaderDataModel.getName())
                .isActive(employerReaderDataModel.getIsActive())
                .build();
    }
}
