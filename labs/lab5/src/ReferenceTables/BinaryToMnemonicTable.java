package ReferenceTables;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BinaryToMnemonicTable {

    //Only for reg instruction
    public static Map<Integer, String> functMap;
            static {
            functMap= new HashMap<>();
            functMap.put( 0b100000,"add");
            functMap.put( 0b100010,"sub");
            functMap.put( 0b100100,"and");
            functMap.put(0b100101,"or") ;
            functMap.put( 0b000000,"sll");
            functMap.put( 0b101010,"slt");
            functMap.put(0b001000,"jr");
        };

    /*This table is to go backward from opcode in binary -> mnemonic operation* (use only !reg types )*/
    private static final Map<Integer, String> opMap;
        static {
            opMap = new Hashtable<>();
            /*IT*/
            opMap.put( 0b001000, "addi");
            opMap.put( 0b000100, "beq" );
            opMap.put( 0b000101, "bne" );
            opMap.put( 0b100011, "lw");
            opMap.put( 0b101011,"sw");
            /*JT*/
            opMap.put(0b000010, "j");
            opMap.put(0b000011, "jal" );

            /*stall Instructions*/
            opMap.put(0b111100, "empty");
            opMap.put(0b111110, "squash");
            opMap.put(0b111111, "stall");
        }

     public static Map<Integer, String> getOpMap(){return opMap;}
     public static Map<Integer, String> getfunctMap(){return functMap;}

}
