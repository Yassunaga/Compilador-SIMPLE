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

/**
 * Exceção na leitura do arquivo contendo o código-fonte para a memória da
 * Simpletron Machine
 */
public class LoadException extends Exception {
    /**
     * Identificador de serialização da classe
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new LoadException with null as its detail message
     */
    public LoadException() {
        super();
    }

    /**
     * Constructs a new LoadException with the specified detail message
     *
     * @param message the detail message
     */
    public LoadException(String message) {
        super(message);
    }

    /**
     * Constructs a new LoadException with the specified detail message and cause
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new LoadException with the specified cause and a detail message
     * of (cause==null ? null : cause.toString()) (which typically contains the
     * class and detail message of cause)
     *
     * @param cause the cause
     */
    public LoadException(Throwable cause) {
        super(cause);
    }
}