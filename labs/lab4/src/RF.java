import java.util.*;

public class RF {
	private final static int MAX_REGISTERS = 32;
	private static int R[] = new int[MAX_REGISTERS];
	private static int reg1Data;
	private static int reg2Data;

	public static void writeData(int regWriteAddr, int data_write) throws Exception {
		//if the address inserted is valid
		if(regWriteAddr >=  0 && regWriteAddr < 32)
			R[regWriteAddr] = data_write;
		else
			throw new Exception("Not a valid write address");
	}

	public static int readData(int regReadData) throws Exception {
		if(regReadData >=  0 && regReadData < 32)
			return R[regReadData];
		else
			throw new Exception("Not a valid write address");
	}//readData

	public static void clearRegisters(){
		for(int i = 0; i < MAX_REGISTERS; i++){
			R[i] = 0;
		}//for
	}//clearRegisters

	public static void printRegisters(){
		HashMap<String, Integer> registerReference = RegisterTable.getRegisterTable();
		List<HashMap.Entry<String, Integer>> registers = new ArrayList<HashMap.Entry<String, Integer>>();

		for (HashMap.Entry<String, Integer> entry : registerReference.entrySet()) {//First convert to list
			if(!(entry.getKey().equals("$zero")) && !(entry.getKey().equals("$r0"))) {//skip if the registers are $zero or $r0; Only print $0
				registers.add(entry);
			}//if
		}//for

		Collections.sort(registers, new Comparator<HashMap.Entry<String, Integer>>() {//Now sort the list in register order
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue() > o2.getValue()) ? 1 : -1;
			}//compare
		});

		int counter = 0;
		for(HashMap.Entry<String, Integer> register : registers){//Finally we will print it!!
			try {
				if(counter != 0 && (counter%4) == 0){//Next line when every 4th register
					System.out.println();
				}//if
				if(counter == 1){//Print out extra tab when at 0 to help align text!!
					System.out.print("\t");
				}//if
				System.out.print(register.getKey() + " = " +
						RF.readData(register.getValue()) + " \t");
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			counter++;
		}//for

		System.out.println();
	}

	public static int [] getR(){ return R;}
	public static int getReg1Data(){ return reg1Data;}
	public static int getReg2Data(){ return reg2Data;}


}
