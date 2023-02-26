package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * The Machine class executes a single Small Machine Language program
 * <p>
 * An instance contains a (class) Registers populated with a fixed number of (enum) RegisterName
 * and changes the Integer values associated with these RegisterName according to the sequential
 * execution of each Instruction contained in the Machine field program.
 *
 */
public final class Machine {

	private final Labels labels = new Labels();

	private final List<Instruction> program = new ArrayList<>();

	private final Registers registers;

	private int programCounter = 0;

	/**
	 * Constructor: a machine ready to load a SML program from a Translator class
	 *
	 * @param registers must be of type Registers
	 *
	 */
	public Machine(Registers registers) {
		this.registers = registers;
	}

	/**
	 * Executes the program in program, beginning at instruction 0.
	 * Precondition: the program and its labels have been stored properly.
	 */
	public void execute() throws ArithmeticException, NullPointerException{
		programCounter = 0;
		registers.clear();
		while (programCounter < program.size()) {
			Instruction ins = program.get(programCounter);
			int programCounterUpdate = ins.execute(this);
			programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
					? programCounter + 1
					: programCounterUpdate;
		}
	}
	/**
	 * returns SML machine's labels field
	 *
	 * @return Map of Strings mapped to Integers
	 */
	public Labels getLabels() {
		return this.labels;
	}

	/**
	 * returns SML machine's program field
	 *
	 * @return ArrayList of Instruction
	 */
	public List<Instruction> getProgram() {
		return this.program;
	}

	/**
	 * returns SML machine's Registers field
	 *
	 * @return Map of Register (enum) mapped to Integers
	 */
	public Registers getRegisters() {
		return this.registers;
	}


	/**
	 * String representation of the program under execution.
	 *
	 * @return pretty formatted version of the code.
	 */
	@Override
	public String toString() {
		return program.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}
	/**
	 * tests for equality between two machine instances. Fields
	 * labels, program, registers and program counter must be equal
	 * for the two instances to be equal.
	 *
	 * @return Boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Machine other) {
			return Objects.equals(this.labels, other.labels)
					&& Objects.equals(this.program, other.program)
					&& Objects.equals(this.registers, other.registers)
					&& this.programCounter == other.programCounter;
		}
		return false;
	}
	/**
	 * @return int hash code for this Machine.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
