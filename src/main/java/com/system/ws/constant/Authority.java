package com.system.ws.constant;

public class Authority {
    public static final String[] CUSTOMER_AUTHORITIES = {"system:read"};
    public static final String[] SYSTEM_USER_AUTHORITIES = {"system:read", "system:update", "system:create"};
    public static final String[] SYSTEM_ADMIN_AUTHORITIES = {"system:read", "system:update", "system:create", "system:delete"};
}
