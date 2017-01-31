package methodseditfile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MethodsEditFile {
    
    public static int getOption(){
        System.out.println("SELECT ONE OF THE FOLLOWING OPTIONS");
        System.out.println("0, to exit");
        System.out.println("1, to add \"Hello world to the file\"");
        Scanner input = new Scanner(System.in);
         
        switch (input.nextInt()){
            case 0:
                return 0;
            case 1: 
                return 1;  
            default:
                 return getOption();  
        }
    }

    public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("Notepad.exe", "file.txt");
        processBuilder.start();
        
        if (getOption() == 1){
            try(FileWriter fileWrite = new FileWriter("file.txt", true);
                BufferedWriter bufferedWrite = new BufferedWriter(fileWrite);
                PrintWriter printWrite = new PrintWriter(bufferedWrite))
            {
                printWrite.println("Hello world to the file");
                processBuilder.redirectInput();
            } catch (IOException e) {
                System.out.println("IOEXception caught\n");
                System.out.println(e.toString());
            }
        }
    }  
}
