import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Cache
{
	/*Characteristics*/
	private Integer size;
	private Integer blockSize;
	private Integer indexSize;
	private Integer associativity;

	/*Entry*/
	//using a List for multiple ways;
    //circular queue
	private List<Line>[] ways;
	/*Statistics*/
	private Integer hits;
	private Integer num_reads;
	//hit rate = hits/num _reads
	
	public Cache(Integer size, Integer blockSize, Integer associativity) {
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.size = size;
		this.indexSize = AddrStream.logb2(size/blockSize);
		this.ways = new ArrayList[indexSize];

		for(int i = 0; i < indexSize ; i++){
			this.ways[i] = new ArrayList<>();
			for(int j = 0; j < ways.length; j++)
			    this.ways[i].add(new Line(blockSize));
		}
		this.hits = 0;
		this.num_reads = 0;
	}
	public Integer readSingleWay(AddrStream stream, int way) {
		//Step 1 - go through each entry see if that tag is in one of
		int hit = 0;
		for (int i = stream.getBlkOffset(); i >= 0; i--) {
			//Case 1 - if the stream index has the same tag as one of the tags in that line
			if (ways[stream.getIndex()].get(way).getLineMap().get(i).getTag().equals(stream.getTag())) {
				hit = 1;
			}//if
		}//for
		return hit;
	}
	public boolean readFullWay(AddrStream stream, Integer associativity) {
		int hit = 0;
		for(int i = 0; i< associativity; i++) {
			if((hit += readSingleWay(stream, i)) > 0) {
				hit = 1;
			}
		}
		return hit > 0;
	}


	public void readCache(AddrStream stream) {
		Integer hit = 0;
		int HEAD_OF_LIST = 0;
		List<Integer> list_LRU = new ArrayList<>();
		int j = 0;
		//Case 1 - see if any of the ways have the stream we are looking for
		if(readFullWay(stream, this.associativity)){
			this.hits += hit;
		}else{ //Case 2 - if not found in the cache then we need to insert it
			ways[stream.getIndex()] = ways[stream.getIndex()].stream().sorted(Comparator.comparingInt(Line::getLRU).reversed()).collect(Collectors.toList());
			//Step 3 - need to block offset and reset LRU
			ways[stream.getIndex()].get(HEAD_OF_LIST).getLineMap().replace(stream.getBlkOffset(),stream);
			//Step 4 - insert around the block offset
			Line block = new Line(blockSize);
			ways[stream.getIndex()].set(HEAD_OF_LIST,insertToBlock(block, stream.getBlkOffset(),stream.getBlkOffset(), blockSize, 1));
		}
		this.num_reads++;
	}
	public Line insertToBlock(Line block, Integer blkOffset, Integer orgBlkOffset, Integer blockSize, int flag){
		//Step 1 - got left first
		if(blkOffset == -1)
			return null;

	    if(blkOffset > -1  && flag == 1){
	    	block.getLineMap().get(blkOffset).setBlkOffset(blkOffset);
	    	block.getLineMap().get(blkOffset).setTag();
	    	insertToBlock(block, blkOffset-1, blkOffset, blockSize, 1);
	    	if(!blkOffset.equals(orgBlkOffset)) {
				return null;
			}
			flag = 0;
		}
		if(blkOffset >= blockSize-1){
			block.getLineMap().get(blkOffset).setBlkOffset(blkOffset);
			insertToBlock(block, blkOffset+1, blkOffset, blockSize, 0);
		}
		block.setLRU(0);

		return block;
	}


	public static int maxIndex(List<Integer> list) {
		Integer i=0, maxIndex=-1, max=null;
		for (Integer x : list) {
			if ((x!=null) && ((max==null) || (x>max))) {
				max = x;
				maxIndex = i;
			}
			i++;
		}
		return maxIndex;
	}


	public void printResults(int cache_num, int size){
		String format_size = "";
		System.out.println("Cache #" + cache_num);
		if(size == 2){
			format_size = "2048B";
		}
		else if (size == 4){
			format_size = "4096B";
		}else{
			exit(-1);
		}
		System.out.println("Cache sizes: " + format_size + "	" + this.associativity + "		" + "Block size: " + this.blockSize);
		float hit_percent = ((float)this.hits/this.num_reads) * 100;
		System.out.println("Hits: " + this.hits + "	" + "Hit Rate: " + + Math.round(hit_percent*100.0)/100.0 + "%" );
	}

	public Integer getSize() {
		return size;}

	public Integer getBlockSize() {
		return blockSize;
	}

	public Integer getAssociativity() {
		return associativity;
	}


	public Integer getHits() {
		return hits;
	}

	public Integer getNum_reads() {
		return num_reads;
	}
}

