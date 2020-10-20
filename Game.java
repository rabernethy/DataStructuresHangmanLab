/* Game,java, a java program written by: Russell Abernethy 10/20/20 */

import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {

        HashSet<String> allTheWords = new HashSet<>();
        
        // Read in the file to the list.
        try {
            File file = new File("words.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                allTheWords.add(scanner.nextLine());
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("The file could not be found.");
        }
        // check that the list has been entered
        
        for (String word : allTheWords) {
            System.out.println(word);
        }
        System.out.println(allTheWords.size());



    }





}
/*
Project Outline:
    1. Read in the dictionary and store for runtime.
    2. Ask the user to choose the size of the word they are trying to guess. (Repeat if there are no words of that size)
*/