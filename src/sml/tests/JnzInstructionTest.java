package sml.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.JnzInstruction;

import static sml.Registers.Register.EAX;

public class JnzInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        //...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }
    //TODO create HashMap<String, Integer> or Lebels Object and populate for testing -
    // do it via Machine instantiation or manually for testing purposes.
    @Test
    void executeLoopRegisterIsZero() {
        registers.set(EAX, 1);
        machine.getLabels().addLabel("f3", 2);
        Instruction instruction = new JnzInstruction(null, EAX, "f3");
        Assertions.assertEquals(2, instruction.execute(machine));
    }

    @Test
    void executeSkipLoopRegisterNotZero() {
        machine.getLabels().addLabel("f3", 2);
        registers.set(EAX, 0);
        Instruction instruction = new JnzInstruction(null, EAX, "f3");
        Assertions.assertEquals(-1, instruction.execute(machine));
    }

    @Test
    void invalidLabel(){
        registers.set(EAX, 1);
        Instruction instruction = new JnzInstruction(null, EAX, "f3");
        Assertions.assertThrows(NullPointerException.class ,()-> instruction.execute(machine));
    }
}
