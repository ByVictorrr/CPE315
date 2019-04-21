public class ImmedInstr extends Instruction
{
    //opcode will be inherited from Instruction
   public  String rs;
   public String rt;
   public String addrImmed;

    public ImmedInstr(String opcode, String rs, String rt, String addrImmed){
        super.opcode = opcode;
        this.rs = rs;
        this.rt = rt;
        this.addrImmed = addrImmed;
    }

}