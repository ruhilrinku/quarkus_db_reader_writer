package org.acme.employer.common;

import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import jakarta.enterprise.context.RequestScoped;

@PersistenceUnitExtension
@RequestScoped
public class MyTenantResolver implements TenantResolver {

    private boolean flag = false;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String getDefaultTenantId() {
        System.out.println("Initialized Read-Only DB Connection !!");
        return "read-only";
    }

    @Override
    public String resolveTenantId() {
        if (flag) {
            System.out.println("Initialized Read-Write DB Connection !!");
            return "read-write";
        }
        return getDefaultTenantId();
    }

}
