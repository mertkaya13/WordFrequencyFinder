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

    public static void ParseLine(String line){

        line = line.replaceAll("[!-/]", " ");
        line = line.replaceAll("[:-@]", " ");
        
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