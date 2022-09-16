package com.example.calcclient;

public class RequestData {
    public String type, operator, num1, num2, result;

    public RequestData(String type, String operator, String num1, String num2, String result) {
        this.type = "1";
        this.operator = operator;
        this.num1 = num1;
        this.num2 = num2;
        this.result = " ";
    }

}
