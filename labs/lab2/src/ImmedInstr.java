public class ImmedInstr extends Instruction
{
    //opcode will be inherited from Instruction
   public String rs;
   public String rt;
   public String addrImmed;

    public ImmedInstr(String op, String rs, String rt, String addrImmed){
        super.op = op;
        this.rs = rs;
        this.rt = rt;
        this.addrImmed = addrImmed;
    }

}
