package com.clayton.claytondemoapplication.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseMessages {
    FAILED("F99","Request Failed"),
    SUCCESSFUL("W52","Request Successful"),
    ERROR("E31","Invalid Request"),
    NOT_FOUND("M63","Not Found"),
    FORBIDDEN("F23","forbidden"),
    NOT_ALLOWED("N77","Request Not Allowed"),
    INTERNAL_SERVER_ERROR("X86","Internal Server Error");

    private String code;
    private String message;


}
