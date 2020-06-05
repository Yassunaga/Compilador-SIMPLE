package com.github.ammirante.yassunaga;

public class ExtrairArgumento{

    public ExtrairArgumento() { }

    public static String extrairOperacao(String linha){
        return linha.split(" ")[1];
    }

    public static String extrairExpressao(String linha){
        return "";
    }
}