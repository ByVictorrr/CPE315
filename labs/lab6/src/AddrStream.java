
import static java.lang.System.exit;

public class AddrStream {
    private Long Addr;
    private Long tag;
    private Integer index;
    private Integer blkOffset;
    private Integer byteOffset;
    private final Integer length = 32;
    private final Integer BYTE = 8;
    public AddrStream(String addr, Cache type){

        //=======In bits====================//
        final Integer bits_byteOffsetSize = 2;
        final Integer bits_totalSize  = 32;
        //Case 1 - get word offset size (unit of bits) from block offset size( unit of words)
        final Integer bits_indexSize = logb2(type.getSize()/type.getBlockSize());
        final Integer bits_blockOffsetSize  = blockSizeToBitsSize(type.getBlockSize());
        final Integer bits_tag = bits_totalSize - (bits_byteOffsetSize+bits_indexSize+bits_blockOffsetSize);
        //=================================//
        //Step 1 - parse left and fill in tag until tag == 0
        StringBuilder addr_ref = new StringBuilder(hexToBin(addr));
        /*
        System.out.println("addr = " + addr);
        System.out.println("addr = " + hexToBin(addr));
        System.out.println("length = " + hexToBin(addr).length()); this.tag = getTag(addr_ref, bits_tag);
        */
        this.Addr = getAddr(hexToBin(addr));
        this.tag = getTag(addr_ref, bits_tag);
        this.index = getIndex(addr_ref, bits_indexSize);
        this.blkOffset = getBlkOffset(addr_ref, bits_blockOffsetSize);
        this.byteOffset = getByteOffset(addr_ref, bits_byteOffsetSize);
    }
    public AddrStream(){
        this.Addr  = new Long(0);
        this.tag =  new Long(0);
        this.index = 0;
        this.blkOffset = 0;
        this.byteOffset = 0;
    }


    private static String hexToBin(String hex){
        return String.format("%32s",Integer.toBinaryString(Integer.parseInt(hex,16))).replace(' ', '0');
    }

    private static Long getAddr(String addr){
        Long tag_value = new Long(0);
        int j = 0;
        //Step 1 - get the value of the tag
        for(int i = 32 -1; i >= 0; i--, j++){
            tag_value += Integer.parseInt(Character.toString(addr.charAt(i)), 2)*(int)Math.pow((double)2, (double)j);
        }
        //Step 2 - Change contents of addr subtracting length of bits_tag
        return tag_value;
    }
    //Pass by referencing addr
    private static Long getTag(StringBuilder addr, Integer bits_tag){
        Long tag_value = new Long(0);
        int j = 0;
        //Step 1 - get the value of the tag
        for(int i = bits_tag-1; i >= 0; i--, j++){
            tag_value += Integer.parseInt(Character.toString(addr.charAt(i)), 2)*(int)Math.pow((double)2, (double)j);
        }
        //Step 2 - Change contents of addr subtracting length of bits_tag
        addr.delete(0, bits_tag);
        return tag_value;
    }
    private static Integer getIndex(StringBuilder addr, Integer bits_index){
        Integer index_value = 0;
        int j = 0;
        //Step 1 - get the value of the index
        for(int i = bits_index-1; i >= 0; i--, j++){
            index_value += Integer.parseInt(Character.toString(addr.charAt(i)), 2)*(int)Math.pow((double)2, (double)j);
        }
        //Step 2 - Change contents of addr subtracting length of bits_index
        addr.delete(0, bits_index );
        return index_value;
    }
    private static Integer getBlkOffset(StringBuilder addr, Integer bits_blkOffset){
        Integer blkOffset_value = 0;
        int j = 0;
        //Step 1 - get the value of the blkOffset
        for(int i = bits_blkOffset-1; i >= 0; i--, j++){
            blkOffset_value += Integer.parseInt(Character.toString(addr.charAt(i)), 2)*(int)Math.pow((double)2, (double)j);
        }
        //Step 2 - Change contents of addr subtracting length of bits_blkOffset
        addr.delete(0, bits_blkOffset );
        return blkOffset_value;
    }
    private static Integer getByteOffset(StringBuilder addr, Integer bits_byteOffset){
        Integer byteOffset_value = 0;
        int j = 0;
        //Step 1 - get the value of the byteOffset
        for(int i = bits_byteOffset-1; i >= 0; i--, j++){
            byteOffset_value += Integer.parseInt(Character.toString(addr.charAt(i)), 2)*(int)Math.pow((double)2, (double)j);
        }
        //Step 2 - Change contents of addr subtracting length of bits_byteOffset
        addr.delete(0, bits_byteOffset);
        return byteOffset_value;
    }

    public static Integer logb2(int val){return (int)(Math.log((double)val)/Math.log(2.0));}

    private Integer blockSizeToBitsSize(Integer word){
        Integer bits = 0;
        switch(word){
            case 0:
                bits = 0;
                break;
            case 1:
                bits = 1;
                break;
            case 2:
                bits = 1;
                break;
            case 3:
                bits = 2;
                break;
            case 4:
                bits = 2;
                break;
            default:
                System.out.println("No cache should have more than 4 word blocks");
                exit(-1);
        }
        return bits;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setBlkOffset(Integer blkOffset) {
        this.blkOffset = blkOffset;
    }

    public void setByteOffset(Integer byteOffset) {
        this.byteOffset = byteOffset;
    }
    public Long getTag() {
        return tag;
    }

    public Long getAddr() {
        return Addr;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getBlkOffset() {
        return blkOffset;
    }

    public Integer getByteOffset() {
        return byteOffset;
    }
}
