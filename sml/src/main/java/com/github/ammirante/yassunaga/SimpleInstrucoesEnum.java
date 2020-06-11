package com.github.ammirante.yassunaga;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Enum responsável por armazenar a quantidade de instruções geradas por cada instrução simple.
 */
public enum SimpleInstrucoesEnum {
    
    REM(0),
    INPUT(1),
    PRINT(1),
    GOTO(1),
    END(1);

    private Integer qtdInstrucoes;

    /**
     * Construtor do enum.
     * @param qtdInstrucoes
     */
    private SimpleInstrucoesEnum(Integer qtdInstrucoes) {
        this.qtdInstrucoes = qtdInstrucoes;
    }

    /**
     * 
     * @return
     */
    public Integer getQtdInstrucoes() {
        return this.qtdInstrucoes;
    }
 
    /**
     * Método responsável por retornar a quantidade de instruções de acordo com o operador.
     * @param operador
     * @return
     */
    public static Integer getQtdInstrucoesByOperador(String operador) {
        if(operador.equals(">") || operador.equals("<") || operador.equals("==") || operador.equals("-") || operador.equals("+") || operador.equals("*") || operador.equals("/")) {
            return 3;
        } else if(operador.equals(">=") || operador.equals("<=")) {
            return 4;
        } else {
            return 6;
        }
    }
}