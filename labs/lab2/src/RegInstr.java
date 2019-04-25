public class RegInstr extends Instruction
{
    //opcode will be inherited from Instruction
    public String rs;
    public String rt;
    public String rd;
    public String shamt; //5 bits
    public String funct; //6bits

    public RegInstr(String op, String rs,String rt, String rd, String shamt, String funct){
        super.op = op;
        this.rs = rs;
        this.rt = rt;
        this.rd= rd;
        this.shamt = shamt;
        this.funct = funct;
    }

    public String toString() {
        return "opcode= " + super.toString() + ",  rd =" + this.rd + ", rs=" +
                this.rs  + " rt = " + this.rt + " shamt = " + this.shamt +  " funct = " + this.funct;
    }

    //Regs are all 5 bits
}