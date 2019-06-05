import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	//Index ->stream
	private List<Map<Integer, Line>> ways;
	/*Statistics*/
	private Integer hits;
	private Integer num_reads;
	//hit rate = hits/num _reads
	
	public Cache(Integer size, Integer blockSize, Integer associativity) {
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.size = size;
		this.indexSize = AddrStream.logb2(size/blockSize);
		this.ways = new ArrayList<>(associativity);

		for(int i = 0; i < associativity ; i++){
			for(int j = 0; j < this.indexSize; j++)
			this.ways.get(i).put(j, new Line(this.blockSize));
		}
		this.hits = 0;
		this.num_reads = 0;
	}
	//TODO : dirty bit
	//Inserts in cache if
	public void readCache(AddrStream stream) {
		Integer hit = 0;
		List<Integer> list_LRU = new ArrayList<>();
		int j = 0;
		//Step 1 - go through each way
        for(Map<Integer, Line> way: ways) {
			//Step 2 - go through each entry see if that tag is in one of
			for (int i = stream.getBlkOffset(); i >= 0; i--) {
				//Case 1 - if the stream index has the same tag as one of the tags in that line
				if (way.get(stream.getIndex()).getLineMap().get(i).getTag().equals(stream.getTag())) {
					hit = 1;
				}//if
			}//for
			//Find the highest LRU if there is hit
			if (hit == 1){
				list_LRU.set(j++, way.get(stream.getIndex()).getLRU());
			}//if
		}//for
       //========================================================================================//
        //Step 3 - see if hit == 0, therefore we need to insert, and find the least recent line in ith way
		if(hit == 0){
			ways.get(maxIndex(list_LRU)).get(

			)
		}
				this.ways.get(j).getKey()[stream.getIndex()][stream.getBlkOffset()];
		//==========================================================================//


		this.hits += hit;
		this.num_reads++;
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

	public void setWays(List<Pair<AddrStream[][], Integer>> ways) {
		this.ways = ways;
	}

