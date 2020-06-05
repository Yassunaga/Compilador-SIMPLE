package com.github.ammirante.yassunaga;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por executar o código
 */
public class ExecutarConversao {
    public static void main(String[] args) throws Exception {
        String SIMPLE_INPUT = "/home/paulin...";
        String SIMPLE_OUTPUT = "/home/paulin...";

        System.out.println(ExtrairArgumento.extrairOperacao("10 rem dasdasdsada"));
        ManipularArquivo manipulador = new ManipularArquivo(SIMPLE_INPUT, SIMPLE_OUTPUT);        
        Transcrever transcrever = new Transcrever();

        String linha = null;
        String operacao = null;
        String expressao = null;
        String instrucao = null;

        while(true){
            linha = manipulador.getLinha();
            operacao = ExtrairArgumento.extrairOperacao(linha);
            expressao = ExtrairArgumento.extrairExpressao(linha);

            switch(operacao){
                case "rem":
                    break;
                case "input":
                    manipulador.escreverNoArquivo(executor.funcInput(expressao)); 
                    break;
                case "let":
                    manipulador.escreverNoArquivo(executor.funcLet(expressao));
                    break;
                case "print":
                    manipulador.escreverNoArquivo(executor.funcPrint(expressao));
                    break;
                case "goto":
                    manipulador.escreverNoArquivo(executor.funcGoto(expressao));
                    break;
                case "if/goto":
                    manipulador.escreverNoArquivo(executor.funcIfgoto(expressao));
                    break;
                case "end":
                    manipulador.escreverNoArquivo(executor.funcEnd(expressao));
                    break;
            }
        }
        
        manipulador.fecharArquivo();
    }
}
