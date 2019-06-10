package Instructions.RegisterInstructions;

import Instructions.Instruction;

public abstract class RegisterInstruction extends Instruction {
    protected final int rs;
    protected final String rsStringBinary;

    protected final int rt;
    protected final String rtStringBinary;
    protected final int REGISTER_BIT_SIZE = 5;

    public RegisterInstruction(int opCode, int rs, int rt) throws Exception {
        super(opCode);

        String binaryRTRep = Integer.toBinaryString(rt);
        if(binaryRTRep.length() > REGISTER_BIT_SIZE){//prevents values greater than 2^26 from being used as an address.
            throw new Exception("Instruction RT Failure: This is not a valid register");
        }//if
        String binaryRSRep = Integer.toBinaryString(rs);
        if(binaryRSRep.length() > REGISTER_BIT_SIZE){//prevents values greater than 2^26 from being used as an address.
            throw new Exception("Instruction RS Failure: This is not a valid register");
        }//if

        rsStringBinary = addBinaryZero(binaryRSRep, REGISTER_BIT_SIZE);
        rtStringBinary = addBinaryZero(binaryRTRep, REGISTER_BIT_SIZE);

        this.rs = rs;
        this.rt = rt;
    }

    public int getRs() { return rs; }
    public int getRt() { return rt; }


}
