package com.asnovikova.bpmcamunda.model;

/**
 * @author Anna Novikova
 */
public class CatFact {

    private String fact;
    private Integer length;

    public CatFact() {
    }

    public CatFact(String fact, Integer length) {
        this.fact = fact;
        this.length = length;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
