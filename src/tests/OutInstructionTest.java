package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.OutInstruction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.EAX;
public class OutInstructionTest {
    private Machine machine;
    private Registers registers;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        machine = Machine.getMachine();
        registers = machine.getRegisters();
        System.setOut(new PrintStream(outputStreamCaptor));

    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
        System.setOut(standardOut);
    }

    @Test
    void executeValidandCheckReturn() {
        registers.set(EAX, 5);
        Instruction instruction = new OutInstruction(null, EAX);
        Assertions.assertEquals(-1, instruction.execute(machine));
    }

    @Test
    void executeValidandCheckConsole() {
        registers.set(EAX, -5);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals("-5", outputStreamCaptor.toString().trim());
    }
}
