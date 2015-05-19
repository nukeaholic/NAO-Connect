package com.example.nao.nao_connect.model;

/**
 * Created by ehanss on 11.05.2015.
 */
public class Command implements Cloneable {

    private String label;

    public Command(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Command clone() {
        return new Command(label);
    }
}
