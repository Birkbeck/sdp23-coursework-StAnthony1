package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static sml.Registers.Register;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

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
        //test
        System.out.println(opcode);
        String classNameString = getClassNameString(opcode);

        String[] unparsedParams = getParams();
        int paramsLength = unparsedParams.length + 1;
        for (Constructor<?> con: Class.forName(classNameString).getConstructors()){
            if (con.getParameterCount() == paramsLength){
                System.out.println(con);
                try{
                    Object[] params = new Object[paramsLength];
                    Class<?>[] paramConTypes = con.getParameterTypes();
                    System.out.println(Arrays.toString(paramConTypes));
                    for (int i = 0; i < paramsLength; i++) {
                        if(i == 0) params[i] = label;
                        else if (paramConTypes[i].equals(RegisterName.class)){
                            params[i] = Register.valueOf(unparsedParams[i-1]);
                            }
                        else {
                        Class<?> c = toWrapper(paramConTypes[i]);
                        params[i] = c.getConstructor(String.class).newInstance(unparsedParams[i-1]);
                        }
                        System.out.println(params[i]);
                    }
                    return (Instruction) con.newInstance(params);
                    }
                catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
        }


    private String getClassNameString(String opcode){
        String upperOpcode = opcode.toUpperCase();
        return "sml.instruction." + upperOpcode.charAt(0) +
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

    private String[] getParams(){
        ArrayList<String> params = new ArrayList<>();
        while (!line.isEmpty()){
            params.add(scan());
            System.out.println(params);
        }
        return params.toArray(new String[0]);
    }


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