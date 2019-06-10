package Instructions.RegisterInstructions;

import Instructions.Instruction;

public final class RInstruction extends RegisterInstruction {
    private final int rd;
    private final int shamt;
    private final int func;
    private final String rdStringBinary, shamtStringBinary, funcStringBinary;
    protected final int FUNC_BIT_SIZE = 6;

    public RInstruction(int rs, int rt, int rd, int shamt, int func) throws Exception {
        super(0b000000, rs, rt);
        String binaryRDRep = Integer.toBinaryString(rd);
        if(binaryRDRep.length() > REGISTER_BIT_SIZE){//prevents values greater than 2^26 from being used as an address.
            throw new Exception("RInstruction RD Failure: This is not a valid register destination");
        }//if
        String binarySHAMTRep = Integer.toBinaryString(shamt);
        if(binarySHAMTRep.length() > REGISTER_BIT_SIZE){//prevents values greater than 2^26 from being used as an address.
            throw new Exception("RInstruction SHAMT Failure: This is not an valid shamt value");
        }//if
        String binaryFUNCRep = Integer.toBinaryString(func);
        if(binaryFUNCRep.length() > FUNC_BIT_SIZE){//prevents values greater than 2^26 from being used as an address.
            throw new Exception("RInstruction FUNC Failure: This is not a valid func value");
        }//if

        rdStringBinary = addBinaryZero(binaryRDRep, REGISTER_BIT_SIZE);
        shamtStringBinary = addBinaryZero(binarySHAMTRep, REGISTER_BIT_SIZE);
        funcStringBinary = addBinaryZero(binaryFUNCRep, FUNC_BIT_SIZE);

        this.rd = rd;
        this.shamt = shamt;
        this.func = func;
    }

    public int getRd() { return rd; }
    public int getShamt() { return shamt; }
    public int getFunc() { return func; }

    @Override
    public String toString() {
        return opCodeStringBinary + " " + rsStringBinary + " " + rtStringBinary + " "
                + rdStringBinary + " " + shamtStringBinary + " " + funcStringBinary;
    }
}
