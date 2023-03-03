package sml;

import sml.concreteCreators.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static sml.Registers.Register;

/**
 * This class sets up an SML machine with an executable program.
 * It takes SML input from a file, converts that input to program
 * instructions and passes the executable program to a single machine
 * instance.
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author oneilo1
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    /**
     * Type Wrapper Map used by toWrapper function for converting
     * primitives to their Wrapper class - will allow a variety of
     * new instruction classes to be added which take primitive class
     * params. New primitives to be added as functionality added to SML.
     */
    private static final Map<Class<?>, Class<?>> TYPE_WRAPPERS = Map.of(
            int.class, Integer.class);

    /**
     * Constructor creates translator instance with associated input file.
     * @param fileName name of a file containing an SML program.
     */
    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    /**
     *
     * @param labels String - can be null
     * @param program empty program belonging to a machine instance to be populated.
     * @throws IOException
     * alterations made to reflect the use of factory method in the production
     * of concrete instruction classes.
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException{
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();
                String opcode = scan();
                String arg1 = scan();
                String arg2 = scan();

                InstructionCreator instruction_creator = createInstruction(opcode);
                if (instruction_creator != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction_creator.createInstruction(opcode, arg1, arg2));
                }
            }
        }
    }

    /**
     * helper function for selecting the correct InstructionCreator
     * @param op String representing the opcode
     * @return the new InstructionCreator
     *
     */
    private InstructionCreator createInstruction(String op){
        return switch (op) {
            case "add" -> new AddInstructionCreator();
            case "div" -> new DivInstructionCreator();
            case "jnz" -> new JnzInstructionCreator();
            case "mov" -> new MovInstructionCreator();
            case "mul" -> new MulInstructionCreator();
            case "out" -> new OutInstructionCreator();
            case "sub" -> new SubInstructionCreator();
            default -> null;
        };
    }


    /**
     * checks for presence of label
     * @return String corresponding to label name or null if no label
     */
    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }


    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                    line = line.substring(i);
                return word;
            }
        String lastWord = line;
        line = "";
        return lastWord;
    }
}