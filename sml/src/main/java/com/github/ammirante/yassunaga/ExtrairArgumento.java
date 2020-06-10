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

    /**
     * Método responsável por extrair o número da linha. Exemplo: 10 input x -> Irá retornar 10.
     * @param linha
     * @return
     */
    public static Integer extrairNumeroLinha(String linha){
        String array[] = linha.split(" ");
        
        return Integer.parseInt(array[0]);
    }

    /**
     * Método responsável por extrair o operador relacional. Exemplo: 10 if i == z goto 98 -> Irá retornar ==.
     * @param linha
     * @return
     */
    public static String extrairOperadorRelacional(String linha) {
        String array[] = linha.split(" ");
        
        return array[3];
    }

    /**
     * Método responsável por extrair as variáveis do relacional. Exemplo 10 if i == z goto 98 -> Irá retornar [i, z].
     * @param linha
     * @return
     */
    public static String[] extrairVariaveisRelacional(String linha) {
        String array[] = linha.split(" ");
        String arrayTemp[] = new String[2];
        arrayTemp[0] = array[2];
        arrayTemp[1] = array[4];

        return arrayTemp;
    }

    /**
     * Método responsável por extrair a linha do relaciona. Exemplo 10 if i == z goto 98 -> Irá retornar 98. 
     * @param linha
     * @return
     */
    public static Integer extrairNumeroLinhaGoto(String linha) {
        String array[] = linha.split(" ");

        return Integer.valueOf(array[6]);
    }

    /**
     * Método responsável por extrair o operador. Exemplo 10 let a = b - c -> Irá retornar -.
     * @param linha
     * @return
     */
    public static String extrairOperador(String linha) {
        String array[] = linha.split(" ");

        if(array.length > 5) {
        	return array[5];
        }
        
        return null;
    }

    /**
     * Método responsável por extrair as variáveis do operador. Exemplo 10 let a = b - c -> Irá retornar [a, b, c].
     * @param linha
     * @return
     */
    public static String[] extrairVariaveisOperador(String linha) {
        String array[] = linha.split(" ");
        String arrayTemp[] = new String[3];
        arrayTemp[0] = array[2];
        arrayTemp[1] = array[4];
        if(array.length > 5) {
        	return new String[]{array[2], array[4], array[6]};
        }

        return new String[]{array[2], array[4]};
    }
}