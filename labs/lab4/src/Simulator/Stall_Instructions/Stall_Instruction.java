package Simulator.Stall_Instructions;

import Emulator.Instructions.Instruction;

public class Stall_Instruction extends Instruction {

    public Stall_Instruction() throws Exception
    {
        super(0b111111); //used 66 - because it exceeds 6 bits
    }

}
