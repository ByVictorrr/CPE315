import java.util.Map;

public class JumpInstr extends Instruction
{
    //opcode will be inherited from Instruction
   public Map <String,String> rs;
   public Map <String,String> rt;
   public Map <String,String> addrImmed; //need to decode using label


  public JumpInstr(Map<String,String> rs,
                   Map <String,String> rt,
                   Map<String,String> addrImmed
  ){
      this.rs = rs;
      this.rt = rt;
      this.addrImmed = addrImmed;
  }

}