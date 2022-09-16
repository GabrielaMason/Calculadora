package com.example.calcserver;

public class RequestResponse {
    public String type, operator, num1, num2, result;

    public RequestResponse(String type, String operator, String num1, String num2, String result) {
        this.type = type;
        this.operator = operator;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
    }
}
