package com.example.calcclient;

public class RequestResult {
    public String type, operator, num1, num2, result;

    public RequestResult(String type, String operator, String num1, String num2, String result) {
        this.type = type;
        this.operator = operator;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
    }
}
