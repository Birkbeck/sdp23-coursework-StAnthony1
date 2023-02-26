package sml;

import java.io.IOException;

public class Main {
	/**
	 * Initialises the system and executes the program.
	 *
	 * @param args name of the file containing the program text.
	 */
	public static void main(String... args) {
		if (args.length != 1) {
			System.err.println("Incorrect number of arguments - Machine <file> - required");
			System.exit(-1);
		}

		try {
			Translator t = new Translator(args[0]);
			Machine m = new Machine(new Registers());
			t.readAndTranslate(m.getLabels(), m.getProgram());

			System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
			//System.out.println("Labels: " + m.getLabels());
			System.out.println(m);

			System.out.println("Beginning program execution.");
			m.execute();
			System.out.println("Ending program execution.");

			System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
		}
		catch (IOException e) {
			System.out.println(e.getMessage());

		}
		catch (ArithmeticException e){
			System.out.println("Machine cannot divide by zero");
		}
		catch (NullPointerException e){
			System.out.println("Machine's labels field has no key corresponding the label passed in jnz instruction");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("problem with dependency injection in Translator.getInstruction()");
		}
	}
}
