package com.dezzy.springsecurity.data.entity.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permissions {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    MANAGEMENT_READ("management:read"),
    MANAGEMENT_WRITE("management:write"),
    MANAGEMENT_UPDATE("management:update"),
    MANAGEMENT_DELETE("management:delete"),
    ;

    private final String permission;
}
