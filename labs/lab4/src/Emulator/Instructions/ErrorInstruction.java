package Emulator.Instructions;

public class ErrorInstruction extends Instruction {
    private final String message;

    public ErrorInstruction(String message) throws Exception {
        super(0);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
