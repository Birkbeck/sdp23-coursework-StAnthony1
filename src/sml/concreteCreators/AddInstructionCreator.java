package sml.concreteCreators;

import sml.Instruction;
import sml.InstructionCreator;
import sml.Registers;
import sml.instruction.AddInstruction;

/**
 * This class is a concrete InstructionCeator
 */
public class AddInstructionCreator extends InstructionCreator {

    /**
     * Factory method implementation
     * @param opcode String
     * @param reg1 String
     * @param reg2 String
     * @return AddInstruction
     */
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new AddInstruction(opcode,
                Registers.Register.valueOf(reg1),
                Registers.Register.valueOf(reg2));
    }
}
