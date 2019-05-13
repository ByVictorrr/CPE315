package Emulator.ReferenceTables;

import java.util.Hashtable;

/*The data structure that will hold information about the operations*/
public class OPTable{

    private static final Hashtable<String, OPTable_Item> opTable;
    static {
        opTable = new Hashtable<String, OPTable_Item>();

        /*RT*/
        opTable.put("and", new OPTable_Item("and", "RT",0b100100, 3 ));
        opTable.put("or", new OPTable_Item("or", "RT",0b100101, 3 ));
        opTable.put("add", new OPTable_Item("add", "RT", 0b100000, 3 ));
        opTable.put("sll", new OPTable_Item("sll", "RT", 0b000000, 3 ));
        opTable.put("sub", new OPTable_Item("sub", "RT", 0b100010, 3 ));
        opTable.put("slt", new OPTable_Item("slt", "RT", 0b101010, 3 ));
        opTable.put("jr", new OPTable_Item("jr", "RT", 0b001000, 1 ));


        /*IT*/
        opTable.put("addi", new OPTable_Item("addi", "IT", 0b001000, 2 ));
        opTable.put("beq", new OPTable_Item("beq", "IT", 0b000100, 2 ));
        opTable.put("bne", new OPTable_Item("bne", "IT", 0b000101, 2 ));
        opTable.put("lw", new OPTable_Item("lw", "IT", 0b100011, 2 ));
        opTable.put("sw", new OPTable_Item("sw", "IT",0b101011, 2));

        /*JT*/
        opTable.put("j", new OPTable_Item("j", "JT", 0b000010, 0 ));
        opTable.put("jal", new OPTable_Item("jal", "JT", 0b000011, 0 ));
    }

    public OPTable(){ }

    public static OPTable_Item getOperation(String key){
        return opTable.get(key);
    }

}