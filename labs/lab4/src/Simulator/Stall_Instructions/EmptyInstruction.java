package Simulator.Stall_Instructions;

import Emulator.Instructions.Instruction;

public class EmptyInstruction extends Instruction {

    public EmptyInstruction() throws Exception
    {
        super(0b111100); //used 64 - because it exceeds 6 bits
    }

}
