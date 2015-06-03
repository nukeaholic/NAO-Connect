package com.example.nao.nao_connect.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehanss on 11.05.2015.
 */
public class Command implements Cloneable {

    private static List<Command> avialableCommands;

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

    public static List<Command> getAviableCommands(){
        if (null == avialableCommands){
            avialableCommands = new ArrayList<Command>();
            avialableCommands.add(new Command("Nach vorne gehen"));
            avialableCommands.add(new Command("Nach Links gehen"));
            avialableCommands.add(new Command("Nach rechts gehen"));
            avialableCommands.add(new Command("Rückwärts gehen"));
            avialableCommands.add(new Command("Sich nach links drehen"));
            avialableCommands.add(new Command("Sich nach rechts drehen"));
            avialableCommands.add(new Command("Sich umdrehen"));
            avialableCommands.add(new Command("Winken"));
            avialableCommands.add(new Command("Hallo sagen"));
            avialableCommands.add(new Command("Linker Arm hoch"));
            avialableCommands.add(new Command("Rechter Arm hoch"));
            avialableCommands.add(new Command("Beide Arme hoch"));
            avialableCommands.add(new Command("Rechter Arm runter"));
            avialableCommands.add(new Command("Linker Arm runter"));
            avialableCommands.add(new Command("Beide Arme runter"));
        }
        return avialableCommands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        if (!label.equals(command.label)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}

