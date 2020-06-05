package com.github.ammirante.yassunaga;

import java.util.HashMap;
import java.util.Map;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por transcrever o código.
 */
public class Transcrever {

    private String indexEndereco = "00";
    private String instrucao = null;
    private Map<String, String> mapaEnderecos = new HashMap<>();
    private static final String OPERACAO_SOMA = "+";
    private static final String OPERACAO_SUBTRACAO = "-";

    public Transcrever(){ }

    //expressao = x
    /**
     * Método responsável por converter o input para código SML. Exemplo: input c -> 2000.
     * @param expressao
     * @return
     */
    public String funcInput(String expressao){
        if(mapaEnderecos.get(expressao) == null) {
            mapaEnderecos.put(expressao, indexEndereco);           
        }
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), indexEndereco);
        this.incrementar();

        return linhaSML;
    }

    public void funcLet(String expressao){

    }
    
    public void funcPrint(String expressao){

    }

    public void funcGoto(String expressao){

    }

    public void funcIfgoto(String expressao){

    }

    public void funcEnd(String expressao){

    }

    /**
     * Método responsável por incrementar o index do endereço.
     */
    private void incrementar(){
        Integer aux = Integer.parseInt(this.indexEndereco);
        aux++;
        if(aux < 10) {
            String cont = "0" + aux;
            this.indexEndereco = cont;
        } else {
            this.indexEndereco = aux.toString();
        }
    }

    /**
     * Método responsável por criar a linha do código SML.
     * @return
     */
    private String montarLinhaSML(String tipoOperacao, Integer codigoOperacao, String endereco) {
        return tipoOperacao + codigoOperacao + endereco;
    }
}