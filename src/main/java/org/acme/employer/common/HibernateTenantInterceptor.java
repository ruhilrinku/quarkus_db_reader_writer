package org.acme.employer.common;

import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Priority(1)
@Interceptor
@ReadWrite
public class HibernateTenantInterceptor {

    @PersistenceUnitExtension
    @Inject
    MyTenantResolver tenantResolver;

    @AroundInvoke
    public Object interceptTenantId(InvocationContext ctx) throws Exception {
        tenantResolver.setFlag(true);
        return ctx.proceed();
    }
}
