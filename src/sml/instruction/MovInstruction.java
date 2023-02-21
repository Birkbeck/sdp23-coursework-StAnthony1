package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final int num;
    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName result, int i) {
        super(label, OP_CODE);
        this.result = result;
        this.num = i;
    }

    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, num);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }
    @Override
    //TODO: implement method
    public boolean equals(Object o) {
        return true;
    }

    @Override
    //TODO: implement method
    public int hashCode(){
        return 1;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + num;
    }
}
