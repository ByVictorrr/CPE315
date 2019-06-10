package ReferenceTables;

import java.util.HashMap;

/*This is the table used to store the address of labels within the .asm files*/
public class LabelTable {
    HashMap<String, Integer> labelTable = new HashMap<>();
    public LabelTable(){};

    public void addLabel(String label, Integer commandRef)throws Exception{
        if(labelTable.containsKey(label)){
            throw new Exception("Duplicate label not allowed!");
        }//if
        labelTable.put(label, commandRef);
    }

    public Integer getLabelRef(String label){
        return labelTable.get(label);
    }

}
