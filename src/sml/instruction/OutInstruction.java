package sml.instruction;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;


/**
 * This class prints the contents of a single register
 * @author oneilo1
 */
public class OutInstruction extends Instruction{
    private final RegisterName result;

    public static final String OP_CODE = "out";

    /**
     * Constructor: An OutInstruction with a single target Register whose integer value will be printed.
     * @param label can be type String or null
     * @param result must be of type RegisterName
     */
    public OutInstruction(String label, RegisterName result) {
        super(label, OP_CODE);
        this.result = result;
    }


    /**
     * Executes the OutInstruction in the given machine instance,
     * Prints the contents of result register
     * Precondition: the machine parameter must have RegisterNames within its Registers
     * which match those passed to the OutInstruction's constructor.
     * @param m some instance of machine
     * @return int
     */
    @Override
    public int execute(Machine m) {
        System.out.print(m.getRegisters().get(result)+"\n");
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * tests for equality between two OutInstruction instances. Fields
     * label, result and source must be equal for the two instances to
     * be equal.
     *
     * @return Boolean
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof OutInstruction other) {
            return Objects.equals(this.label, other.label)
                    && Objects.equals(this.result, other.result);
        }
        return false;
    }

    /**
     * @return hash code for this OutInstruction.
     */
    @Override
    public int hashCode(){
        return Objects.hash(label, result);
    }

    /**
     * String representation of the instruction.
     *
     * @return pretty formatted version of the instruction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result;
    }
}
