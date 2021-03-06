public class Block {

    private Integer priority;
    private Integer tag;

    public Block(){
        this.tag = -1;
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

    @Override
    public String toString() {
        return "Priority: " + priority + "  tag = " + tag;
    }

}
