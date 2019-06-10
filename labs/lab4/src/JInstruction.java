public class JInstruction extends Instruction {
    private final int addr;
    private final String addrStringBinary;
    private final int addrBitLength = 26;

    public JInstruction(int opCode, int addr) throws Exception{
        super(opCode);
        String binaryAddrRep = Integer.toBinaryString(addr);
        if(binaryAddrRep.length() > addrBitLength){//prevents values greater than 2^26 from being used as an address.
            throw new Exception("JInstruction Address Failure: This is not a valid address!");
        }//if

        this.addrStringBinary = addBinaryZero(binaryAddrRep, addrBitLength);
        this.addr = addr;
    }

    public int getAddr() { return addr; }

    @Override
    public String toString() {
        return opCodeStringBinary + " " + addrStringBinary;
    }
}
