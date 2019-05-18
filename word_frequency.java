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


    public static void main(String[] args){
    }


}