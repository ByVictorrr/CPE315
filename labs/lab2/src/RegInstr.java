import java.util.Map;

public class RegInstr extends Instruction
{
    //opcode will be inherited from Instruction
    public Map <String,String> rs;
    public Map<String,String> rt;
    public Map<String,String> rd;
    public Map <String,String> shamt; //5 bits
    public Map <String,String> funct; //6bits

    public RegInstr(
            Map<String,String> op,
            Map <String,String> rs,
            Map<String,String> rt,
            Map<String,String> rd,
            Map <String,String> shamt,
            Map <String,String> funct
    ){
                super.opCode = op;
                this.rs = rs;
                this.rt = rt;
                this.rd = rd;
                 this.shamt = shamt;
                 this.funct = funct;

    }
    //Regs are all 5 bits
}