import java.util.Map;

public class ImmedInstr extends Instruction
{
    //opcode will be inherited from Instruction
   public  Map <String,String> rs;
   public Map<String,String> rt;
   public Map <String,String> addrImmed;

    public ImmedInstr(){

    }


}
