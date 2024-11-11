package org.acme.employer.adapters.grpc;

import com.brightly.ca.grpc.employer.EmployerCodeRequest;
import com.brightly.ca.grpc.employer.EmployerDetail;
import com.brightly.ca.grpc.employer.EmployerDetailList;
import com.brightly.ca.grpc.employer.EmployerGrpcService;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import org.acme.employer.common.ReadWrite;
import org.acme.employer.domain.model.EmployerDomainModel;
import org.acme.employer.domain.model.EmployerDomainService;

import java.util.List;

@GrpcService
@Transactional
public class EmployerGrpcResource implements EmployerGrpcService {

    EmployerDomainService employerDomainService;

    public EmployerGrpcResource(EmployerDomainService employerDomainService) {
        this.employerDomainService = employerDomainService;
    }

    @Override
    public Uni<StringValue> createEmployerWithReadOnly(EmployerDetail request) {
        EmployerDomainModel employerDomainModel =
                EmployerDomainModelConverter.toEmployerDomainModel(request);

        String empCode = employerDomainService.createEmployer(employerDomainModel);

        return Uni.createFrom().item(StringValue.of(empCode));
    }

    @ReadWrite
    @Override
    public Uni<StringValue> createEmployerWithReadWrite(EmployerDetail request) {
        EmployerDomainModel employerDomainModel =
                EmployerDomainModelConverter.toEmployerDomainModel(request);

        String empCode = employerDomainService.createEmployer(employerDomainModel);

        return Uni.createFrom().item(StringValue.of(empCode));
    }

    @ReadWrite
    @Override
    public Uni<StringValue> updateEmployer(EmployerDetail request) {
        EmployerDomainModel employerDomainModel =
                EmployerDomainModelConverter.toEmployerDomainModel(request);

        String empCode = employerDomainService.updateEmployer(employerDomainModel);

        return Uni.createFrom().item(StringValue.of(empCode));
    }

    @Override
    public Uni<EmployerDetail> getEmployerByCode(EmployerCodeRequest employerCodeRequest) {
        if (employerCodeRequest.getEmployerCode().getValue().isEmpty()){
            throw new RuntimeException("Code is Invalid");
        }
        return Uni.createFrom()
                .item(EmployerDomainModelConverter
                        .toEmployerDetail(employerDomainService.getEmployerByCode(employerCodeRequest.getEmployerCode().getValue())));
    }

    @Override
    public Uni<EmployerDetailList> getAllActiveEmployers(Empty request) {
        List<EmployerDetail> employers = employerDomainService.getAllActiveEmployers().stream()
                .map(EmployerDomainModelConverter::toEmployerDetail)
                .toList();

        return Uni.createFrom()
                .item(EmployerDetailList.newBuilder()
                        .addAllEmployers(employers)
                        .build());
    }

    @Override
    public Uni<EmployerDetailList> getAllEmployers(Empty request) {

        List<EmployerDetail> employers = employerDomainService.getEmployers().stream()
                .map(EmployerDomainModelConverter::toEmployerDetail)
                .toList();

        return Uni.createFrom()
                .item(EmployerDetailList.newBuilder()
                        .addAllEmployers(employers)
                        .build());
    }

    @ReadWrite
    @Override
    public Uni<StringValue> deleteEmployer(EmployerCodeRequest employerCodeRequest) {
        boolean isDeleted = employerDomainService.deleteEmployer(employerCodeRequest.getEmployerCode().getValue());

        if (isDeleted) {
            return Uni.createFrom()
                    .item(StringValue.of(""));
        }
        return null;
    }

}
