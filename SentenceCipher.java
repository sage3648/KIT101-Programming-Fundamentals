/**
 * SentenceCipher is a simple program to encrypt and decrypt words
 * @author Sage Stainsby 390889
 * @version final 18/04/2016
 */

import java.util.Scanner; //import the scanner object to be used throughout the program


public class SentenceCipher {

  
    public static void main(String[] args) {
        
        
       final int LENGTH; //a finalinteger to hold the length of characters in the source text 
       final int SLENGTH; //a final integer to hold the length of characters in the 
       String source; //a string to hold the source text added by the user
       int wordLength; //an integer to hold the length of the word for decryption
       char pos; //a char that holds the 
       String word; //a string to hold the user inputted word for encryption
       int encryption; //an integer to hold the encrypted data before it is appended into the results string
       char decrypt; //an integer to hold the results of the charAt statement in the decryption loop
       double charNotFound = 0; //a double for loss equation with characters not found
       int charNotFoundI = 0; //an integer to correctly display number of chars not found 
       String results; //a string to hold the encrypted and decrypted message
       
       Scanner sc = new Scanner(System.in); //initialising the scanner object sc
       StringBuilder sb = new StringBuilder();//initialising the StringBuilder objec sb for appending strings
       
       
       System.out.println("Sentence Cipher");
       System.out.print("Enter source text: ");
       source = sc.nextLine(); //takes user input and puts it in the "source" string
       source = source.toLowerCase();  //coverts the source input to lower case
       SLENGTH = source.length(); //takes the length of the source text and puts it in the sLength variable
       System.out.println("Select a cipher option: "); 
       System.out.println("1. Encrypt ");
       System.out.println("2. Decrypt ");
       System.out.print("Choice: ");
       int choice = sc.nextInt(); //takes input from the user and puts it into the choice variable
       
       
       if (choice == 1){ //the user chose to encrypt
         
         
        System.out.print("Enter a word  to encypt: ");
        word = sc.next(); //takes input from user in only one word
        word = word.toLowerCase(); //coverts word to lower case places it back in the word variable
        LENGTH = word.length(); //takes the lenth of the word and puts an integer value into the LENGTH variable
        
       
       
            for (int i = 0; i < LENGTH; i++ ){ //initialises i variable and then increments it by one until it exeeds the length of the word
         
 
              pos = word.charAt(i);//gets the position of i from the word string
              encryption = source.indexOf(pos); //puts the index of source from the pos variable and then puts it in the encryption variable
              
          
              if (encryption == -1){ //loop to count number of times encryption equals -1 for information loss
              
                
                    charNotFound = charNotFound + 1; //increments the number of character not found for later use in data loss equation
                    
         
               }
           
         
            sb.append(encryption+ " ");  //appends the data in encryption and a space into the StringBuidler variable
          
          
            } 
       
       
       System.out.println("Result: " +sb+ "-2");           
       double infoLoss = charNotFound/LENGTH*100;  //equation for data loss in encryption
       System.out.println("Information loss (%): "+infoLoss);
       
                 
        }
       
       
       if (choice == 2){//the user chose to decrpyt
           
           
           System.out.print("Enter positions (-2 to end): ");
           
           
           int num = 0; //a variable to control how long the loop runs in the do-while statement
           
           
           do {
               
              
                int code = sc.nextInt(); //takes input from the user to be used to decrypt
                
                
                if (code < SLENGTH){
                  
                  
                  if (code == -1){ 
                    
                    results = "_";
                    sb.append(results); //appends results in the String Builder
                    
                  }
                                
                
                  if (code == -2){ 
                    
                    num = 99; //changes the num variable to 99 ending the do-while loop
                    
                  }
                
                  if (code < 0){
                    
                    charNotFound = charNotFound + 1;
                    
                
                  } else {
                    
                    decrypt = source.charAt(code); //finds the value of decrypt in the source text adds in to the code integer                    
                    sb.append(decrypt);
                                  
                    }
                               
                }  else {
                  
                  results = "!";
                  sb.append(results);
                  charNotFound = charNotFound + 1;
                  
                }
                                
                
                if (code < -2){ 
                  
                    results = "!";
                    sb.append(results);                   
                    
                }
                
               
           } while (num != 99);
           
           
           wordLength = sb.length();//takes the length of the StringBuilder and gives wordLenth an integer vale of that length
           charNotFound = charNotFound -1;
           
           if (charNotFound > 0){
             
             charNotFoundI = (int)charNotFound; //coverts the int from the charNotFound double variable to the correct display integer variable 
             System.out.println (charNotFoundI + " invalid position(s)" );
             
               
           }
           
           
           System.out.println("Result: "+sb); //results of decryption           
           double infoLoss = charNotFound/wordLength*100;  //decrpyt data loss equation
           System.out.println("Information loss (%): "+infoLoss);
          
           
       }
            
        
        
    }
    
}
