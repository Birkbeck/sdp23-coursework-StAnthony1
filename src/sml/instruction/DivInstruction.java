package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class divides two integers and stores the result in a Register
 * @author oneilo1
 */
public class DivInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "div";

    /**
     * Constructor: An DivInstruction with two target RegisterNames.
     * The result will be stored in the register passed to the result param.
     * @param label can be type String or null
     * @param result must be of type RegisterName
     * @param source must be of type RegisterName
     */
    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the DivInstruction in the given machine instance,
     * altering that values within those registers.
     * Precondition: the machine parameter must have RegisterNames within its Registers
     * which match those passed to the DivInstruction's constructor.
     * @param m some instance of machine
     * @return int
     */
    @Override
    public int execute(Machine m) throws ArithmeticException{
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 / value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * tests for equality between two DivInstruction instances. Fields
     * label, result and source must be equal for the two instances to
     * be equal.
     *
     * @return Boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof DivInstruction other) {
            return Objects.equals(this.label, other.label)
                    && Objects.equals(this.result, other.result)
                    && Objects.equals(this.source, other.source);
        }
        return false;
    }

    /**
     * @return hash code for this DivInstruction.
     */
    @Override
    public int hashCode(){
        return Objects.hash(label, result, source);
    }

    /**
     * String representation of the instruction.
     *
     * @return pretty formatted version of the instruction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

}

