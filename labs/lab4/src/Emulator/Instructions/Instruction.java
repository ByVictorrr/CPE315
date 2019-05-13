package Emulator.Instructions;

public abstract class Instruction {
    protected final int opCode;
    protected final String opCodeStringBinary;
    protected final int OPCODE_BIT_LENGTH = 6;

    public Instruction(int opCode)throws Exception{
        String opCodeString = Integer.toBinaryString(opCode);

        if(opCodeString.length() > OPCODE_BIT_LENGTH){
            throw new Exception("OpCode Initialize Failure: This is not a valid op code!!");
        }//if
        opCodeStringBinary = addBinaryZero(opCodeString, OPCODE_BIT_LENGTH);
        this.opCode = opCode;
    }

    protected String addBinaryZero(String binaryRep, int size){
        while(binaryRep.length() < size){
            binaryRep = "0" + binaryRep;
        }//while
        return binaryRep;
    }

    public int getOpCode() { return opCode; }
    public String getOpCodeString(){ return opCodeStringBinary;}

}
