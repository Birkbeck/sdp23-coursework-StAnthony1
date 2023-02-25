package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class checks if a given register's value is not equal to zero and,
 * if not, alters the program counter in a machine instance so that the execution cycle
 * resumes from te position at which the label can be found in the program's instruction set.
 * If the value of the register is zero, JnzInstruction does not alter the
 * sequence of execution.
 * @author oneilo1
 */
public class JnzInstruction extends Instruction {

    private final RegisterName result;

    private final String prior_labelA;

    public static final String OP_CODE = "jnz";

    /**
     * Constructor: An JnzInstruction with a single target RegisterName and label.
     *
     * @param label can be type String or null
     * @param result must be of type RegisterName
     * @param prior_label must be a String
     */
    public JnzInstruction(String label, RegisterName result, String prior_label) {
        super(label, OP_CODE);
        this.result = result;
        this.prior_labelA = prior_label;

    }

    @Override
    /**
     * Executes the JnzInstruction in the given machine instance. If the target RegisterName
     * contains a zero value, the program counter moves back to the point at which the prior label
     * can be found in the machine's labels field.
     * Precondition: the machine parameter must have RegisterNames within its Registers
     * which match those passed to the AddInstruction's constructor and prior label must
     * match some key in machine's labels
     * @param m some instance of machine
     * @return int
     */
    public int execute(Machine m) throws NullPointerException{
        int value = m.getRegisters().get(result);
        if (value == 0)
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        return m.getLabels().getAddress(prior_labelA);
    }

    /**
     * tests for equality between two JnzInstruction instances. Fields
     * label, result and prior_labelA must be equal for the two instances to
     * be equal.
     *
     * @return Boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof JnzInstruction other) {
            return Objects.equals(this.label, other.label)
                    && Objects.equals(this.result, other.result)
                    && Objects.equals(this.prior_labelA, other.prior_labelA);
        }
        return false;
    }

    /**
     * @return hash code for this JnzInstruction.
     */
    @Override
    public int hashCode(){
        return Objects.hash(label, result, prior_labelA);
    }

    /**
     * String representation of the instruction.
     *
     * @return pretty formatted version of the instruction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + prior_labelA;
    }
}
