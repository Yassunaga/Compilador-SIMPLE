package com.github.ammirante.yassunaga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável por preparar o arquivo de saída.
 */
public class PreparadorArquivo{

    private ContadorInstrucoes contadorInstrucoes;
    private ManipularArquivo manipularArquivo;
    private static final String VARIAVEL = "+0000";
    private Integer contadorLinhas = 0;
    private List<String> linhasPreenchidasArquivo = new ArrayList<>();

    /**
     * Construtor da classe.
     * @param manipularArquivo
     */
    public PreparadorArquivo(ManipularArquivo manipularArquivo){
        this.manipularArquivo = manipularArquivo;
        contadorInstrucoes = new ContadorInstrucoes(manipularArquivo);
    }

    public List<String> getLinhasPreenchidasArquivo() {
        return this.linhasPreenchidasArquivo;
    }

    /**
     * Método responsável por retornar a instância do manipulador de arquivo.
     * @return
     */
    public ManipularArquivo getManipularArquivo() {
        return this.manipularArquivo;
    }

    /**
     * Método responsável por realizar a preparação do arquivo de saída.
     * @return {@link Mapas}
     */
    public Mapas prepararArquivoSaida() throws IOException {
        Mapas mapas = new Mapas();
        Integer qtdInstrucoes = contadorInstrucoes.getQtdInstrucoes();
        // Integer qtdLinhasArquivo = qtdInstrucoes + mapas.getQuantidadeVariaveis();
        criarLinhasVazias(qtdInstrucoes);
        this.contadorLinhas = qtdInstrucoes;
        List<String> variaveis = ExtrairArgumento.extrairVariaveis(manipularArquivo.getLinhasEntrada());
        variaveis = variaveis.stream().distinct().collect(Collectors.toList());
        
        String variavelSML = null;
        for(String variavel : variaveis){
            if(isConstante(variavel)){
                variavelSML = montarConstante(variavel);
                linhasPreenchidasArquivo.add(variavelSML + "\n");
            }
            else {
                linhasPreenchidasArquivo.add(VARIAVEL + "\n");
            }
            
            // Montando mapa das variáveis chave = variável, valor = linha
            mapas.mapaVariavelToEnderecoSML.put(variavel, contadorLinhas);
            contadorLinhas++;
        }

        return mapas;
    }

    /**
     * Método responsável por preparar a constante.
     * @param variavel
     * @return
     */
    private String montarConstante(String variavel) {
        String variavelTemp = variavel;
        String SINAL; 
        if(variavel.contains("-")) {
            SINAL = "-";
            variavelTemp = variavel.replaceAll("-", "");
        }
        else{
            SINAL = "+";
        }
        if(variavelTemp.length() == 1) {
            return SINAL + "000" + variavelTemp;
        } else if(variavelTemp.length() == 2) {
            return SINAL + "00" + variavelTemp;
        } else if(variavelTemp.length() == 3) {
            return SINAL + "0" + variavelTemp;
        }
        return SINAL + variavelTemp;        
    }
    
    /**
     * Método responsável por verificar se a variável é uma constante ou não.
     * @param variavel
     * @return
     */
    private Boolean isConstante(String variavel){
        try{
            Integer.parseInt(variavel);
            return Boolean.TRUE;
        } catch(NumberFormatException e){
            return Boolean.FALSE;
        }
    }

    /**
     * Método responsável por criar linhas vazias.
     * @param qtdLinhas
     */
    private void criarLinhasVazias(Integer qtdLinhas) throws IOException{
        for(int i=0; i<qtdLinhas; i++){
            linhasPreenchidasArquivo.add("");
        }
    }

}