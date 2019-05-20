import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

public class word_frequency{

    static CircularDoublyLinkedList<String> words = new CircularDoublyLinkedList<String>();
    
    private static File getFile(String fileName){
        File file = null;
        file = new File(fileName);
        return file;
    }

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
            ParseLine(reader.nextLine());
        }
            
    } 

    /*
     * Ayrılmayanlar: ^ [] {} 
     * Boşluklar: ()
     */
    public static void ParseLine(String line){

        char c = '[';
        int num = (int)c;

        //line = line.replaceAll("^\\W", " "); Can be used for only english users.
        
        
        //getting rid of chars that are not alphanum.Putting space
        line = line.replaceAll("[!-/]", " ");
        line = line.replaceAll("[:-@]", " ");
// TODO       line = line.replaceAll("[[-`]", " "); 
        line = line.replaceAll("[{-~]", " ");
        line = line.replaceAll("  ", " ");
        
        //getting words by splitting the spaces we put above.
        String[] wordArray = line.split(" ");

        for(String word : wordArray){
            addToList(word);
            System.out.println(words);
        }
        
    }


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