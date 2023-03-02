package sml.concreteCreators;

import sml.InstructionCreator;
import sml.Instruction;
import sml.Registers;
import sml.instruction.OutInstruction;

/**
 * OutInstructionCreator simply ignores the third param of Creators createInstruction signature.
 */
public class OutInstructionCreator extends InstructionCreator {
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new OutInstruction(opcode,
                Registers.Register.valueOf(reg1));
    }
}
