package com.tindung.jhip.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String STOREADMIN = "ROLE_STORE";
    public static final String STAFFADMIN = "ROLE_STAFF";

    private AuthoritiesConstants() {
    }
}
