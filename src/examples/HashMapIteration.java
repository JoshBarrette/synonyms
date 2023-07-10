package examples;

import java.io.File;
import java.util.*;

/**
 * This class illustrates how to iterate across a HashMap.
 *
 * @version October 2019
 */
public class HashMapIteration
{
    static HashMap<String, Integer> populations = new HashMap<>();

    public static void main ( String[] args )
    {
        readInPopulations ( "StatePopulations.txt" );

        // Iterate across the map, adding up the populations
        int usPopulation = 0;

        // The set of all keys in the population map
        Set<String> keys = populations.keySet();
        Iterator<String> it = keys.iterator();
        while ( it.hasNext() )
        {
            String key = it.next();
            usPopulation += populations.getOrDefault ( key, 0 );
        }
        System.out.println ( "The populations of the U.S. is: " + usPopulation );

        // There are several other ways to iterate across a map
        // Collection<Integer> valueCollection = populations.values();
        // Set<Map.Entry<String, Integer>> entries = populations.entrySet();
    }


    /**
     * This method reads state populations from a file.
     * Since some state names have multiple words we delimit the fields
     * in the file using tabs instead of spaces
     * @param filename
     */
    public static void readInPopulations ( String filename )
    {
        try
        {
            Scanner fin = new Scanner ( new File ( filename ) );

            // The useDelimiter says use tabs or end of lines
            // as the delimiter. In particular, we do NOT use spaces
            fin.useDelimiter ( "(\\t|\\r\\n)" );
            while ( fin.hasNext() )
            {
                int num = fin.nextInt();
                String state = fin.next();
                int pop = fin.nextInt();
                populations.put ( state, pop );
                fin.nextLine();
            }
        }
        catch ( Exception e )
        {
            System.err.println ( "Problem reading from file: " + filename );
        }
    }
}