import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lab6 {


    public static void main(String[] args) {
        //String filePath = args[0];
        List<AddrStream> addrStream = new ArrayList<>();
        List<String> addrStreamString = new ArrayList<>();


        ///================CACHE stuff=====================//
        List<Cache> caches = new ArrayList<>();
        final Integer numCaches = 1;
        //List<Integer> sizes = Arrays.asList(2, 2, 2, 2, 2, 2, 4);
        List<Integer> sizes = Arrays.asList(2);
        //List<Integer> blockSizes = Arrays.asList(1, 2, 4, 1, 1, 4, 1);
        List<Integer> blockSizes = Arrays.asList(1);
       // List<Integer> associativities = Arrays.asList(1, 1, 1, 2, 4, 4, 1);
        List<Integer> associativities = Arrays.asList(1);
        //=========================================//


        for (int i = 0; i < numCaches; i++)
            caches.add(i, new Cache(sizes.get(i), blockSizes.get(i), associativities.get(i)));

        //============Test 1 - Address stream===========================//
        /*String test = "1fffff58";
        AddrStream stream = new AddrStream(test, caches.get(0));
        System.out.println("Tag = " + stream.getTag());
        System.out.println("Index = " + stream.getIndex());
        System.out.println("Block offset = " + stream.getBlkOffset());
         System.out.println("Byte offset = " + stream.getByteOffset());*/
        //===============================================================//

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
            addrStreamString = stream.map(lab6::removeSpaceAndNum).collect(Collectors.toList());
        } catch (IOException e) {
            e.getSuppressed();
        }

        //Step 3 - make 7 different addr streams for the 7 diff caches
        for (int i = 0; i< numCaches; i++){
            for (String aStream : addrStreamString)
                addrStream.add(new AddrStream(aStream,caches.get(i)));
        }
        //Step 4 - use Address stream ith to store into cache
        for(AddrStream stream: addrStream){
            for(int i = 0; i<caches.size(); i++)
                caches.get(i).readCache(stream);
        }

         for(int i = 0; i<caches.size(); i++)
                caches.get(i).printResults(i+1,sizes.get(i));

    }
    public static String removeSpaceAndNum(String unFiltered){
        return unFiltered.substring(1,unFiltered.length()-1).trim();
    }

}
