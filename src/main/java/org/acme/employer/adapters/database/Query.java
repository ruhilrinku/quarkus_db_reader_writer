package org.acme.employer.adapters.database;

public class Query {

    private Query() {}

    public static final String SELECT_EMPLOYER_BY_CODE = "code = ?1";

    public static final String SELECT_ALL_ACTIVE_EMPLOYERS = "isActive = true";

}
