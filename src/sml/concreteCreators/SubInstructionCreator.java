package sml.concreteCreators;

import sml.InstructionCreator;
import sml.Instruction;
import sml.Registers;
import sml.instruction.SubInstruction;

public class SubInstructionCreator extends InstructionCreator {

    /**
     * Factory method implementation
     * @param opcode String
     * @param reg1 String
     * @param reg2 String
     * @return SubInstruction
     */
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new SubInstruction(opcode,
                Registers.Register.valueOf(reg1),
                Registers.Register.valueOf(reg2));
    }
}
