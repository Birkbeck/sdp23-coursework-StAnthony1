package sml;

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
     * primitives to their Wrapper class
     */
    private static final Map<Class<?>, Class<?>> TYPE_WRAPPERS = Map.of(
            int.class, Integer.class,
            long.class, Long.class,
            boolean.class, Boolean.class,
            byte.class, Byte.class,
            char.class, Character.class,
            float.class, Float.class,
            double.class, Double.class,
            short.class, Short.class,
            void.class, Void.class);

    /**
     * Constructor creates translator instance with associated input file.
     * @param fileName
     */
    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException, ClassNotFoundException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) throws ClassNotFoundException, SecurityException {
        if (line.isEmpty())
            return null;
        String opcode = scan();
        //use opcode to get Instruction class name
        String classNameString = getClassNameString(opcode);
        //retrieve String parameters from input
        String[] unparsedParams = getParams();
        int paramsLength = unparsedParams.length + 1;//label must be included
        for (Constructor<?> con: Class.forName(classNameString).getConstructors()){
            if (con.getParameterCount() == paramsLength){
                try{
                    //create array for typedParams of various types
                    Object[] typedParams = new Object[paramsLength];
                    Class<?>[] paramConTypes = con.getParameterTypes();
                    for (int i = 0; i < paramsLength; i++) {
                        //first param will always be label (can be null)
                        if(i == 0) typedParams[i] = label;
                        //check if param is of type RegisterName
                        else if (paramConTypes[i].equals(RegisterName.class)){
                            typedParams[i] = Register.valueOf(unparsedParams[i-1]);
                            }
                        //assume param is of primitive type - use wrapper
                        // class to constuct new param from string.
                        else {
                            Class<?> c = paramConTypes[i];
                            if (c.isPrimitive()){
                                c = toWrapper(c);
                            }
                            typedParams[i] = c.getConstructor(String.class).newInstance(unparsedParams[i-1]);
                        }
                    }
                    //call Instruction with correctly typed typedParams
                    return (Instruction) con.newInstance(typedParams);
                    }
                catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                    throw new RuntimeException("Dependency injection issue in Translator class");
                }
            }
        }
        return null;
    }

    /**
     * takes opcode and converts to a string matching the name of an
     * Instruction class with package prefix.
     * @param opcode
     * @return String representing an Instruction
     */
    private String getClassNameString(String opcode){
        String upperOpcode = opcode.toUpperCase();
        return  "sml.instruction." + upperOpcode.charAt(0) +
                upperOpcode.substring(1).toLowerCase() + "Instruction";
    }
    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Creates String Array of un-typed parameters for Instruction class constructor,
     * excluding the first param (which should always be label)
     * @return String[]
     */
    private String[] getParams(){
        ArrayList<String> params = new ArrayList<>();
        while (!line.isEmpty()){
            params.add(scan());
        }
        return params.toArray(new String[0]);
    }


    /**
     * Return the correct Wrapper class if testClass is primitive
     *
     * @param testClass class being tested
     * @return Object class or testClass
     */
    private static Class<?> toWrapper(Class<?> testClass) {
        return TYPE_WRAPPERS.getOrDefault(testClass, testClass);
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