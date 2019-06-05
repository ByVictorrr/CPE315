import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lab6 {


    public static void main(String[] args) {


///================CACHE stuff=====================//
        final List<Cache> caches = new ArrayList<>(7);
        final Integer numCaches = 7;
        final List<Integer> sizes = Arrays.asList(2, 2, 2, 2, 2, 2, 4);
        final List<Integer> blockSizes = Arrays.asList(1, 2, 4, 1, 1, 4, 1);
        final List<Integer> associativities = Arrays.asList(1, 1, 1, 2, 4, 4, 1);

        for(int i = 0; i< numCaches; i++)
            caches.add(i, new Cache(sizes.get(i), blockSizes.get(i), associativities.get(i)));
        //==================================================//

        List<String> addrStreamString = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
            addrStreamString = stream.map(lab6::removeSpaceAndNum).collect(Collectors.toList());
        } catch (IOException e) {
            e.getSuppressed();
        }
        int priority = 0;
        for(String addr: addrStreamString){
            for(int i = 0; i < 7; i++){
               caches.get(i).readAddr(addr, priority);
           }
           priority++;
        }

        int j = 0;
        //print results/
        for(Cache c: caches) {
            c.printResults(j+1,sizes.get(j));
            j++;
        }

    }

    public static String removeSpaceAndNum(String unFiltered) {
        return unFiltered.substring(1, unFiltered.length() - 1).trim();
    }

}