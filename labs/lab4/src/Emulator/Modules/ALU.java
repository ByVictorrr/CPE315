package Emulator.Modules;

import Emulator.Instructions.Instruction;
import Emulator.Instructions.RegisterInstructions.I_Instruction;
import Emulator.Instructions.RegisterInstructions.RInstruction;
import Emulator.Instructions.RegisterInstructions.RegisterInstruction;

public class ALU{

/*getResult: returns the output of the ALU, alters carryflag and zeroFlag depending on instruction
 * parms: r1 - data read from register 1
		 r2 - data read fropm register 2 (optionally could be immediate w/ sign extension )
*/
	public static int getResult(Instruction type) throws Exception {
		int result = 0;
		//Case this is not an immediate instruction
		if(!(type instanceof RegisterInstruction)){//Case a non register instruction has been passed
			throw new Exception("ALU Error: Non Register Read instruction was passed!");
		}//if

		//RS and RT are default given if we have a register read instruction!
		int r1Reference = ((RegisterInstruction) type).getRs();
		int r2Reference = ((RegisterInstruction) type).getRt();
		int r1 = RF.readData(r1Reference); //Read from the Register file to get the values stored
		int r2 = RF.readData(r2Reference); //Notice: if the value is not used, it will simply read from $0 as convention

		if (type instanceof RInstruction) {
			RInstruction r = (RInstruction) type;
			switch (r.getFunc()) {
				case 0b100100: //and
					result = r1 & r2;
					break;
				case 0b100101: //or
					result = r1 | r2;
					break;
				case 0b100000: //add
					result = r1 + r2;
					break;
				case 0b000000: //sll
					result = r1 << r.getShamt(); //r2 don't care
					break;
				case 0b100010: //sub
					result = r1 - r2;
					break;
				case 0b101010: //slt
					result = r1 < r2 ? 1 : 0;
					break;
				default:
					throw new Exception("No type of register function");
			}
		} else if (type instanceof I_Instruction) {
			I_Instruction i = (I_Instruction) type;
			switch (type.getOpCode()) {
				case 0b001000: //addi
					result = r1 + i.getImm();
					break;
				case 0b000100: //beq
					result = r1 == r2 ? 1 : 0;
					break;
				case 0b000101: //bne
					result = r1 != r2 ? 1 : 0;
					break;
				case 0b100011: //lw
					result = r1 + i.getImm(); //r2 - immediate
					break;
				case 0b101011: //sw
					result = r1 + i.getImm();//r2 - immediate
					break;
				default:
					throw new Exception("No type of immediate function");
			}
		}
		return result;
	}
}
