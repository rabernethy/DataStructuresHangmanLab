/* Game,java, a java program written by: Russell Abernethy 10/20/20 */
import java.util.*;
import java.io.*;


public class Game {
    public static void main(String[] args) {
        // Read in words from file:
        Set<String> words = readInWords(new File("words.txt"));

        // Ask the user how long they would like the word to be.
        Scanner scanner = new Scanner(System.in);
        List<String> hiddenWords = getWordLen(words, scanner);
        
        // Ask the user how many guesses they would like to have.
        int numGuess = getNumberGuesses(scanner);
        
        // Construct the word to be displayed to screen.
        String displayWord = getDisplayString(hiddenWords.get(0).length());
        
        // Play the game.
        PlayGame(hiddenWords, numGuess, displayWord, words, scanner);
        scanner.close();
    }

    public static Set<String> readInWords(File file) {
        Set<String> words = new HashSet<>();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) 
                words.add(scanner.nextLine().toLowerCase());
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("The file could not be found.");
        }
        return words;
    }

    public static List<String> getWordLen(Set<String> words, Scanner scanner) {
        //Ask the user the length of the word they would like to guess.
        List<String> hiddenWords = new ArrayList<>();
        int wordLength = 0;
        while(hiddenWords.isEmpty()) {
            System.out.print("Enter the length of the word to be guessed: ");
            wordLength = scanner.nextInt();
            for (String word : words) {
                if(word.length() == wordLength) {
                    hiddenWords.add(word);
                }
            }
        }
        return hiddenWords;
    }

    public static int getNumberGuesses(Scanner scanner) {
        int guesses = 0;
        while(guesses <= 0) {
            System.out.print("Enter the number of guesses you get (must be a positive number): ");
            guesses = scanner.nextInt();
        }
        return guesses;
    }

    public static String getDisplayString(int wordLength) {
        String retval = "";
        for(int i = 0; i < wordLength; i++)
            retval+="_";
        return retval;
    }

    public static void PlayGame(List<String> hiddenWords, int numGuess, String displayWord,Set<String> words, Scanner scanner) {
        HashSet<Character> letters = new HashSet<>();
        Map<String, List<String>> families;

        // When the user runs out of turns, the game ends.
        for(int i = numGuess; i > 0; i--) {

            // Print out the current game state and promt the user to enter a character until they enter an un entered character.
            System.out.print("\nWord: " + displayWord + "\nGuessed Letters: " + letters + "\nGuesses left: " + i + "\nEnter a letter to guess: ");
            String guess = scanner.next();
            while(letters.contains(guess.charAt(0))) {
                System.out.print("That character has already been entered. Enter a letter to guess: ");
                guess = scanner.next();
            }
            // Save the user's guess.
            letters.add(guess.charAt(0));

            // Construct families of words from the remaining words and select the largest to use for the next round.
            families = getFamilies(hiddenWords, letters);

            // Give the user an extra guess if they correctly guessed a character.
            i = (displayWord.equals(largestFamily(families))) ? i : i+1;

            // Make the List of hidden words contain only the words from the largst family and set displayWord to that family.
            displayWord = largestFamily(families);
            hiddenWords = families.get(displayWord);
            
            // If there are no letters left to guess, the user wins.
            if(! displayWord.contains("_")) 
                break;
        }

        // End of Game:
        if(displayWord.contains("_"))
            System.out.println("\nSorry, Game Over! The word was '" + hiddenWords.get(0) + "'.");
        else
            System.out.println("\nWinner! The word was '" + displayWord + "'.");

        // Play again?
        playAgain(words, scanner);
    }



    public static Map<String, List<String>> getFamilies(List<String> hiddenWords, Set<Character> letters) {
        // Generates a map of possible outputs for a given guess
        Map<String, List<String>> families = new HashMap<>();
        // Loop through every item in the list of possible words:
        for (String word : hiddenWords) {
            String wordFamily = "";
            // Construct a version of the word that would be displayed if it was the chosen word.
            for(int i = 0; i < word.length(); i++)
                wordFamily += (letters.contains(word.charAt(i))) ? word.charAt(i) : "_";
        
            // If the key already exists in the map, add the word to the key's list of words.
            if(families.containsKey(wordFamily)) {
                List<String> temp = families.get(wordFamily);
                temp.add(word);
                families.put(wordFamily, temp);
            }
            // The key has not been added to the map already.
            else {
                List<String> temp = new ArrayList<>();
                temp.add(word);
                families.put(wordFamily, temp);
            }
        }
        return families;
    }

    public static String largestFamily(Map<String, List<String>> families) {
        String retVal = "";
        int size = 0;
        // Loop over all the families and return the one with the largest cardinality.
        for (Map.Entry<String, List<String>> entry : families.entrySet()) {
            if(entry.getValue().size() > size) {
                size = entry.getValue().size();
                retVal = entry.getKey();
            }
        }
        return retVal;
    }

    public static void playAgain(Set<String> words, Scanner scanner) {
        System.out.print("Would you like to play again? (Y/N): ");
        String choice = "";
        while(! choice.toLowerCase().startsWith("n") && ! choice.toLowerCase().startsWith("y"))
            choice = scanner.next().toLowerCase();
        // If yes, reset the game and start again.
        if(choice.startsWith("y")) {
            // Do all the setup then start the game again.
            List<String> hiddenWords = getWordLen(words, scanner);
            int numGuesses = getNumberGuesses(scanner);
            String displayWord = getDisplayString(hiddenWords.get(0).length());

            // Start the game.
            PlayGame(hiddenWords, numGuesses, displayWord, words, scanner);
        }
    }

}
