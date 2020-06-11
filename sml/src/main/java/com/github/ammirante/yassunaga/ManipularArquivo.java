package com.github.ammirante.yassunaga;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Classe responsável manipular o arquivo.
 */
public class ManipularArquivo {

    private Map<Integer, String> textoArquivo = new HashMap<Integer, String>();
    private PrintWriter gravarArq;
    private FileWriter arquivo;
    private Integer index = 0;
    private String linhaArquivo = "00";
    private Map<Integer, String> mapaOcorrencias = new HashMap<>();
    // private static String VARIAVEL_LET;
    private List<String> listaArquivo = new ArrayList<>();
    private String caminhoArquivoSaida;
    private List<String> lstLinhasEntrada = new ArrayList<>();

    /**
     * Construtor da classe.
     * 
     * @param caminhoArquivoLeitura
     * @param caminhoArquivoEscrita
     * @throws Exception
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ManipularArquivo(String caminhoArquivoLeitura, String caminhoArquivoEscrita) throws Exception {
        this.caminhoArquivoSaida = caminhoArquivoEscrita;
        this.criarArquivo(caminhoArquivoEscrita);
        this.lerArquivo(caminhoArquivoLeitura);
    }

    /**
     * Método responsável por criar o arquivo.
     * @param caminhoArquivoEscrita
     * @throws IOException
     */
    private void criarArquivo(String caminhoArquivoEscrita) throws IOException {
        arquivo = new FileWriter(caminhoArquivoEscrita, Boolean.TRUE);
        gravarArq = new PrintWriter(arquivo);
    }

    /**
     * Método responsável por appendar no arquivo.
     * @param linha
     * @param numeroLinha
     */
    public void escreverNoArquivo(String linha, Integer numeroLinha) {
        gravarArq.println(linha);
        this.mapaOcorrencias.put(numeroLinha, this.linhaArquivo);
        incrementarEndereco();
    }

    /**
     * Método responsável por appendar várias linhas no arquivo.
     * @param lstLinhas
     */
    public void escreverNoArquivo(List<String> lstLinhas){
        for(String linha : lstLinhas){
            gravarArq.print(linha);
        }
    }

    /**
     * Método responsável por appendar uma lista no arquivo.
     * @param lstLinhas
     * @param numeroLinha
     */
    public void escreverNoArquivo(List<String> lstLinhas, Integer numeroLinha) {
    	this.mapaOcorrencias.put(numeroLinha, this.linhaArquivo);
        for(String linha : lstLinhas) {
            gravarArq.println(linha);
            incrementarEndereco();
        }
    }

    public void escreverNoArquivo(String linha){
        gravarArq.println(linha);
    }

    /**
     * Método responsável por fechar o arquivo.
     */
    public void fecharArquivo() throws IOException {
        try {
            arquivo.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Método responsável por ler o arquivo.
     * 
     * @param caminhoArquivo
     * @throws Exception
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void lerArquivo(String caminhoArquivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        
        Integer contadorLinha = 0;
        while (br.ready()) {
            String linha = br.readLine();
            textoArquivo.put(contadorLinha, linha);
            contadorLinha++;
            lstLinhasEntrada.add(linha);
        }

        try {
            br.close();
        } catch(IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Método responsável por retornar a lista de linhas do arquivo de entrada.
     * @return
     */
    public List<String> getLinhasEntrada() {
        return this.lstLinhasEntrada;
    }

    /**
     * Método responsável por let o arquivo de saída.
     * @throws IOException
     */
    public List<String> lerArquivoSaida() throws IOException{
        listaArquivo = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoSaida));
        while (br.ready()) {
            listaArquivo.add(br.readLine());
        }
        try {
            br.close();
        } catch(IOException e) {
            throw new IOException(e);
        }

        return listaArquivo;
    }

    /**
     * Método responsável por retornar o mapa com as linhas.
     * @return
     */
    public Map<Integer, String> getLinhas() {
        return this.textoArquivo;
    } 

    /**
     * Método responsável por retornar a linha que está no topo do arquivo.
     */
    public String getLinhaEntrada() {
        return textoArquivo.get(this.index++);
    }

    public String getLinhaEntrada(Integer indexLinha){
        return textoArquivo.get(indexLinha);
    }

    /**
     * Método responsável por varrer o arquivo a procura de valores nulos e
     * substituir os seus valores.
     * 
     * @param lstOcorrencias
     * @param mapaGoto
     * @throws IOException
     */
    public void substituirValoresNulos(Mapas mapas, String caminho) throws IOException {
        for(Map.Entry<Integer, Integer> mapa : mapas.mapaOcorrenciasFaltantes.entrySet()) {
            for(Map.Entry<Integer, Integer> mapa2 : mapas.mapaOcorrenciasFaltantes.entrySet()) {
                /*if(mapa2.getKey() == mapa.getValue()) {
                    try (Stream<String> all_lines = Files.lines(Paths.get(caminho))) {
                        String linha = all_lines.skip(Integer.valueOf(mapa2.getValue())).findFirst().get();
                        linha.split(" ");
                    }
                }*/
            }
        }
    }
    
    /**
     * Método responsável por incrementar o index do endereço.
     * @param idx
     * @return
     */
    private void incrementarEndereco() {
        Integer aux = Integer.parseInt(this.linhaArquivo);
        aux++;
        if(aux < 10) {
            String cont = "0" + aux;
            this.linhaArquivo = cont;
        } else {
        	this.linhaArquivo = aux.toString();
        }       
    }
}