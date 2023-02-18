package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

public class JnzInstruction extends Instruction {

    private final RegisterName result;

    private final String prior_labelA;

    public static final String OP_CODE = "jnz";

    public JnzInstruction(String label, RegisterName result, String prior_label) {
        super(label, OP_CODE);
        this.result = result;
        this.prior_labelA = prior_label;

    }

    @Override
    public int execute(Machine m) throws NullPointerException{
        int value = m.getRegisters().get(result);
        if (value == 0)
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        return m.getLabels().getAddress(prior_labelA);
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
        return getLabelString() + getOpcode() + " " + result + " " + prior_labelA;
    }
}
