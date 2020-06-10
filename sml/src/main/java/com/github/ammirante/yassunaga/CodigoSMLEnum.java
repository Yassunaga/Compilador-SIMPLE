package com.github.ammirante.yassunaga;

/**
 * 
 * 
 */
public enum CodigoSMLEnum {

    READ(10),
    LOAD(20),
    WRITE(11),
    HALT(43),
    BRANCH(40),
    SUBTRACT(31),
    BRANCHNEG(41),
    BRANCHZERO(42);


    private Integer codigoSML;

    /**
     * Construtor da classe.
     * @param codigoSML
     */
    private CodigoSMLEnum(Integer codigoSML) {
        this.codigoSML = codigoSML;
    }

    public Integer getCodigoSML() {
        return this.codigoSML;
    }
}