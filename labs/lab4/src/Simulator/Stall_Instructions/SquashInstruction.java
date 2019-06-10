package Simulator.Stall_Instructions;

import Emulator.Instructions.Instruction;

public class SquashInstruction extends Instruction {

    public SquashInstruction() throws Exception
    {
        super(0b111110); //used 65 - because it exceeds 6 bits
    }

}
