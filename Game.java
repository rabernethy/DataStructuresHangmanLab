/* Game,java, a java program written by: Russell Abernethy 10/20/20 */

import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {

        HashSet<String> words = new HashSet<>();
        
        // Read in the file to the list.
        try {
            File file = new File("words.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("The file could not be found.");
        }

        // Change the scanner to user input.
        Scanner scanner = new Scanner(System.in);

        //Ask the user the length of the word they would like to guess.
        HashSet<String> hiddenWords = new HashSet<>();
        while(hiddenWords.size() <= 0) {
            System.out.print("Enter the length of the word to be guessed: ");
            int guessLength = scanner.nextInt();
            for (String word : words) {
                if(word.length() == guessLength) {
                    hiddenWords.add(word);
                }
            }
        }
        
        //Ask the user how many guesses they get.
        System.out.print("\nEnter the number of guesses you get: ");
        int numGuess = scanner.nextInt();
        
        
        
        
        
        // check that the list has been entered
        for (String word : hiddenWords) {
            System.out.println(word);
        }
        System.out.println(hiddenWords.size());



    }





}
/*
Project Outline:
    1. Read in the dictionary and store for runtime.
    2. Ask the user to choose the size of the word they are trying to guess. (Repeat if there are no words of that size).
    3. Ask the user how many guesses they get before they lose.
    4. Create an initial list of hidden words for the game.
    5. Play the game:
        a. Print out the revealed letters, wrong guesses, and the remaining number of wrong guesses
        b. Get the user's new guess and ask them to re enter if they entered a character already entered.
        c. Seperate your list of hidden words into families base on input.
        d. Pick the best family to continue with.
        e. Keep going until:
            - if all letters are revealed, the player wins.
            - if the player is out of guesses, pick a hidden word from the hidden list and reveal it.
    6. Ask if they want to play again.
*/