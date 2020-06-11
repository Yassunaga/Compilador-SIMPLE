package com.github.ammirante.yassunaga;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 Gabriel Bueno
 * Yassunaga - 1912130023
 * 
 * Classe responsável por executar o código
 */
public class UtilSML {
    
    /**
     * Método responsável por incrementar o index. Exemplo: "01" -> "02", "10" -> "11".
     * @param idx
     * @return
     */
    public static String incrementarIndex(String idx) {
        Integer aux = Integer.parseInt(idx);
        aux++;
        if(aux < 10) {
            String cont = "0" + aux;
            idx = cont;
        } else {
        	idx = aux.toString();
        }       
        return idx;
    }

    /**
     * Método responsável por montar o índice. Exemplo: 1 -> "01", 10 -> "10".
     * @param indice
     * @return
     */
    public static String montarIndice(Integer indice){
        if(indice < 10){
            return "0" + indice.toString();
        }
        return indice.toString();
    }
}