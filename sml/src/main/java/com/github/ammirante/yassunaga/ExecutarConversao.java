package com.github.ammirante.yassunaga;

import java.io.IOException;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 Gabriel Bueno
 * Yassunaga - 1912130023
 * 
 * Classe responsável por executar o código
 */
public class ExecutarConversao {
    public static void main(String[] args) throws Exception, IOException {
        String SIMPLE_INPUT = "D:\\Faculdade Douglas\\Interpretador-SML\\sml\\src\\main\\java\\com\\github\\ammirante\\yassunaga\\entrada.txt";
        String SIMPLE_OUTPUT = "D:\\Faculdade Douglas\\Interpretador-SML\\sml\\src\\main\\java\\com\\github\\ammirante\\yassunaga\\saida.txt";

        ManipularArquivo manipulador = new ManipularArquivo(SIMPLE_INPUT, SIMPLE_OUTPUT);
        Transcrever transcrever = new Transcrever();

        String linha = null;
        String operacao = null;
        String expressao[] = null;

        while (!"end".equals(operacao)) {
            linha = manipulador.getLinha();
            operacao = ExtrairArgumento.extrairOperacao(linha);
            expressao = ExtrairArgumento.extrairExpressao(linha);

            switch (operacao) {
                case "rem":
                    break;
                case "input":
                    manipulador.escreverNoArquivo(transcrever.funcInput(expressao[1]));
                    break;
                case "end": 
                    // manipulador.escreverNoArquivo(transcrever.funcEnd(expressao)); 
                    break;
                /*
                 * case "let": manipulador.escreverNoArquivo(transcrever.funcLet(expressao));
                 * break; case "print":
                 * manipulador.escreverNoArquivo(transcrever.funcPrint(expressao)); break; case
                 * "goto": manipulador.escreverNoArquivo(transcrever.funcGoto(expressao));
                 * break; case "if/goto":
                 * manipulador.escreverNoArquivo(transcrever.funcIfgoto(expressao)); break; 
                 */
                
            }
        }

        manipulador.fecharArquivo();
    }
}
