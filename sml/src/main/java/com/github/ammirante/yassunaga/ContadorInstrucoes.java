package com.github.ammirante.yassunaga;

import java.util.List;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por realziar a contagem de instruções.
 */
public class ContadorInstrucoes {
    
    private Integer qtdInstrucoes = 0;

    public ContadorInstrucoes(ManipularArquivo manipularArquivo) {
        percorrerLinhas(manipularArquivo.getLinhasEntrada());
    }

    /**
     * Método responsável por percorrer as linhas do arquivo txt.
     * @param linhasEntrada
     */
    private void percorrerLinhas(List<String> linhasEntrada) {
        String tipoOperacao;
        for(String linhaEntrada : linhasEntrada) {
            tipoOperacao = ExtrairArgumento.extrairOperacao(linhaEntrada);
            somarQuantidade(tipoOperacao, linhaEntrada);
        }
    }

    /**
     * Método responsável por calcular a quantidade de instruções.
     * @param tipoOperacao
     */
    private void somarQuantidade(String tipoOperacao, String linhaEntrada) {
        if(tipoOperacao.equals("let")) {
            String operadores[] = ExtrairArgumento.extrairVariaveisOperador(linhaEntrada);
            if(operadores.length > 2) {
                qtdInstrucoes += SimpleInstrucoesEnum.getQtdInstrucoesByOperador(ExtrairArgumento.extrairOperador(linhaEntrada));
            } else {
                qtdInstrucoes += 2;
            }
        } else if(tipoOperacao.equals("if")) {
            qtdInstrucoes += SimpleInstrucoesEnum.getQtdInstrucoesByOperador(ExtrairArgumento.extrairOperadorRelacional(linhaEntrada));
        } else if(tipoOperacao.equals("input")) {
            qtdInstrucoes += SimpleInstrucoesEnum.INPUT.getQtdInstrucoes();
        } else if(tipoOperacao.equals("goto")) {
            qtdInstrucoes += SimpleInstrucoesEnum.GOTO.getQtdInstrucoes();
        } else if(tipoOperacao.equals("print")) {
            qtdInstrucoes += SimpleInstrucoesEnum.PRINT.getQtdInstrucoes();
        } else if(tipoOperacao.equals("rem")) {
            qtdInstrucoes += SimpleInstrucoesEnum.REM.getQtdInstrucoes();
        } else if(tipoOperacao.equals("end")){
            qtdInstrucoes += SimpleInstrucoesEnum.END.getQtdInstrucoes();
        }
    }

    /**
     * Método responsável pro retornar a quantidade de instruções que serão criadas na hora de realizar a conversão da linguagem SIMPLE para SML.
     * @return
     */
    public Integer getQtdInstrucoes() {
        return this.qtdInstrucoes;
    }
}