package AssemblerActions;

import Modules.Memory;

public class MemFetch implements Action {
    private final int lower;
    private final int higher;

    public MemFetch(int lower, int higher){
        this.lower = lower;
        this.higher = higher;
    }

    @Override
    public void executeAction() {
        System.out.println();
        for(int i = lower; i <= higher; i++){
            try{
                Memory.loadWordFromMemory(i);
                System.out.println("[" + i + "] = " + Memory.loadWordFromMemory(i));
            }catch (Exception e){
                System.out.println("Address at " + i + " is out of bounds of memory!");
                break;
            }//catch
        }//for
        System.out.println();
    }
}
