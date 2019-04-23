public class JumpInstr extends Instruction
{
    //opcode will be inherited from Instruction
   public String rs;
   public String rt;
   public String addrImmed; //need to decode using label

  public JumpInstr(String op, String rs, String rt, String addrImmed){
        super.op = op;
        this.rs = rs;
        this.rt = rt;
        this.addrImmed = addrImmed;
    }

}