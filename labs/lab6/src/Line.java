import java.util.HashMap;
import java.util.Map;

public class Line {
    /*============Macros=====================================*/
    private final Integer MOST_RECENT = 0;
    private final Integer RECENT = 1;
    private final Integer LESS_RECENT = 2;
    private final Integer LEAST_RECENT = 3;
    /*=======================================================*/
    //===========block offset -> Address Stream===============\\
    private Map<Integer, AddrStream> LineMap;
    private Integer LRU;

    public Line(Integer block_size) {
        this.LineMap = new HashMap<>();
        //How many words are in one block
        for(int i = 0; i < block_size; i++)
            this.LineMap.put(i, new AddrStream());
    }

    public Map<Integer, AddrStream> getLineMap() {
        return LineMap;
    }

    public Integer getLRU() {
        return LRU;
    }
}
