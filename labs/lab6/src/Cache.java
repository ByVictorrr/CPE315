import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Cache
{
	/*Characteristics*/
	private Integer size;
	private Integer blockSize;
	private Integer associativity;

	/*Entry*/
	//using a List for multiple ways;
	//Index ->stream
	private List<Pair<AddrStream[][],Integer>> ways;
	/*Statistics*/
	private Integer hits;
	private Integer num_reads;
	//hit rate = hits/num _reads
	
	public Cache(Integer size, Integer blockSize, Integer associativity) {
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.size = size;
		this.ways = new ArrayList<>(associativity);
		this.hits = 0;
		this.num_reads = 0;
	}
	//TODO : dirty bit
	//Inserts in cache if
	public void readCache(AddrStream stream) {
		Integer hit = 0;
		//Step 1 - go through each way
		for (Pair<AddrStream[][],Integer> way  : ways) {
			//Step 2 - go through each entry see if that tag is in one of
			for (int i = stream.getBlkOffset(); i >= 0; i--) {
				if (way.getKey()[stream.getIndex()][i].getTag().equals(stream.getTag())) {
					hit = 1;
				}
			}
		}
			//Step 2 - if hit == 0 then store this stream
			Integer LRU = 0;
			int j = 0;
			if(hit == 0){
				for (int i = 0; i< associativity; i++){
					if(i == 0)
						LRU = ways.get(i).getValue();

				if(LRU <= ways.getValue()){
					j=i;
				}
			}//for
				this.ways.get(j).getKey()[stream.getIndex()][stream.getBlkOffset()];
		}//if


		this.hits += hit;
		this.num_reads++;
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

	public void setWays(List<Pair<AddrStream[][], Integer>> ways) {
		this.ways = ways;
	}

