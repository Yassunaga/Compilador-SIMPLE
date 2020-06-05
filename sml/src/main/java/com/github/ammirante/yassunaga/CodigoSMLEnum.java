package com.github.ammirante.yassunaga;

/**
 * 
 * 
 */
public enum CodigoSMLEnum {

    LOAD(20);

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