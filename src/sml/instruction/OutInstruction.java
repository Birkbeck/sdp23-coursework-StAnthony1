package sml.instruction;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class OutInstruction extends Instruction{
    private final RegisterName result;

    public static final String OP_CODE = "out";

    public OutInstruction(String label, RegisterName result) {
        super(label, OP_CODE);
        this.result = result;
    }

    @Override
    public int execute(Machine m) {
        System.out.print(m.getRegisters().get(result));
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
        return getLabelString() + getOpcode() + " " + result;
    }
}
