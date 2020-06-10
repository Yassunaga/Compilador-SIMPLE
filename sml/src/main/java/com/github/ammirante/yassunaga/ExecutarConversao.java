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
        String SIMPLE_INPUT = "C:\\git\\estudo\\Interpretador-SIMPLE\\sml\\src\\main\\java\\com\\github\\ammirante\\yassunaga\\entrada.txt";
        String SIMPLE_OUTPUT = "C:\\git\\estudo\\Interpretador-SIMPLE\\sml\\src\\main\\java\\com\\github\\ammirante\\yassunaga\\saida.txt";

        ManipularArquivo manipulador = new ManipularArquivo(SIMPLE_INPUT, SIMPLE_OUTPUT);
        Transcrever transcrever = new Transcrever();

        String linha = null;
        String operacao = null;
        String expressao[];
        Integer numeroLinha;

        while (!"end".equals(operacao)) {
            linha = manipulador.getLinha();
            operacao = ExtrairArgumento.extrairOperacao(linha);
            expressao = ExtrairArgumento.extrairExpressao(linha);
            numeroLinha = ExtrairArgumento.extrairNumeroLinha(linha);

            switch (operacao) {
                case "rem":
                    break;
                case "input":
                    manipulador.escreverNoArquivo(transcrever.funcInput(expressao[1], numeroLinha), numeroLinha);
                    break;
                case "print":
                    manipulador.escreverNoArquivo(transcrever.funcPrint(expressao[1], numeroLinha), numeroLinha);
                    break;
                case "end": 
                    manipulador.escreverNoArquivo(transcrever.funcEnd(numeroLinha), numeroLinha); 
                    break;
                case "goto":
                    manipulador.escreverNoArquivo(transcrever.funcGoto(expressao[1], numeroLinha), numeroLinha);
                    break;
                case "if":
                    manipulador.escreverNoArquivo(transcrever.funcIfgoto(ExtrairArgumento.extrairVariaveisRelacional(linha), ExtrairArgumento.extrairOperadorRelacional(linha), ExtrairArgumento.extrairNumeroLinhaGoto(linha), numeroLinha), numeroLinha); 
                    break;
                case "let":
                    manipulador.escreverNoArquivo(transcrever.funcLet(ExtrairArgumento.extrairVariaveisOperador(linha), ExtrairArgumento.extrairOperador(linha), numeroLinha), numeroLinha); 
                    break;
            }
        }
        manipulador.fecharArquivo();
        manipulador.substituirValoresNulos(transcrever.getMapaOcorrenciaFaltantes(), SIMPLE_OUTPUT);
    }
}
