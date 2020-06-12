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

    private Integer indexLinhaSML = 0;
    private Integer indexVariaveis = 99;
    private static final String OPERACAO_SOMA = "+";
    private Mapas mapas;

    /**
     * Construtor vazio.
     */
    public Transcrever(Mapas mapas){
        this.mapas = mapas;
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
        Integer indexEnderecoLocal = indexVariaveis;
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        if(!isContemEndereco(variavel)){
            preencherMapaEnderecos(variavel, numLinhaSimple);
        } else{
            indexEnderecoLocal = this.mapas.mapaVariavelToEnderecoSML.get(variavel);
        }
        this.indexLinhaSML++;

        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.READ.getCodigoSML(), indexEnderecoLocal, numLinhaSimple);

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
    public String funcPrint(String variavel, Integer numLinhaSimple){
        inserirSeNaoHouverEndereco(new String[]{variavel});
        Integer indexEnderecoLocal =  this.mapas.mapaVariavelToEnderecoSML.get(variavel);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        this.indexLinhaSML++;

        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.WRITE.getCodigoSML(), indexEnderecoLocal, numLinhaSimple);

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
        this.indexVariaveis--;
        Integer linhaGoto = this.mapas.mapaGotoLinhaSimpleLinhaSML.get(Integer.valueOf(expressao));
        if(linhaGoto == null) {
            this.mapas.mapaOcorrenciasFaltantes.put(indexLinhaSML, Integer.valueOf(expressao));
        }
        this.indexLinhaSML++;
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCH.getCodigoSML(), linhaGoto, linhaGoto);

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
        Integer enderecoVariavelA = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[0]);
        Integer enderecoVariavelB = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[1]);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        Integer linha = this.mapas.mapaGotoLinhaSimpleLinhaSML.get(linhaGoto);
        if(linha == null) {
            this.mapas.mapaOcorrenciasFaltantes.put(indexLinhaSML, linhaGoto);
        }
        List<String> lstLinhas = new ArrayList<>(6);
        switch(operador){
            case ">":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha, linhaGoto));
                this.indexLinhaSML += 3;
                break;
            case "<":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha, linhaGoto));
                this.indexLinhaSML += 3;
                break;
            case "==":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linha, linhaGoto));
                this.indexLinhaSML += 3;
                break;
            case ">=":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linha, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha, linhaGoto));
                this.indexLinhaSML += 4;
                break;
            case "<=":
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linha, linhaGoto));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha, linhaGoto));
                this.indexLinhaSML += 4;
                break;
            default:
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA, numLinhaSimple));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB, numLinhaSimple));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha, numLinhaSimple));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB, numLinhaSimple));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA, numLinhaSimple));
                lstLinhas.add(this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linha, numLinhaSimple));
                this.indexLinhaSML += 6;
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
        this.indexLinhaSML++;
        return this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.HALT.getCodigoSML(), 0, numLinhaSimple);
    }

    /**
     * Método responsável por criar a linha do código SML.
     * @param tipoOperacao
     * @param codigoOperacao
     * @param endereco
     * @return
     */
    private String montarLinhaSML(String tipoOperacao, Integer codigoOperacao, Integer endereco, Integer numLinhaSimple) {
        String enderecoTemp;
        if(endereco == null) {
            enderecoTemp = "S" + numLinhaSimple.toString();
        } else {
            enderecoTemp = UtilSML.montarIndice(endereco);
        }
        return tipoOperacao + codigoOperacao + enderecoTemp;
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
                this.indexVariaveis--;
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
        Integer variavelA = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[0]);
        Integer variavelB = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[1]);
        Integer variavelC = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[2]);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        List<String> lstLinhas = new ArrayList<>(3);
        switch(operador){
            case "-":
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), variavelC, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA, numLinhaSimple));
                this.indexLinhaSML += 3;
                break;
            case "+":
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.ADD.getCodigoSML(), variavelC, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA, numLinhaSimple));
                this.indexLinhaSML += 3;
                break;
            case "*":
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.MULTIPLY.getCodigoSML(), variavelC, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA, numLinhaSimple));
                this.indexLinhaSML += 3;
                break;
            default:
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.DIVIDE.getCodigoSML(), variavelC, numLinhaSimple));
                lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA, numLinhaSimple));
                this.indexLinhaSML += 3;
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
        Integer variavelA = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[0]);
        Integer variavelB = this.mapas.mapaVariavelToEnderecoSML.get(variaveis[1]);
        this.mapas.mapaGotoLinhaSimpleLinhaSML.put(numLinhaSimple, indexLinhaSML);
        List<String> lstLinhas = new ArrayList<>(2);
        lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), variavelB, numLinhaSimple));
        lstLinhas.add(montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.STORE.getCodigoSML(), variavelA, numLinhaSimple));
        this.indexLinhaSML += 2;
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
        this.indexVariaveis--;
        this.indexLinhaSML++;
    }

}