package com.github.ammirante.yassunaga;

/**
 * Desenvolvido por: Douglas Ammirante da Cunha - 1712130040 
 * Gabriel Bueno Yassunaga - 1912130023
 * 
 * Enum responsável por armazenar os códigos da SML.
 */
public enum CodigoSMLEnum {

    READ(10),
    WRITE(11),
    LOAD(20),
    STORE(21),
    ADD(30),    
    SUBTRACT(31),
    DIVIDE(32),
    MULTIPLY(33),
    BRANCH(40),
    BRANCHNEG(41),
    BRANCHZERO(42),
    HALT(43);

    private Integer codigoSML;

    /**
     * Construtor da classe.
     * @param codigoSML
     */
    private CodigoSMLEnum(Integer codigoSML) {
        this.codigoSML = codigoSML;
    }

    /**
     * 
     * @return
     */
    public Integer getCodigoSML() {
        return this.codigoSML;
    }
}