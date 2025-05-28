package com.a3bradesco.api.entities.enums;

public enum ReportType {
    CPF(1),
    CNPJ(2),
    EMAIL(3),
    SITE(4),
    CELLPHONE(5);

    private final int code;

    ReportType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
