package sml.concreteCreators;

import sml.InstructionCreator;
import sml.Instruction;
import sml.Registers;
import sml.instruction.OutInstruction;


public class OutInstructionCreator extends InstructionCreator {

    /**
     * Factory method implementation
     * @param opcode String
     * @param reg1 String
     * @param reg2 String - ignored in this instance
     * @return OutInstruction
     * OutInstructionCreator simply ignores the third param of Creators createInstruction signature.
     */
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new OutInstruction(opcode,
                Registers.Register.valueOf(reg1));
    }
}
