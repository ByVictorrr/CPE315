import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Cache {
	/*=========Characteristics===========*/
	private Integer sizeOneWay;
	private Integer sizeTotal;
	private Integer blockSize; //in bytes
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
	public Cache(Integer sizeTotal, Integer blockSize, Integer associativity) {
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.sizeTotal = sizeTotal;
		this.sizeOneWay = sizeTotal/associativity;
		this.indexSize = sizeOneWay/(blockSize*4);
		this.cacheEntry = new ArrayList[indexSize];

		for (int i = 0; i < indexSize; i++) {
			this.cacheEntry[i] = new ArrayList<>();
			for (int j = 0; j < associativity; j++)
				cacheEntry[i].add(j,new Block());
		}
		this.hits = 0;
		this.num_reads = 0;
	}

	//====================READING FUNCTIONS =================================\\

	public void readAddr(String addr, int priority){
		List<Integer> vals = this.getValues(addr, this);
		//Case 1- direct blocking
	    if(this.associativity == 1){
	        readSingleWay(vals, priority);
		}else{ //Case 2 - multiple caches
			readFullWay(vals, priority);
		}
		this.num_reads++;
	}

	public void readSingleWay(List<Integer> vals, int priority){
		//Step 1 - reference the index of single way
	    Block block = cacheEntry[vals.get(INDEX)].get(0);
	    //Case 1 - hit, update priority
	    if(block.getTag().equals(vals.get(TAG))){
	    	block.setPriority(priority);
	    	this.hits++;
	    	//Case 2 - no hit, update priority and tag
		}else{
	    	block.setPriority(priority);
	    	block.setTag(vals.get(TAG));
		}
	}

	public void readFullWay(List<Integer> vals, int priority) {
		Integer hit = 0;
		//Step 1 - reference the index given by addr
		List<Block> blocks = cacheEntry[vals.get(INDEX)];
		//Step 2 - sort the index given by addr
        blocks = blocks.stream().sorted(Comparator.comparingInt(Block::getPriority))
								.collect(Collectors.toList());
		//Step 3 - go through each way and see if tag found
		for(int i = 0; i< this.associativity; i++) {
			//Case 1 - if we have a hit
			if (blocks.get(i).getTag().equals(vals.get(TAG))) {
				//Step 3.1 - replace that block with priority given
				hit = 1;
				this.hits++;
				blocks.get(i).setPriority(priority);
				break;
			}
		}
			//Case 2 - if we dont have a hit replace both the tag and priority
			if (hit == 0) {
			    blocks.get(0).setPriority(priority);
			    blocks.get(0).setTag(vals.get(TAG));
			}//if
	}//end of function

//=====================================Calculation functions =============================================================================\\
		 private Long getAddr(String hex){
			return Long.parseLong(hex, 16);
		 }
		public static Integer logb2(int val){
			return (int)(Math.log((double)val)/Math.log(2.0));
		}

		 public final List<Integer> getValues(String addr, Cache type) {
		 	final List<Integer> vals = new ArrayList<Integer>(3);
		 	final int TAG = 0;
		 	final int BLKOFF = 1;
		 	final int INDEX = 2;
		 	Integer blk;
		 	Integer index;
		 	Integer tag;
		 	Long address = getAddr(addr);
		 	System.out.println(address);
		 	//Step 1 - get rid of byte offset
			 address /=4;
		 	//Step 2 - get block offset value
		 	blk = address.intValue() % (type.blockSize*4); //in bytes
			address /= (type.blockSize*4);
		 	//step 3 - get index value
			 index = address.intValue() % type.indexSize;
			 address /= type.indexSize; //shift to left by logf
		 	tag = address.intValue();

		 	vals.add(TAG, tag);
		 	vals.add(BLKOFF, blk);
		 	vals.add(INDEX, index);

		 	System.out.println(type.indexSize);
		 	System.out.println("tag = " + vals.get(TAG));
		 	System.out.println("index = " + vals.get(INDEX));
		 	System.out.println("blk = " + vals.get(BLKOFF));

		 	return vals;
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
		System.out.println("Cache sizes: " + this.sizeTotal + "	"  + "Associativity: " + this.associativity + "		" + "Block size: " + this.blockSize);
		float hit_percent = ((float)this.hits/this.num_reads) * 100;
		System.out.println("Hits: " + this.hits + "	" + "Hit Rate: " + + Math.round(hit_percent*100.0)/100.0 + "%" );
	}
}


