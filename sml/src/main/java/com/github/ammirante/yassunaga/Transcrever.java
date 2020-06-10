package com.github.ammirante.yassunaga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por transcrever o código.
 */
public class Transcrever {

    private String indexLinha = "00";
    private String indexEndereco = "99";
    private Map<String, String> mapaEnderecos = new HashMap<>();
    private Map<Integer, String> mapaGoto = new HashMap<>();
    private static final String OPERACAO_SOMA = "+";
    private Map<String, Integer> mapaOcorrenciasFaltantes = new HashMap<>(); 

    public Transcrever(){ } 

    public Map<Integer, String> getMapaGoto(){
        return this.mapaGoto;
    }

    public Map<String, Integer> getMapaOcorrenciaFaltantes(){
        return this.mapaOcorrenciasFaltantes;
    }

    /**
     * Método responsável por converter o input para código SML. Exemplo: input c -> 1000.
     * @param expressao
     * @param linha
     * @return
     */
    public String funcInput(String expressao, Integer linha) {
        String indexEnderecoLocal = indexEndereco;
        if(!isContemEndereco(expressao)){
            preencherMapaEnderecos(expressao, linha);
        } else{
            indexEnderecoLocal = mapaEnderecos.get(expressao);
        }
        
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.READ.getCodigoSML(), indexEnderecoLocal);

