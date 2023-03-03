package sml.concreteCreators;

import sml.InstructionCreator;
import sml.Instruction;
import sml.Registers;
import sml.instruction.JnzInstruction;

public class JnzInstructionCreator extends InstructionCreator {

    /**
     * Factory method implementation
     * @param opcode String
     * @param reg1 String
     * @param reg2 String
     * @return JnzInstruction
     */
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new JnzInstruction(opcode,
                Registers.Register.valueOf(reg1),
                reg2);
    }
}
