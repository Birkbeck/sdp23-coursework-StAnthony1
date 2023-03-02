package sml.concreteCreators;

import sml.Instruction;
import sml.InstructionCreator;
import sml.Registers;
import sml.instruction.AddInstruction;

public class AddInstructionCreator extends InstructionCreator {

    public Instruction createInstruction(String opcode, String reg1, String reg2){
        return new AddInstruction(opcode,
                Registers.Register.valueOf(reg1),
                Registers.Register.valueOf(reg2));
    }
}
