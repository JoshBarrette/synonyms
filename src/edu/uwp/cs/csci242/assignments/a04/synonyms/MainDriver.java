package edu.uwp.cs.csci242.assignments.a04.synonyms;

/**
 * This assignment uses generic data structures and their methods to parse books and calculate word
 * similarities.
 *<p>
 *   We first define the URLs to parse from, then we split each book into its
 *   sentences and split those into an array of strings. We then step through
 *   the arrays and put the word we are on into a HashMap with the key being
 *   the word and the value being another HashMap with the other words in
 *   the sentence and the amount of times that each word shows up along with
 *   other words.
 *</p>
 *
 * @author Josh Barrette
 * @edu.uwp.cs.242.course CSCI 242 - Computer Science II
 * @edu.uwp.cs.242.section 001
 * @edu.uwp.cs.242.assignment 4
 * @bugs For some reason the Sherlock Holmes book gets parsed as unknown characters.
 */

import java.io.IOException;
import java.net.URL;

public class MainDriver {
    /**
     * Main driver that instantiates the URL array and synonyms object, gives the array to the synonyms object
     * for parsing, then asks the user for inputs to check for word similarities.
     * @param args Classic args.
     * @throws IOException Doesn't actual throw anything, it gets caught in the method.
     */
    public static void main(String[] args) throws IOException {
        // I wanted to make this a class constant but there were too
        // many errors being yelled about so this was just easier.
        URL[] corpus = {
            // Pride and Prejudice, by Jane Austen
            new URL ( "https://www.gutenberg.org/files/1342/1342-0.txt" ),
            // The Adventures of Sherlock Holmes, by A. Conan Doyle
            new URL ( "http://www.gutenberg.org/cache/epub/1661/pg1661.txt" ),
            // A Tale of Two Cities, by Charles Dickens
            new URL ( "https://www.gutenberg.org/files/98/98-0.txt" ),
            // Alice's Adventures In Wonderland, by Lewis Carroll
            new URL ( "https://www.gutenberg.org/files/11/11-0.txt" ),
            // Moby Dick; or The Whale, by Herman Melville
            new URL ( "https://www.gutenberg.org/files/2701/2701-0.txt" ),
            // War and Peace, by Leo Tolstoy
            new URL ( "https://www.gutenberg.org/files/2600/2600-0.txt" ),
            // The Importance of Being Earnest, by Oscar Wilde
            new URL ( "http://www.gutenberg.org/cache/epub/844/pg844.txt" ),
            // The Wisdom of Father Brown, by G.K. Chesterton
            new URL ( "https://www.gutenberg.org/files/223/223-0.txt" ), };

        // Tester corpus.
        URL[] corpusTest = {corpus[1]};

        // Instantiate the synonyms object, give it the corpus, and parse.
        Synonyms syn = new Synonyms();
        syn.parseCorpus(corpus);

        // Run the loop to get input.
        syn.run();
    }
}
