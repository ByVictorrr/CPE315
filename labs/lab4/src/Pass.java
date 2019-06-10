public abstract class Pass {
    protected boolean doesInstructionExist(String token){//Checks to see if an instruction does exist.
        OPTable_Item instruction = null;
        String instructionString = token;
        for(int i = 0; i < token.length(); i++){
            if(token.charAt(i) == '$'){
                instructionString = token.substring(0, i);
            }//if
        }//for
        instruction = OPTable.getOperation(instructionString);
        return (instruction != null);
    }

}
