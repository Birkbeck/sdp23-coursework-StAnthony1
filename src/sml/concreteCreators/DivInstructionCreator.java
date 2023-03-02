package sml.concreteCreators;

import sml.Instruction;
import sml.InstructionCreator;
import sml.Registers;
import sml.instruction.DivInstruction;

public class DivInstructionCreator extends InstructionCreator {

    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new DivInstruction(opcode,
                Registers.Register.valueOf(reg1),
                Registers.Register.valueOf(reg2));
    }
}
