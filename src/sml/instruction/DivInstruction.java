package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class DivInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "div";

    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) throws ArithmeticException{
        try {
            int value1 = m.getRegisters().get(result);
            int value2 = m.getRegisters().get(source);
            m.getRegisters().set(result, value1 / value2);
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        }
        catch (ArithmeticException ex){
            throw new ArithmeticException();
        }
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
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

}
