package com.github.ammirante.yassunaga;

import java.util.Arrays;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por manipular a linha e extrair os valores.
 */
public class ExtrairArgumento {

    public ExtrairArgumento() { }

    /**
     * Método responsável por extrair a operação da linha. Exemplo: 10 input x -> Irá retornar input.
     * @param linha
     * @return
     */
    public static String extrairOperacao(String linha){
        return linha.split(" ")[1];
    }

    /**
     * Método responsável por extrair a expressão da linha. Exemplo: 10 input x -> Irá retornar x ou 10 let c = a + b irá retornar [c, =, a, +, b].
     * @param linha
     * @return
     */
    public static String[] extrairExpressao(String linha){
        String array[] = linha.split(" ");
        return Arrays.copyOfRange(array, 1, array.length);
    }
}