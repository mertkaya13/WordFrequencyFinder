import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

//Mert KAYA 22 MAY 2019

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
    private static void textFileParser(String fileName){
        Scanner reader = null;
        try{

            File newFile = getFile("./"+fileName);
            reader = new Scanner(newFile);

        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File NOT FOUND!!"+"--"+fileName+"--");
            System.exit(0);
        }
        while(reader.hasNextLine()){
            textlineParser(reader.nextLine());
        }
            
    } //Ends of textFileParser


    //Parses throught the line.Sends words to addToList method of CDLL
    public static void textlineParser(String line){ 
        
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

        }
        
    }//Ends of textlineParser


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

    /**
     * Reads directives line by line then;
     * sends them to doDirectives()
     * 
     */
    private static void readDirectives(File f){
        Scanner reader = null;
        try{

            File newFile = f;
            reader = new Scanner(newFile);

        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File NOT FOUND - directives !!");
            System.exit(0);
        }
        while(reader.hasNextLine()){
            doDirective(reader.nextLine());
        }
            
    }//Ends of readDirectives


    private static void getDirectives(String directiveFileName){
        File directiveFile = getFile(directiveFileName);
        readDirectives(directiveFile);
    }


    /**
     * Splits directive name 
     * gets directive and calls corresponding method in CDLL
     * 
     */
    private static void doDirective(String directiveName){
        String[] directive = directiveName.split(" ");
        int number;
        int number2;
        switch(directive[0])
        {    
     
            case "load":
            textFileParser(directive[1]);
            System.out.println(words);
            break;

            
            case "print-max":
            number = Integer.parseInt(directive[1]);
            words.printMax(number);
            break;


            case "print-min":
            number = Integer.parseInt(directive[1]);
            words.printMin(number);
            break;


            case "print-range":
            number = Integer.parseInt(directive[1]);
            number2 = Integer.parseInt(directive[2]);
            words.printRange(number , number2);
            break;


            case "print-freq":
            words.printFreq(directive[1].toLowerCase());
            break;

            case "print-nth":
            words.printNth(Integer.parseInt(directive[1]));
            break;

            case "truncate-list":
            words.truncateList(Integer.parseInt(directive[1]));
            break;
        }
        
    }//Ends of doDirectives

    public static void main(String[] args){

        if(!args[0].equals("")){
           getDirectives(args[0]);    

        //If no directive file exists.
        }else{
            System.out.println("ERROR - NO DIRECTIVE FILE GIVEN!");
        }    
    }


}