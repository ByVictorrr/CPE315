import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Cache {
	private Integer indexSizePerCache;
	/*=========Characteristics===========*/
	private Integer size;
	private Integer blockSize;
	private Integer indexSize;
	private Integer associativity;
	/*==================================*/

	/*==========Entry=================*/
	private List<Block>[] cacheEntry;
	/*=======Statistics===========*/
	private Integer hits;
	private Integer num_reads;
	/*===========================*/


	final int TAG = 0;
	final int BLKOFF = 1;
	final int INDEX = 2;

	/*================================*/
	public Cache(Integer size, Integer blockSize, Integer associativity) {
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.size = size;
		this.indexSize = logb2(size * 1024 / (blockSize * 4));
		this.indexSizePerCache = indexSize / associativity;
		this.cacheEntry = new ArrayList[indexSizePerCache];

		for (int i = 0; i < indexSizePerCache; i++) {
			this.cacheEntry[i] = new ArrayList<>();
			for (int j = 0; j < associativity; j++)
				cacheEntry[i].add(j,new Block());
		}
		this.hits = 0;
		this.num_reads = 0;
	}

	public static Integer logb2(int val){return (int)(Math.log((double)val)/Math.log(2.0));}

	public void readAddr(String addr, int priority){
		List<Integer> vals = this.getValues(addr, this);
		//Case 1- direct blocking
	    if(this.associativity == 1){
	    	this.hits += cacheEntry[vals.get(INDEX)].get(0).isBlock(vals.get(TAG), priority);
		}else{ //Case 2 - multiple caches
			readFullWay(vals, priority);
		}
		this.num_reads++;
	}

	public void readFullWay(List<Integer> vals, int priority) {
		Integer hit = 0;
		List<Block> blocks;
		//Step 1 - go through each way
		for(int i = 0; i< this.associativity; i++) {
			if (cacheEntry[vals.get(INDEX)].get(i).getTag().equals(vals.get(TAG))) {
				hit = 1;
				this.hits++;
				break;
			}
		}
		//System.out.println("tag of the input stream : " + vals.get(TAG));
			//Step 2 - sort each row (list by LRU)
			cacheEntry[vals.get(INDEX)] = cacheEntry[vals.get(INDEX)].stream()
																	.sorted(Comparator.comparingInt(Block::getPriority))
																	.collect(Collectors.toList());
			blocks = cacheEntry[vals.get(INDEX)];
			//Step 2 - check if we got a hit
			if (hit == 0) {
				Block replacment = blocks.get(0);
				//Step 3 - see which has highest priority (lowest number)
                swapLRU(replacment, vals.get(TAG), priority);
			}//if
	}//end of function
	private void swapLRU(Block replacement, int tag, int priority) {
		replacement.setPriority(priority);
		replacement.setTag(tag);
	}

		 private String hexToBin(String hex){
			 return String.format("%32s",Integer.toBinaryString(Integer.parseInt(hex,16))).replace(' ', '0');
		 }
		 private Long getAddr(String addr){
			 Long val = new Long(0);
			 int j = 0;
			 for(int i = 32 -1; i >= 0; i--, j++){
				 val += Integer.parseInt(Character.toString(addr.charAt(i)), 2)*(int)Math.pow((double)2, (double)j);
			 }
			 return val;
		 }
		 private Integer getTag(Long addr){
			 return addr.intValue();
		 }
		 private Integer getIndex(Long addr, Cache type){
			 return (addr.intValue() / this.associativity) %  type.getIndexSizePerCache();
		 }
		 private Integer getBlkOffset(Long addr, Cache type){
			 return addr.intValue() % type.getBlockSize();
		 }
		 public final List<Integer> getValues(String addr, Cache type) {
		 	final List<Integer> vals = new ArrayList<Integer>(3);
		 	final int TAG = 0;
		 	final int BLKOFF = 1;
		 	final int INDEX = 2;
		 	Integer blk;
		 	Integer index;
		 	Integer tag;
		 	Long address = getAddr(hexToBin(addr));
		 	address >>= 2;
		 	blk = getBlkOffset(address, type);
		 	index = getIndex(address, type);
		 	tag = address.intValue() >> (32-type.getIndexSize()-type.getBlockSize());

		 	vals.add(TAG, tag);
		 	vals.add(BLKOFF, blk);
		 	vals.add(INDEX, index);

		 	return vals;
		 }


	public Integer getIndexSizePerCache() {
		return indexSizePerCache;
	}

	public void setIndexSizePerCache(Integer indexSizePerCache) {
		this.indexSizePerCache = indexSizePerCache;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}

	public Integer getIndexSize() {
		return indexSize;
	}

	public void setIndexSize(Integer indexSize) {
		this.indexSize = indexSize;
	}


	public Integer getAssociativity() {
		return associativity;
	}

	public void setAssociativity(Integer associativity) {
		this.associativity = associativity;
	}

	public List<Block>[] getCacheEntry() {
		return cacheEntry;
	}

	public void setCacheEntry(List<Block>[] cacheEntry) {
		this.cacheEntry = cacheEntry;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getNum_reads() {
		return num_reads;
	}

	public void setNum_reads(Integer num_reads) {
		this.num_reads = num_reads;
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
}


