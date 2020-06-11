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
        PreparadorArquivo preparadorArquivo = new PreparadorArquivo(manipulador);
        Mapas mapas = preparadorArquivo.prepararArquivoSaida();
        Transcrever transcrever = new Transcrever(mapas);
        String linhaEntrada = null;
        String operacao = null;
        String expressao[];
        Integer numeroLinhaSimple;

        while (!"end".equals(operacao)) {
            linhaEntrada = manipulador.getLinhaEntrada();
            operacao = ExtrairArgumento.extrairOperacao(linhaEntrada);
            expressao = ExtrairArgumento.extrairExpressao(linhaEntrada);
            numeroLinhaSimple = ExtrairArgumento.extrairNumeroLinhaSimple(linhaEntrada);

            switch (operacao) {
                case "rem":
                    break;
                case "input":
                    manipulador.escreverNoArquivo(transcrever.funcInput(expressao[1], numeroLinhaSimple), numeroLinhaSimple);
                    break;
                case "print":
                    manipulador.escreverNoArquivo(transcrever.funcPrint(expressao[1], numeroLinhaSimple), numeroLinhaSimple);
                    break;
                case "end": 
                    manipulador.escreverNoArquivo(transcrever.funcEnd(numeroLinhaSimple), numeroLinhaSimple); 
                    break;
                case "goto":
                    manipulador.escreverNoArquivo(transcrever.funcGoto(expressao[1], numeroLinhaSimple), numeroLinhaSimple);
                    break;
                case "if":
                    manipulador.escreverNoArquivo(transcrever.funcIfgoto(ExtrairArgumento.extrairVariaveisRelacional(linhaEntrada), ExtrairArgumento.extrairOperadorRelacional(linhaEntrada), ExtrairArgumento.extrairNumeroLinhaGoto(linhaEntrada), numeroLinhaSimple), numeroLinhaSimple); 
                    break;
                case "let":
                    manipulador.escreverNoArquivo(transcrever.funcLet(ExtrairArgumento.extrairVariaveisOperador(linhaEntrada), ExtrairArgumento.extrairOperador(linhaEntrada), numeroLinhaSimple), numeroLinhaSimple); 
                    break;
            }
        }
        manipulador.fecharArquivo();
        manipulador.substituirValoresNulos(transcrever.getMapas(), SIMPLE_OUTPUT);
    }
}
