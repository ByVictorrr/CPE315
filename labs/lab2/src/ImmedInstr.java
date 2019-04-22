import java.util.Map;

public class ImmedInstr extends Instruction
{
    //opcode will be inherited from Instruction
   public Map <String,String> rs;
   public Map<String,String> rt;
   public Map <String,String> addrImmed;

    public ImmedInstr(
            Map <String,String> op,
            Map <String,String> rs,
            Map<String,String> rt,
            Map <String,String> addrImmed
    ) {
        super.opCode = op;
        this.rs = rs;
        this.rt = rt;
        this.addrImmed = addrImmed;


    }


}
