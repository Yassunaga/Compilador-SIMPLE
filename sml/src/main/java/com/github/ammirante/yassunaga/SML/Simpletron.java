package com.github.ammirante.yassunaga.SML;
/*************************************************************************
 * Copyright (C) 2009/2020 - Cristiano Lehrer (cristiano@ybadoo.com.br)  *
 *                  Ybadoo - Solucoes em Software Livre (ybadoo.com.br)  *
 *                                                                       *
 * Permission is granted to copy, distribute and/or modify this document *
 * under the terms of the GNU Free Documentation License, Version 1.3 or *
 * any later version published by the  Free Software Foundation; with no *
 * Invariant Sections,  no Front-Cover Texts, and no Back-Cover Texts. A *
 * A copy of the  license is included in  the section entitled "GNU Free *
 * Documentation License".                                               *
 *                                                                       *
 * Ubuntu 16.10 (GNU/Linux 4.8.0-39-generic)                             *
 * OpenJDK Version "1.8.0_121"                                           *
 * OpenJDK 64-Bit Server VM (build 25.121-b13, mixed mode)               *
 *************************************************************************/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Classe responsavel pela simulacao da Simpletron Machine
 */
public class Simpletron {
    /**
     * Quantidade de palavras armazenadas na memoria
     */
    public static final int MEMORY = 100;

    /**
     * Le uma palavra do teclado para uma posicao especifica da memoria
     */
    public static final int READ = 10;

    /**
     * Escreve na tela uma palavra de uma posicao especifica da memoria
     */
    public static final int WRITE = 11;

    /**
     * Carrega uma palavra de uma posicao especifica na memoria para o acumulador
     */
    public static final int LOAD = 20;

    /**
     * Armazena uma palavra do acumulador em uma posicao especifica na memoria
     */
    public static final int STORE = 21;

    /**
     * Adiciona uma palavra de uma posicao especifica na memoria a palavra
     * armazenada no acumulador
     */
    public static final int ADD = 30;

    /**
     * Subtrai uma palavra de uma posicao especifica na memoria a palavra armazenada
     * no acumulador
     */
    public static final int SUBTRACT = 31;

    /**
     * Divide uma palavra de uma posicao especifica na memoria a palavra armazenada
     * no acumulador
     */
    public static final int DIVIDE = 32;

    /**
     * Multiplica uma palavra de uma posicao especifica na memoria a palavra
     * armazenada no acumulador
     */
    public static final int MULTIPLY = 33;

    /**
     * Desvia para uma posicao especifica na memoria
     */
    public static final int BRANCH = 40;

    /**
     * Desvia para uma posicao especifica na memoria se o acumulador for negativo
     */
    public static final int BRANCHNEG = 41;

    /**
     * Desvia para uma posicao especifica na memoria se o acumulador for zero
     */
    public static final int BRANCHZERO = 42;

    /**
     * Finaliza o programa
     */
    public static final int HALT = 43;

    /**
     * Formato numerico dos dados da memoria
     */
    private final DecimalFormat memoryFormatter;

    /**
     * Memoria da Simpletron Machine
     */
    private int[] memory;

    /**
     * Acumulador da Simpletron Machine
     */
    private int accumulator;

    /**
     * Contador de instrucoes da Simpletron Machine
     */
    private int instructionCounter;

    /**
     * Codigo da operacao
     */
    private int operationCode;

    /**
     * Operando
     */
    private int operand;

    /**
     * Leitor do teclado
     */
    private Scanner scanner;

    /**
     * Se a Simpletron Machine esta em processamento
     */
    private boolean processing;

    /**
     * Construtor padrao
     */
    public Simpletron() {
        processing = false;

        memory = new int[MEMORY];

        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }

        accumulator = 0;
        instructionCounter = 0;
        operationCode = 0;
        operand = 0;

        scanner = new Scanner(System.in);

