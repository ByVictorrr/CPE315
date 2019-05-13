package Simulator.Delays;

import Emulator.Instructions.Instruction;

public class nopInstruction extends Instruction {

    public nopInstruction() throws Exception
    {
        super(0b111111);
    }

}
