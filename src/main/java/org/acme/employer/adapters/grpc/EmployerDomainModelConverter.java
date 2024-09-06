package org.acme.employer.adapters.grpc;

import com.brightly.ca.grpc.employer.EmployerDetail;
import org.acme.employer.domain.model.EmployerDomainModel;

public class EmployerDomainModelConverter {

    private EmployerDomainModelConverter() {}

    public static com.brightly.ca.grpc.employer.EmployerDetail toEmployerDetail(EmployerDomainModel employerDomainModel) {
        if (employerDomainModel == null)
            return null;

        return EmployerDetail.newBuilder()
                .setCode(employerDomainModel.getCode())
                .setName(employerDomainModel.getName())
                .setIsActive(employerDomainModel.getIsActive())
                .build();
    }

    public static EmployerDomainModel toEmployerDomainModel(com.brightly.ca.grpc.employer.EmployerDetail employerDetail) {
        if (employerDetail == null) {
            return null;
        }

        if (employerDetail.getCode().isEmpty()) {
            throw new RuntimeException("Code is Invalid");
        } else if (employerDetail.getName().isEmpty()) {
            throw new RuntimeException("Name is Invalid");
        }

        return EmployerDomainModel.builder()
                .code(employerDetail.getCode())
                .name(employerDetail.getName())
                .isActive(employerDetail.getIsActive())
                .build();
    }


}