        memoryFormatter = new DecimalFormat("+0000;-0000");
    }

    /**
     * Ler o arquivo contendo o codigo-fonte para a memoria da Simpletron Machine
     *
     * @throws LoadException problemas na leitura do codigo-fonte
     */
    private void load() throws LoadException {
        BufferedReader leitor = null;

        System.out.print("Please enter your file program: ");

        String source = scanner.nextLine();

        try {
            leitor = new BufferedReader(new FileReader(source));

            String line = leitor.readLine();

            int counter = 0;

            while (line != null) {
                int word = Integer.parseInt(line);

                if ((word >= -9999) && (word <= 9999)) {
                    memory[counter] = word;

                    counter = counter + 1;
                } else {
                    throw new NumberFormatException();
                }

                line = leitor.readLine();
            }
        } catch (FileNotFoundException exception) {
            throw new LoadException("error : attempt to file not found!", exception);
        } catch (IOException exception) {
            throw new LoadException("error : attempt to invalid file!", exception);
        } catch (NumberFormatException exception) {
            throw new LoadException("error : attempt to invalid instruction!", exception);
        } finally {
            try {
                leitor.close();
            } catch (IOException exception) {
                /*
                 * nothing to do
                 */
            }
        }

        System.out.println("Simpletron loading completed!");
    }

    /**
     * Interpretar a instrucao READ
     */
    private void readInstruction() {
        System.out.print("input : ");

        int number = 0;

        try {
            number = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            number = Integer.MIN_VALUE;
        }

        if ((number >= -9999) && (number <= 9999)) {
            memory[operand] = number;

            instructionCounter = instructionCounter + 1;
        } else {
            System.out.println("error : attempt to invalid number!");
        }
    }

    /**
     * Interpretar a instrucao WRITE
     */
    private void writeInstruction() {
        System.out.print("output: ");
        System.out.println(memoryFormatter.format(memory[operand]));

        instructionCounter = instructionCounter + 1;
    }

    /**
     * Interpretar a instrucao LOAD
     */
    private void loadInstruction() {
        accumulator = memory[operand];

        instructionCounter = instructionCounter + 1;
    }

    /**
     * Interpretar a instrucao STORE
     */
    private void storeInstruction() {
        memory[operand] = accumulator;

        instructionCounter = instructionCounter + 1;
    }

    /**
     * Interpretar a instrucao ADD
     *
     * @throws InterpretException problemas na interpretacao da instrucao ADD
     */
    private void addInstruction() throws InterpretException {
        accumulator = accumulator + memory[operand];

        if ((accumulator >= -9999) && (accumulator <= 9999)) {
            instructionCounter = instructionCounter + 1;
        } else {
            throw new InterpretException("error : attempt to accumulator overflow!");
        }
    }

    /**
     * Interpretar a instrucao SUBTRACT
     *
     * @throws InterpretException problemas na interpretacao da instrucao SUBTRACT
     */
    private void subtractInstruction() throws InterpretException {
        accumulator = accumulator - memory[operand];

        if ((accumulator >= -9999) && (accumulator <= 9999)) {
            instructionCounter = instructionCounter + 1;
        } else {
            throw new InterpretException("error : attempt to accumulator overflow!");
        }
    }

    /**
     * Interpretar a instrucao DIVIDE
     *
     * @throws InterpretException problemas na interpretacao da instrucao DIVIDE
     */
    private void divideInstruction() throws InterpretException {
        if (memory[operand] != 0) {
            accumulator = accumulator / memory[operand];

            instructionCounter = instructionCounter + 1;
        } else {
            throw new InterpretException("error : attempt to divide by zero!");
        }
    }

    /**
     * Interpretar a instrucao MULTIPLY
     *
     * @throws InterpretException problemas na interpretacao da instrucao MULTIPLY
     */
    private void multiplyInstruction() throws InterpretException {
        accumulator = accumulator * memory[operand];

        if ((accumulator >= -9999) && (accumulator <= 9999)) {
            instructionCounter = instructionCounter + 1;
        } else {
            throw new InterpretException("error : attempt to accumulator overflow!");
        }
    }

    /**
     * Interpretar a instrucao BRANCH
     */
    private void branchInstruction() {
        instructionCounter = operand;
    }

    /**
     * Interpretar a instrucao BRANCHNEG
     */
    private void branchnegInstruction() {
        if (accumulator < 0) {
            instructionCounter = operand;
        } else {
            instructionCounter = instructionCounter + 1;
        }
    }

    /**
     * Interpretar a instrucao BRANCHZERO
     */
    private void branchzeroInstruction() {
        if (accumulator == 0) {
            instructionCounter = operand;
        } else {
            instructionCounter = instructionCounter + 1;
        }
    }

    /**
     * Interpretar a instrucao HALT
     */
    private void haltInstruction() {
        processing = false;
    }

    /**
     * Interpretar o codigo escrito em Simpletron Machine Language
     */
    private void interpret() throws InterpretException {
        System.out.println("Simpletron execution begins!");

        processing = true;

        while (processing) {
            int instructionRegister = memory[instructionCounter];

            operationCode = instructionRegister / 100;

            operand = instructionRegister % 100;

            switch (operationCode) {
                case READ:
                    readInstruction();
                    break;
                case WRITE:
                    writeInstruction();
                    break;
                case LOAD:
                    loadInstruction();
                    break;
                case STORE:
                    storeInstruction();
                    break;
                case ADD:
                    addInstruction();
                    break;
                case SUBTRACT:
                    subtractInstruction();
                    break;
                case DIVIDE:
                    divideInstruction();
                    break;
                case MULTIPLY:
                    multiplyInstruction();
                    break;
                case BRANCH:
                    branchInstruction();
                    break;
                case BRANCHNEG:
                    branchnegInstruction();
                    break;
                case BRANCHZERO:
                    branchzeroInstruction();
                    break;
                case HALT:
                    haltInstruction();
                    break;
                default:
                    throw new InterpretException("error : attempt to unknown instruction!");
            }
        }

        System.out.println("Simpletron execution terminated!");
    }

    /**
     * Apresentar o dump da Simpletron Machine
     */
    private void dump() {
        DecimalFormat variableFormatter = new DecimalFormat("   00");

        System.out.println();

        System.out.println("REGISTERS:");

        String temp = memoryFormatter.format(accumulator).substring(0, 5);
        System.out.println(temp + " Accumulator");

        temp = variableFormatter.format(instructionCounter);
        System.out.println(temp + " Instruction Counter");

        temp = memoryFormatter.format(memory[instructionCounter]);
        System.out.println(temp + " Instruction Register");

        temp = variableFormatter.format(operationCode);
        System.out.println(temp + " Operation Code");

        temp = variableFormatter.format(operand);
        System.out.println(temp + " Operand");

        System.out.println();

        System.out.println("MEMORY:");

        System.out.print(" ");

        for (int i = 0; i < 10; i++) {
            System.out.print("     " + i);
        }

        System.out.println();

        for (int i = 0; i < memory.length; i += 10) {
            System.out.print((i / 10) + " ");

            for (int j = i; j < i + 10; j++) {
                System.out.print(memoryFormatter.format(memory[j]) + " ");
            }

            System.out.println();
        }
    }

    /**
     * Executar a Simpletron Machine
     */
    public void execute() {
        System.out.println("Welcome to Simpletron!");

        try {
            load();

            interpret();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

            System.out.println("Simpletron execution abnormally terminated!");
        } finally {
            dump();
        }
    }

    /**
     * Metodo principal da linguagem de programacao Java
     *
     * @param args argumentos da linha de comando (nao utilizado)
     */
    public static void main(String[] args) {
        Simpletron simpletron = new Simpletron();

        simpletron.execute();
    }
}