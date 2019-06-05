public class Block {
    /*============Macros=====================================*/
    private final Integer MOST_RECENT = 0;
    private final Integer RECENT = 1;
    private final Integer LESS_RECENT = 2;
    private final Integer LEAST_RECENT = 3;
    /*=======================================================*/
    private Integer priority;
    private Integer tag;

    public Block(){
        this.tag = 0;
        this.priority = -1;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getTag() {
        return tag;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer isBlock(Integer tag, Integer priority){
        int hit = 0;
       if(this.tag.equals(tag)) {
           this.priority = priority;
           hit = 1;
       }else{
           this.priority = priority;
           this.tag = tag;
       }
           return hit;
    }

    public Integer isBlockAssocative(Integer tag, Integer priority) {
        int hits = 0;
        if (this.tag.equals(tag)) {
            this.priority = priority;
            hits = 1;
        }
        return hits;
    }


}
