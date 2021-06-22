package frc.robot.auto;

import java.util.ArrayList;

public class AutoSequence {
    private ArrayList<AutoInstruction> instructions;
    private int currentInstruction;

    public AutoSequence() {
        instructions = new ArrayList<AutoInstruction>();
    }

    public void Execute() {
        if (instructions.get(currentInstruction).execute())
            currentInstruction++;
    }

    public void addAll(AutoInstruction[] instructions) {
        for (AutoInstruction instruction : instructions)
            this.instructions.add(instruction);
    }

    public void add(AutoInstruction instruction) {
        instructions.add(instruction);
    }

    public void remove(AutoInstruction instruction) {
        instructions.remove(instruction);
    }

    public int getCurrentIndex() {
        return currentInstruction;
    }

    public AutoInstruction getInstruction(int index) {
        return instructions.get(index);
    }

    public AutoInstruction getCurrentInstruction() {
        return instructions.get(currentInstruction);
    }
    
}
