package Instructions.RegisterInstructions;

public class I_Instruction extends RegisterInstruction{

    private final int imm;
    private final int IMM_BIT_SIZE = 16;
    private final int INTEGER_BIT_SIZE = 32;
    private final String immStringBinary;

    public I_Instruction(int opCode, int rs, int rt, int imm) throws Exception {
        super(opCode, rs, rt);
        String binaryIMMRep = Integer.toBinaryString(imm);
        if(binaryIMMRep.length() > IMM_BIT_SIZE){//prevents values greater than 2^16 from being used as an address.
            if(imm < 0){//if the value is negative, trim it to first 16 bits
                immStringBinary = binaryIMMRep.substring(INTEGER_BIT_SIZE - IMM_BIT_SIZE);
            } else {//Else this value is much too high.
                throw new Exception("Instruction IMM Failure: This is not an immediate value");
            }//else
        }else {
            immStringBinary = addBinaryZero(binaryIMMRep, IMM_BIT_SIZE);
        }//else
        this.imm = imm;
    }

    public int getImm() { return imm; }

    @Override
    public String toString() {
        return opCodeStringBinary + " " + rsStringBinary + " " + rtStringBinary + " "
                + immStringBinary;
    }
}
