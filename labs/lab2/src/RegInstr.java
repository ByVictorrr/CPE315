public class RegInstr extends Instruction
{
    //opcode will be inherited from Instruction
    public String rs;
    public String rt;
    public String rd;
    public String shamt; //5 bits
    public String funct; //6bits

    //Regs are all 5 bits
}