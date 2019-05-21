import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

public class word_frequency{

    //Holds all the words.
    static CircularDoublyLinkedList<String> words = new CircularDoublyLinkedList<String>();
    
    //Reads the file and returns it.
    private static File getFile(String fileName){
        File file = null;
        file = new File(fileName);
        return file;
    }

    /*Parses throught the file and sends every line to 
    lineParser
    */
    private static void fileParser(String fileName){
        Scanner reader = null;
        try{

            File newFile = getFile("./"+fileName);
            reader = new Scanner(newFile);

        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File NOT FOUND!!");
            System.exit(0);
        }
        while(reader.hasNextLine()){
            lineParser(reader.nextLine());
        }
            
    } 


    //Parses throught the line.Sends words to addToList method of CDLL
    public static void lineParser(String line){ 
        
        //getting rid of chars that are not alphanum.Putting space
        line = line.replaceAll("[^A-Za-z0-9]", " ");

        //two specials in a row. 
        line = line.replaceAll("  ", " "); 

        //if it has 2 or even number ofspecials and spaces.
        line = line.replaceAll("  ", " ");

        //if it has 3 or odd number of specials and spaces.
        line = line.replaceAll("  ", " ");


        //getting words by splitting the spaces we put above.
        String[] wordArray = line.split(" ");

        for(String word : wordArray){
            addToList(word);
            System.out.println(words);
        }
        
    }


    /** If list has the element adds Frequency and uses Insertion Sort
     * If not,Adds to last
     * (checks alphabethic order of the 1 frequency words too.)
     * */
    private static void addToList(String word){

        if( !words.isThere( word.toLowerCase() ) ){
            words.addLast(word.toLowerCase());
            return;
        }

        words.addFreq(word.toLowerCase());
    
    }

    public static void main(String[] args){

        fileParser("textFile.txt");        
        System.out.println(words);
    }


}