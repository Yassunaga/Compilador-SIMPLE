package com.github.ammirante.yassunaga;

import java.util.HashMap;
import java.util.Map;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por cuidar dos mapas.
 */
public class Mapas{

    public Map<String, Integer> mapaVariavelToEnderecoSML;
    public Map<Integer, Integer> mapaGotoLinhaSimpleLinhaSML;
    public Map<Integer, Integer> mapaOcorrenciasFaltantes;

    /**
     * Construtor da classe.
     */
    public Mapas(){
        this.mapaOcorrenciasFaltantes = new HashMap<>();
        this.mapaVariavelToEnderecoSML = new HashMap<>();
        this.mapaGotoLinhaSimpleLinhaSML = new HashMap<>();
    }
} 