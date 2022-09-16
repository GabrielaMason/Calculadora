package com.example.calcmiddleware;

public class DataComponent {
    public String type, num1, operator, num2, result;

    public DataComponent(String type, String operator, String num1, String num2, String result) {
        this.type = type;
        this.operator = operator;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
    }
}
