package com.github.ammirante.yassunaga;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Método responsável por ordenar por valor.
     * @param mapa
     * @return
     */
    public static Map<String, Integer> sortByValue(Map<String, Integer> mapa) {
        return mapa.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}