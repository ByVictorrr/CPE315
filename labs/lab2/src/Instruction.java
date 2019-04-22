import java.util.*;

public class Instruction{
    public Map<String,String> opCode; //text opcode -> binary
    public Parser parse;

    public String getOpcode(String line) {
       return parse.getOp(line);
    }
}