        return linhaSML;
    }

    /**
     * Método responsável por converter o let para código SML.
     * @param variaveis
     * @param operador
     * @param numeroLinha
     */
    public List<String> funcLet(String[] variaveis, String operador, Integer numeroLinha){
        inserirSeNaoHouverEndereco(variaveis);
        mapaGoto.put(numeroLinha, indexLinha);
        this.indexLinha = incrementarEndereco(indexLinha);
        if(variaveis.length > 2){
            return let3Variaveis(variaveis, operador, numeroLinha);
        }         
        return let2Variaveis(variaveis, numeroLinha);
    }

    /**
     * Método responsável por converter o print para código SML. Exemplo: print 10 -> 1100.
     * @param expressao
     * @param linha
     * @return
     */
    public String funcPrint(String expressao, Integer linha){
        inserirSeNaoHouverEndereco(new String[]{expressao});
        String indexEnderecoLocal =  mapaEnderecos.get(expressao);
        mapaGoto.put(linha, indexLinha);
        this.indexLinha = incrementarEndereco(this.indexLinha);

        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.WRITE.getCodigoSML(), indexEnderecoLocal);

        return linhaSML;
    }

    /**
     * Método responsável por converter a função goto para código SML. Exemplo: 15 goto 10 -> 4010
     * @param expressao
     * @param linha
     * @return
     */
    public String funcGoto(String expressao, Integer linha){
        mapaGoto.put(linha, indexLinha);
        
        this.indexEndereco = decrementarEndereco(this.indexEndereco);
        String linhaGoto = mapaGoto.get(Integer.valueOf(expressao));
        if(linhaGoto == null) {
            mapaOcorrenciasFaltantes.put(indexLinha, Integer.valueOf(expressao));
        }
        this.indexLinha = incrementarEndereco(indexLinha);
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCH.getCodigoSML(), linhaGoto);

        return linhaSML;
    }

    /**
     * Método responsável por convertar a função ifGoto para código SML.
     * @param variaveis
     * @param operador
     * @param linhaGoto
     * @param numeroLinha
     */
    public List<String> funcIfgoto(String[] variaveis, String operador, Integer linhaGoto, Integer numeroLinha){
        inserirSeNaoHouverEndereco(variaveis);
        String enderecoVariavelA = mapaEnderecos.get(variaveis[0]);
        String enderecoVariavelB = mapaEnderecos.get(variaveis[1]);
        this.mapaGoto.put(numeroLinha, indexLinha);
        String linha = mapaGoto.get(linhaGoto);
        if(linha == null) {
            mapaOcorrenciasFaltantes.put(indexLinha, linhaGoto);
        }
        this.indexLinha = incrementarEndereco(indexLinha);
        List<String> lstLinhas = new ArrayList<>(6);
        switch(operador){
            case ">":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha));
                break;
            case "<":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha));
                break;
            case "==":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linha));
                break;
            case ">=":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linha));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha));
                break;
            case "<=":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linha));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha));
                break;
            default:
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha));
                break;
        }
        
        return lstLinhas;
    }

    /**
     * Método responsável por transcrever a instrução de parada do código SIMPLE.
     * @param numeroLinha
     * @return 
     */
    public String funcEnd(Integer numeroLinha){
        mapaGoto.put(numeroLinha, indexLinha);
        this.indexLinha = incrementarEndereco(indexLinha);
        return this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.HALT.getCodigoSML(), "00");
    }

    /**
     * Método responsável por incrementar o index do endereço.
     * @param idx
     * @return
     */
    private String incrementarEndereco(String idx){
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
     * Método responsável decrementar o index da variável.
     * @param idx
     * @return
     */
    private String decrementarEndereco(String idx){
      Integer aux = Integer.parseInt(idx);
      aux--;
      idx = aux.toString();
      return idx;
    } 

    /**
     * Método responsável por criar a linha do código SML.
     * @param tipoOperacao
     * @param codigoOperacao
     * @param endereco
     * @return
     */
    private String montarLinhaSML(String tipoOperacao, Integer codigoOperacao, String endereco) {
        return tipoOperacao + codigoOperacao + endereco;
    }

    /**
     * Método responsável por identificar se a variável informada já possuí um endereço válido.
     * @param variavel
     * @return
     */
    private Boolean isContemEndereco(String variavel) {
        return mapaEnderecos.get(variavel) != null ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Método responsável por inserir no mapa de endereços a variável caso ela ainda não exista.
     * @param variaveis
     */
    private void inserirSeNaoHouverEndereco(String[] variaveis){
        for(int i=0; i<variaveis.length; i++){
            if(!isContemEndereco(variaveis[i])){
                mapaEnderecos.put(variaveis[i], indexEndereco);
                this.indexEndereco = decrementarEndereco(indexEndereco);
            }
        }
    }

    /**
     * Método responsável por converter o código SIMPLE (operação) para SML. Exemplo: let a = b - c.
     * @param variaveis
     * @param operador
     * @param numeroLinha
     * @return
     */
    private List<String> let3Variaveis(String[] variaveis, String operador, Integer numeroLinha) {
        String variavelA = mapaEnderecos.get(variaveis[0]);
        String variavelB = mapaEnderecos.get(variaveis[1]);
        String variavelC = mapaEnderecos.get(variaveis[2]);
        this.mapaGoto.put(numeroLinha, indexLinha);
        this.indexLinha = incrementarEndereco(indexLinha);
        List<String> lstLinhas = new ArrayList<>(3);
        switch(operador){
            case "-":
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), variavelC));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA));
                break;
            case "+":
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.ADD.getCodigoSML(), variavelC));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA));
                break;
            case "*":
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.MULTIPLY.getCodigoSML(), variavelC));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA));
                break;
            default:
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.DIVIDE.getCodigoSML(), variavelC));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA));
                break;
        }
        
        return lstLinhas;
    }

    /**
     * Método responsável por realizar a conversão do código SIMPLE (atribuição) para SML. Exemplo: let a = b.
     * @param variaveis
     * @param numeroLinha
     * @return
     */
    private List<String> let2Variaveis(String[] variaveis, Integer numeroLinha){
        String variavelA = mapaEnderecos.get(variaveis[0]);
        String variavelB = mapaEnderecos.get(variaveis[1]);
        this.mapaGoto.put(numeroLinha, indexLinha);
        this.indexLinha = incrementarEndereco(indexLinha);
        List<String> lstLinhas = new ArrayList<>(2);
        lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB));
        lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA));

        return lstLinhas;
    }

    /**
     * Método responsável por realizar o preenchimento dos mapas de endereços.
     * @param expressao
     * @param linha
     */
    private void preencherMapaEnderecos(String expressao, Integer linha) {
        mapaEnderecos.put(expressao, indexEndereco);
        mapaGoto.put(linha, indexLinha);
        this.indexEndereco = decrementarEndereco(this.indexEndereco);
        this.indexLinha = incrementarEndereco(this.indexLinha);
    }

}