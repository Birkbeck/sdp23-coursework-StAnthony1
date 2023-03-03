package sml;

/**
 * Class containing factory method for Instruction
 */
public abstract class InstructionCreator {

    /**
     * String params to be converted to correct types in concrete InstructionCreators
     * @param s1 String
     * @param s2 String
     * @param s3 String
     * @return Instruction
     */
    protected abstract Instruction createInstruction(String s1, String s2, String s3);

}
