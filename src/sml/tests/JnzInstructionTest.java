package sml.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.JnzInstruction;

import java.io.IOException;

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

    @Test
    void executeLoopRegisterIsZero() {
        try{
            registers.set(EAX, 1);
        machine.getLabels().addLabel("f1", 2);
        Instruction instruction = new JnzInstruction(null, EAX, "f1");
        Assertions.assertEquals(2, instruction.execute(machine));
        }
        catch (IOException e){
            System.out.println("Attempt to overwrite exisiting label in machine instance");
        }
    }

    @Test
    void executeSkipLoopRegisterNotZero() {
        try {
            machine.getLabels().addLabel("f3", 2);
            registers.set(EAX, 0);
            Instruction instruction = new JnzInstruction(null, EAX, "f3");
            Assertions.assertEquals(-1, instruction.execute(machine));
        }
        catch (IOException e) {
            System.out.println("Attempt to overwrite exisiting label in machine instance");
        }
    }

    @Test
    void invalidLabel(){
        registers.set(EAX, 1);
        Instruction instruction = new JnzInstruction(null, EAX, "f3");
        Assertions.assertThrows(NullPointerException.class ,()-> instruction.execute(machine));
    }
}
