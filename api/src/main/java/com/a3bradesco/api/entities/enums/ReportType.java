package com.a3bradesco.api.entities.enums;

public enum ReportType {
    CPF(1),
    CNPJ(2),
    EMAIL(3),
    SITE(4),
    CELLPHONE(5);

    private int code;

    private ReportType(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static ReportType valueOf(int code){
        for(ReportType value : ReportType.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        return ReportType.CPF;
    }
}
