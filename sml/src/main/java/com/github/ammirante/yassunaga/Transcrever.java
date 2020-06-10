package com.github.ammirante.yassunaga;

import java.util.Arrays;
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
    private String indexEndereco = "00";
    private Map<String, String> mapaEnderecos = new HashMap<>();
    private Map<Integer, String> mapaGoto = new HashMap<>();
    private static final String OPERACAO_SOMA = "+";
    private static final String OPERACAO_SUBTRACAO = "-";

    public Transcrever(){ }

    /**
     * Método responsável por converter o input para código SML. Exemplo: input c -> 1000.
     * @param expressao
     * @param linha
     * @return
     */
    public String funcInput(String expressao, Integer linha) {
        String indexEnderecoLocal = indexEndereco;
        if(mapaEnderecos.get(expressao) == null){
            mapaEnderecos.put(expressao, indexEndereco);
            mapaGoto.put(linha, indexLinha);
            this.indexEndereco = incrementarEndereco(this.indexEndereco);
            this.indexLinha = incrementarEndereco(this.indexLinha);
        } else{
            indexEnderecoLocal = mapaEnderecos.get(expressao);
        }
        
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.READ.getCodigoSML(), indexEnderecoLocal);

        return linhaSML;
    }

    public void funcLet(String expressao){

    }
    
    /**
     * Método responsável por converter o print para código SML. Exemplo: print 10 -> 1100.
     */
    public String funcPrint(String expressao, Integer linha){
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
        
        this.indexEndereco = incrementarEndereco(this.indexEndereco);
        this.indexLinha = incrementarEndereco(this.indexLinha);
        String linhaGoto = mapaGoto.get(Integer.valueOf(expressao));
        String linhaSML = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCH.getCodigoSML(), linhaGoto);

        return linhaSML;
    }

    /**
     * Método responsável por convertar a função ifGoto para código SML.
     * @param variaveis
     * @param operador
     * @param linhaGoto
     */
    public List<String> funcIfgoto(String[] variaveis, String operador, Integer linhaGoto){
        String enderecoVariavelA = mapaEnderecos.get(variaveis[0]);
        String enderecoVariavelB = mapaEnderecos.get(variaveis[1]);
        String SMLLoad, SMLSubtract, SMLBranchNeg, SMLBranchZero;
        List<String> SMLs;
        switch(operador){
            case ">":
                SMLLoad = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB);
                SMLSubtract = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA);
                SMLBranchNeg = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linhaGoto.toString());
                SMLs = Arrays.asList(SMLLoad, SMLSubtract, SMLBranchNeg);
                break;
            case "<":
                SMLLoad = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA);
                SMLSubtract = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB);
                SMLBranchNeg = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linhaGoto.toString());
                SMLs = Arrays.asList(SMLLoad, SMLSubtract, SMLBranchNeg);
                break;
            case "==":
                SMLLoad = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA);
                SMLSubtract = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB);
                SMLBranchZero = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linhaGoto.toString());
                SMLs = Arrays.asList(SMLLoad, SMLSubtract, SMLBranchZero);
                break;
            case ">=":
                SMLLoad = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB);
                SMLSubtract = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA);
                SMLBranchZero = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linhaGoto.toString());
                SMLBranchNeg = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linhaGoto.toString());
                SMLs = Arrays.asList(SMLLoad, SMLSubtract, SMLBranchZero, SMLBranchNeg);
                break;
            case "<=":
                SMLLoad = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA);
                SMLSubtract = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB);
                SMLBranchZero = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHZERO.getCodigoSML(), linhaGoto.toString());
                SMLBranchNeg = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linhaGoto.toString());
                SMLs = Arrays.asList(SMLLoad, SMLSubtract, SMLBranchZero, SMLBranchNeg);
                break;
            default:
                SMLLoad = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelA);
                SMLSubtract = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelB);
                SMLBranchNeg = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linhaGoto.toString());
                String SMLLoadB = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.LOAD.getCodigoSML(), enderecoVariavelB);
                String SMLSubtractA = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.SUBTRACT.getCodigoSML(), enderecoVariavelA);
                String SMLBranchNegX = this.montarLinhaSML(OPERACAO_SOMA, CodigoSMLEnum.BRANCHNEG.getCodigoSML(), linhaGoto.toString());
                SMLs = Arrays.asList(SMLLoad, SMLSubtract, SMLBranchNeg, SMLLoadB, SMLSubtractA, SMLBranchNegX);
                break;
        }

        return SMLs;
    }

    /**
     * Método responsável por transcrever a instrução de parada do código SIMPLE.
     * @param 
     * @return 
     */
    public String funcEnd(){
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
     * Método responsável por criar a linha do código SML.
     * @param tipoOperacao
     * @param codigoOperacao
     * @param endereco
     * @return
     */
    private String montarLinhaSML(String tipoOperacao, Integer codigoOperacao, String endereco) {
        return tipoOperacao + codigoOperacao + endereco;
    }
}