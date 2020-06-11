package com.github.ammirante.yassunaga;

import java.util.ArrayList;
import java.util.List;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por transcrever o código.
 */
public class Transcrever {

    private String indexLinhaSML = "00";
    private String indexVariaveis = "99";
    private static final String OPERACAO_SOMA = "+";
    private Mapas mapas;

    /**
     * Construtor vazio.
     */
    public Transcrever(Mapas mapas){
        this.mapas = mapas;
        this.indexVariaveis = mapas.mapaVariavelToEnderecoSML.values().stream().sorted().findFirst().orElse(null);
     } 

    /**
     * Método responsável por retornar os mapas.
     * @return
     */
    public Mapas getMapas(){
        return this.mapas;
    }

    /**
     * Método responsável por converter o input para código SML. Exemplo: input c -> 1000.
     * @param variavel
     * @param linha
     * @return
     */
    public String funcInput(String variavel, Integer numLinhaSimple) {
        String indexEnderecoLocal = indexVariaveis;
        if(!isContemEndereco(variavel)){
            preencherMapaEnderecos(variavel, numLinhaSimple);
        } else{
            indexEnderecoLocal = this.mapas.mapaVariavelToEnderecoSML.get(variavel);
        }
        
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.READ.getCodigoSML(), indexEnderecoLocal);

        return linhaSML;
    }

    /**
     * Método responsável por converter o let para código SML.
     * @param variaveis
     * @param operador
     * @param numLinhaSimple
     */
    public List<String> funcLet(String[] variaveis, String operador, Integer numLinhaSimple){
        inserirSeNaoHouverEndereco(variaveis);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexLinhaSML = incrementarEndereco(indexLinhaSML);
        if(variaveis.length > 2){
            return let3Variaveis(variaveis, operador, numLinhaSimple);
        }         
        return let2Variaveis(variaveis, numLinhaSimple);
    }

    /**
     * Método responsável por converter o print para código SML. Exemplo: print 10 -> 1100.
     * @param expressao
     * @param linha
     * @return
     */
    public String funcPrint(String expressao, Integer numLinhaSimple){
        inserirSeNaoHouverEndereco(new String[]{expressao});
        String indexEnderecoLocal =  this.mapas.mapaVariavelToEnderecoSML.get(expressao);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexLinhaSML = incrementarEndereco(this.indexLinhaSML);

        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.WRITE.getCodigoSML(), indexEnderecoLocal);

        return linhaSML;
    }

    /**
     * Método responsável por converter a função goto para código SML. Exemplo: 15 goto 10 -> 4010
     * @param expressao
     * @param linha
     * @return
     */
    public String funcGoto(String expressao, Integer numLinhaSimple){
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexVariaveis = decrementarEndereco(this.indexVariaveis);
        String linhaGoto = this.mapas.mapaGotoLinhaSimpleLinhaSML.get(Integer.valueOf(expressao));
        if(linhaGoto == null) {
            this.mapas.mapaOcorrenciasFaltantes.put(indexLinhaSML, Integer.valueOf(expressao));
        }
        this.indexLinhaSML = incrementarEndereco(indexLinhaSML);
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCH.getCodigoSML(), linhaGoto);

        return linhaSML;
    }

    /**
     * Método responsável por convertar a função ifGoto para código SML.
     * @param variaveis
     * @param operador
     * @param linhaGoto
     * @param numLinhaSimple
     */
    public List<String> funcIfgoto(String[] variaveis, String operador, Integer linhaGoto, Integer numLinhaSimple){
        inserirSeNaoHouverEndereco(variaveis);
        String enderecoVariavelA = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[0]);
        String enderecoVariavelB = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[1]);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        String linha = this.mapas.mapaGotoLinhaSimpleLinhaSML.get(linhaGoto);
        if(linha == null) {
            this.mapas.mapaOcorrenciasFaltantes.put(indexLinhaSML, linhaGoto);
        }
        this.indexLinhaSML = incrementarEndereco(indexLinhaSML);
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
     * @param numLinhaSimple
     * @return 
     */
    public String funcEnd(Integer numLinhaSimple){
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexLinhaSML = incrementarEndereco(indexLinhaSML);
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
        return this.mapas.mapaVariavelToEnderecoSML.get(variavel) != null ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Método responsável por inserir no mapa de endereços a variável caso ela ainda não exista.
     * @param variaveis
     */
    private void inserirSeNaoHouverEndereco(String[] variaveis){
        for(int i=0; i<variaveis.length; i++){
            if(!isContemEndereco(variaveis[i])){
                this.mapas.mapaVariavelToEnderecoSML.put(variaveis[i], indexVariaveis);
                this.indexVariaveis = decrementarEndereco(indexVariaveis);
            }
        }
    }

    /**
     * Método responsável por converter o código SIMPLE (operação) para SML. Exemplo: let a = b - c.
     * @param variaveis
     * @param operador
     * @param numLinhaSimple
     * @return
     */
    private List<String> let3Variaveis(String[] variaveis, String operador, Integer numLinhaSimple) {
        String variavelA = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[0]);
        String variavelB = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[1]);
        String variavelC = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[2]);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexLinhaSML = incrementarEndereco(indexLinhaSML);
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
     * @param numLinhaSimple
     * @return
     */
    private List<String> let2Variaveis(String[] variaveis, Integer numLinhaSimple){
        String variavelA = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[0]);
        String variavelB = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[1]);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexLinhaSML = incrementarEndereco(indexLinhaSML);
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
        this.mapas.mapaVariavelToEnderecoSML.put(expressao, indexVariaveis);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(linha, indexLinhaSML);
        this.indexVariaveis = decrementarEndereco(this.indexVariaveis);
        this.indexLinhaSML = incrementarEndereco(this.indexLinhaSML);
    }

}