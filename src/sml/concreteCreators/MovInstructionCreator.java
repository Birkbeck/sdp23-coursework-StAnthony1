package sml.concreteCreators;

import sml.InstructionCreator;
import sml.Instruction;
import sml.Registers;
import sml.instruction.MovInstruction;

public class MovInstructionCreator extends InstructionCreator {

    /**
     * Factory method implementation
     * @param opcode String
     * @param reg1 String
     * @param reg2 String
     * @return MovInstruction
     */
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new MovInstruction(opcode,
                Registers.Register.valueOf(reg1),
                Integer.parseInt(reg2));
    }

}
