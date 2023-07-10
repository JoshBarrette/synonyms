package examples;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ReadUrlMainDriver
{
    public static void main ( String[] args )
    {
        try
        {
            // Create a URL and Scanner
            URL url = new URL ( "http://www.gutenberg.org/cache/epub/25546/pg25546.txt" );

            // Use a scanner to read the
            Scanner s = new Scanner ( url.openStream () );

            // Set the delimiter for the scanner
            s.useDelimiter ( "[.?!]|\\Z" );

            // Read until the end of the URL
            while ( s.hasNext () )
            {
                String sentence = s.next ();
                System.out.println ( sentence );
            }
        }
        catch ( IOException e )
        {
            System.err.println ( "Trouble reading from url: " + e );
        }
    }
}
