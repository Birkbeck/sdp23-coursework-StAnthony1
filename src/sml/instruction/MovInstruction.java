package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class moves an integer value into a register
 * @author oneilo1
 */
public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final int num;
    public static final String OP_CODE = "mov";

    /**
     * Constructor: An MovInstruction with a target register and an integer value
     * @param label can be type String or null
     * @param result must be of type RegisterName
     * @param i must be an Integer
     */
    public MovInstruction(String label, RegisterName result, int i) {
        super(label, OP_CODE);
        this.result = result;
        this.num = i;
    }

    /**
     * Executes the MovInstruction in the given machine instance,
     * allocates i to result
     * Precondition: the machine parameter must have RegisterNames within its Registers
     * which match those passed to the AddInstruction's constructor.
     * @param m some instance of machine
     * @return int
     */
    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, num);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * tests for equality between two MovInstruction instances. Fields
     * label, result and i must be equal for the two instances to
     * be equal.
     *
     * @return Boolean
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof MovInstruction other) {
            return Objects.equals(this.label, other.label)
                    && Objects.equals(this.result, other.result)
                    && Objects.equals(this.num, other.num);
        }
        return false;
    }

    /**
     * @return hash code for this MovInstruction.
     */
    @Override
    public int hashCode(){
        return Objects.hash(label, result, num);
    }

    /**
     * String representation of the instruction.
     *
     * @return pretty formatted version of the instruction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + num;
    }
}
