package sml.concreteCreators;

import sml.InstructionCreator;
import sml.Instruction;
import sml.Registers;
import sml.instruction.MulInstruction;

public class MulInstructionCreator extends InstructionCreator {
    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new MulInstruction(opcode,
                Registers.Register.valueOf(reg1),
                Registers.Register.valueOf(reg2));
    }
}
