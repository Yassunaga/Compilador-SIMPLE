package com.github.ammirante.yassunaga;

import java.util.List;
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
        List<String> linhasSaida = preparadorArquivo.getLinhasPreenchidasArquivo();
        Integer index = 0;

        while (!"end".equals(operacao)) {
            linhaEntrada = manipulador.getLinhaEntrada();
            operacao = ExtrairArgumento.extrairOperacao(linhaEntrada);
            expressao = ExtrairArgumento.extrairExpressao(linhaEntrada);
            numeroLinhaSimple = ExtrairArgumento.extrairNumeroLinhaSimple(linhaEntrada);
            List<String> lstTmp;

            switch (operacao) {
                case "rem":
                    break;
                case "input":
                    linhasSaida.set(index++, transcrever.funcInput(expressao[1], numeroLinhaSimple));
                    manipulador.inserirNoMapaDeOcorrencias(numeroLinhaSimple);
                    break;
                case "print":
                    linhasSaida.set(index++, transcrever.funcPrint(expressao[1], numeroLinhaSimple));
                    manipulador.inserirNoMapaDeOcorrencias(numeroLinhaSimple);
                    break;
                case "end": 
                    linhasSaida.set(index++, transcrever.funcEnd(numeroLinhaSimple));
                    manipulador.inserirNoMapaDeOcorrencias(numeroLinhaSimple);
                    break;
                case "goto":
                    linhasSaida.set(index++, transcrever.funcGoto(expressao[1], numeroLinhaSimple));
                    manipulador.inserirNoMapaDeOcorrencias(numeroLinhaSimple);
                    break;
                case "if":
                    lstTmp = transcrever.funcIfgoto(ExtrairArgumento.extrairVariaveisRelacional(linhaEntrada), ExtrairArgumento.extrairOperadorRelacional(linhaEntrada), ExtrairArgumento.extrairNumeroLinhaGoto(linhaEntrada), numeroLinhaSimple);
                    for(String linha : lstTmp) {
                        linhasSaida.set(index++, linha);
                    }
                    manipulador.inserirNoMapaDeOcorrencias(numeroLinhaSimple);
                    break;
                case "let":
                    lstTmp = transcrever.funcLet(ExtrairArgumento.extrairVariaveisOperador(linhaEntrada), ExtrairArgumento.extrairOperador(linhaEntrada), numeroLinhaSimple);
                    for(String linha : lstTmp) {
                        linhasSaida.set(index++, linha);
                    }
                    manipulador.inserirNoMapaDeOcorrencias(numeroLinhaSimple);
                    break;
            }
        }
        linhasSaida = manipulador.substituirValoresNulos(transcrever.getMapas(), linhasSaida);
        manipulador.escreverNoArquivo(linhasSaida);
    }
}
