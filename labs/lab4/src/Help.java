public class Help implements Action {
    @Override
    public void executeAction() {
        System.out.println("h = show help");
        System.out.println("d = dump register state");
        System.out.println("s = step through a single clock cycle step (i.e. simulate 1 cycle and stop)");
        System.out.println("s num = step through num clock cycles");
        System.out.println("r = run until the program ends and display timing summary");
        System.out.println("m num1 num2 = display data memory from location num1 to num2");
        System.out.println("c = clear all registers, memory, and the program counter to 0");
        System.out.println("q = exit the program\n");
    }
}
