import java.util.Map;

public class RegInstr extends Instruction
{
    //opcode will be inherited from Instruction
    public Map <String,String> rs;
    public Map<String,String> rt;
    public Map<String,String> rd;
    public Map <String,String> shamt; //5 bits
    public Map <String,String> funct; //6bits

    public RegInstr(String opCode, String rs,String rt, String rd, String shamt, String funct){
                this.rs = rs;
                this.rt;
                this.rd;
                 this.shamt;
                 this.funct;

    }
    //Regs are all 5 bits
}