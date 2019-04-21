public class RegInstr extends Instruction
{
    //opcode will be inherited from Instruction
    public String rs;
    public String rt;
    public String rd;
    public String shamt; //5 bits
    public String funct; //6bits

    public RegInstr(String opcode, String rs, String rd, String rt, String shamt, String funct){
        super.opcode = opcode;
        this.rs = rs;
        this.rt = rt;
        this.rd= rd;
        this.shamt = shamt;
        this.funct = funct;
    }
    //Regs are all 5 bits
}