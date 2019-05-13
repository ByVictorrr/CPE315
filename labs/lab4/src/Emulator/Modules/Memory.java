package Emulator.Modules;

public class Memory {
    private static int MAX_MEMORY = 8192;
    private static int Memory[] = new int[MAX_MEMORY]; // Size of memory
    private static final String SP_REGISTER = "$sp";

    //Function used to store an integer into memory. Raises an error if bounds has been passed
    public static void storeWordIntoMemory(int byteOffset, int value) throws Exception{
        int location = byteOffset;
        if(location >= MAX_MEMORY){
            throw new Exception("Memory Error: Out of Bounds. You have run out of memory!!");
        }
        Memory[location] = value;
    }//storeIntoMemory

    //Function used to load an integer into memory. Raises an error if bounds has been passed
    public static int loadWordFromMemory(int byteOffset) throws Exception {
        int location = byteOffset;
        if(location >= MAX_MEMORY){
            throw new Exception("Memory Error: Out of Bounds. Loading from Out of Bounds!!");
        }
        return Memory[location];
    }//loadFromMemory

    /*
       This is a utility function used to help the user allocate memory from the stack.
       This function assumes the user is inputting a negative value for allocationAmount
     */
    public static int allocateStack(int stackPointer, int allocationAmount){
        return stackPointer - allocationAmount;
    }//allocateStack

    public static void clearMemory(){
        for(int i = 0; i < MAX_MEMORY; i++){
            Memory[i] = 0;
        }//for
    }//clearMemory

}
