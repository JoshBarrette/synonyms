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

import java.net.URL;
import java.util.*;

public class Synonyms {
    /**
     * The array that holds the URL' that we parse.
     */
    private URL[] corpus;

    /**
     * The nested map that holds the words, common words, and counts of common words.
     */
    private HashMap<String, HashMap<String , Integer>> descriptors;

    /**
     * Default constructor that just instantiates the descriptors HashMap.
     */
    public Synonyms() {
        descriptors = new HashMap<>();
    }

    /**
     * Take in the corpus we parse and instantiate the map.
     * @param corpus The corpus we parse.
     */
    public Synonyms(URL[] corpus) {
        // I don't see why we have corpus as an argument for the constructor and
        // for parseCorpus().
        this.corpus = corpus;
        descriptors = new HashMap<>();
    }

    /**
     * Parse the given URLs and populate the map with the data.
     * @param corpus The corpus we parse.
     */
    public void parseCorpus(URL[] corpus) {
        System.out.print("Parsing.\t");

        for (int i = 0; i < corpus.length; i++) {
            // Java doesn't like Scanners like this not being inside of a try.
            try {
                // Make the scanner and set the delimiter.
                Scanner s = new Scanner(corpus[i].openStream());
                s.useDelimiter("[.?!]|\\Z");

                // Clean the words.
                while (s.hasNext()) {
                    String[] temp = s.next().split("\\s+");
//                    System.out.println(Arrays.toString(temp));
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = temp[j].replaceAll("\\W+", "").toLowerCase();
                    }
//                    System.out.println(Arrays.toString(temp));
//
                    // Go through each sentence and populate descriptors.
                    for (int k = 0; k < temp.length; k++) {
                        if (temp[k].length() > 0) {
                            for (int j = 0; j < temp.length; j++) {

                                // Check that k != j so that a word is not added to its own map.
                                if (temp[j].length() > 0 && k != j) {
                                    // If the map does not contain the word.
                                    if (!descriptors.containsKey(temp[k])) {
                                        descriptors.put(temp[k], new HashMap<>());
                                        descriptors.get(temp[k]).put(temp[j], 1);
                                    }
                                    // Check to see if the inner map contains the other word.
                                    else if (descriptors.containsKey(temp[k]) && !descriptors.get(temp[k]).containsKey(temp[j])) {
                                        descriptors.get(temp[k]).put(temp[j], 1);
                                    }
                                    // If they both exists we just update what's there.
                                    else if (descriptors.containsKey(temp[k]) && descriptors.get(temp[k]).containsKey(temp[j])) {
                                        descriptors.get(temp[k]).replace(temp[j], descriptors.get(temp[k]).get(temp[j]), descriptors.get(temp[k]).get(temp[j]) + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Parsing complete.");
    }

    /**
     * Calculate the Cosine Similarity between 2 words.
     * @param word1 The first word we check.
     * @param word2 The second word we check.
     * @return The similarity.
     */
    public double calculateCosineSimilarity(String word1, String word2) {
        // First, we check to see if the denominator is going to be 0.
        // If it is, then we just send back -1.0 because they have no similarity.
        if (Math.sqrt( calcWordMag(word1) * calcWordMag(word2)) == 0.0) {
            return -1.0;
        } else {
            return calcDotProd(word1, word2) / Math.sqrt(calcWordMag(word1) * calcWordMag(word2));
        }
    }

    /**
     * Helper method for calculateCosineSimilarity that calculates the dot product.
     * @param word1 The first word we check.
     * @param word2 The second word we check.
     * @return
     */
    private double calcDotProd(String word1, String word2) {
        // We check to see if the words exist in the map, if they don't then
        // we would get an out of bounds error.
        if (!descriptors.containsKey(word1) || !descriptors.containsKey(word2)) {
            return 0.0;
        }

        // Get our iterator set up.
        Set<Map.Entry<String, Integer>> word1Set = descriptors.get(word1).entrySet();
        Iterator<Map.Entry<String, Integer>> word1It = word1Set.iterator();

        // Counter for the prdoct.
        double counter = 0.0;

        while (word1It.hasNext()) {
            // Since we only need the words in common, we only need to iterate over
            // one of them and then check for common words in word2.
            Map.Entry<String, Integer> tempMap = word1It.next();
            String tempK = tempMap.getKey();

            // If they have one in common we do the math.
            if (descriptors.get(word2).containsKey(tempK)) {
                counter += tempMap.getValue() * descriptors.get(word2).get(tempK);
            }
        }

        // Send back what we get.
        return counter;
    }

    /**
     * Helper method that calculates the magnitude of a given word's vector.
     * @param word The word we check.
     * @return The magnitude.
     */
    private double calcWordMag(String word) {
        // If the word isn't in the map then we send back 0.0.
        if (!descriptors.containsKey(word)) {
            return 0.0;
        }

        // Set up our iterator and counter.
        Set<Map.Entry<String, Integer>> wordSet = descriptors.get(word).entrySet();
        Iterator<Map.Entry<String, Integer>> it = wordSet.iterator();
        double word1Sum = 0.0;

        // Do the math.
        while (it.hasNext()) {
            word1Sum += Math.pow(it.next().getValue(), 2);
        }

        // Send back our sum.
        return word1Sum;
    }

    /**
     * The method that holds the loop to get user input words and print the similarities.
     */
    public void run() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            // Get the input and split it into an array for testing.
            System.out.println("Enter a word:");
            String wordToTest = scan.nextLine();
            System.out.println("Enter the choices");
            String[] tempAr = scan.nextLine().split(" ");

            // Create a counter for the best similarity and run the tests.
            int bestScorer = 0;
            for (int i = 0; i < tempAr.length; i++) {
                try {
                    System.out.println("\t" + tempAr[i] + " " + this.calculateCosineSimilarity(wordToTest, tempAr[i]));
                    if (this.calculateCosineSimilarity(wordToTest, tempAr[i]) >= this.calculateCosineSimilarity(wordToTest, tempAr[bestScorer])) {
                        bestScorer = i;
                    }
                } catch (Exception e) {
                    System.out.println("\t" + tempAr[i] + " " + -1.0);
                    if (this.calculateCosineSimilarity(wordToTest, tempAr[i]) >= this.calculateCosineSimilarity(wordToTest, tempAr[bestScorer])) {
                        bestScorer = i;
                    }
                }
            }

            // Print out the word with the best score.
            System.out.println(tempAr[bestScorer] + "\n");
        }
    }

    /**
     * Set the corpus.
     * @param c New corpus.
     */
    public void setCorpus(URL[] c) {
        this.corpus = c;
    }

    /**
     * Get the corpus.
     * @return The corpus.
     */
    public URL[] getCorpus() {
        return this.corpus;
    }
}
