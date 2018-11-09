/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cutfilemodifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Jacob Schaible
 */
public class FileModifier {
    private final String regex = "0 pendown"; // String to search for
    private final String addition = "escape"; // String that will be added
    private final String replacement = addition + "\r\n" + regex; // Adds the new string on the line above the regex
    private final String conditional = "1 pendown"; // String must exist to continue
    
    public FileModifier() {
    }
    
    // Accessors
    public String getRegex() {
        return regex;
    }
    
    public String getAddition() {
        return addition;
    }
    
    public String getReplacement() {
        return replacement;
    }
    
    public String getConditional() {
        return conditional;
    }
    
    public String readFile(File file) {
        String fileString = "";
        // Reads one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(file.getPath());

            // Wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                fileString = this.readLine(line, fileString);
            }   

            // Close file
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            this.showErrorDialog(
                "Unable to open file '" + 
                file.getName() + "'");                
        }
        catch(IOException ex) {
            this.showErrorDialog(
                "Error reading file '" 
                + file.getName() + "'");                  
        }
        //System.out.print("\nFilestring at readFile:\n" + fileString);
        return fileString; // Return the file as string
    }
    
    public String readLine(String s, String fileString) {
        // Adds the next line to the string and returns the whole thing
        fileString = fileString + s + "\r\n";
        return fileString; 
    }
    
    public boolean stringExists(String fileString, String regex) {
        // Checks if the string to be added already exists in the file
        if (fileString.contains(regex)) {
            return true;
        }
        return false;
    }
    
    public String stringReplace(String fileString) {
        // Makes the replacement
        fileString = fileString.replaceFirst(regex, replacement);
        //System.out.print("\nFilestring at stringReplace:\n" + fileString);
        return fileString;
    }
    
    public void writeFile(File file, String fileString) {
        // Saves (overwrites) the file
        //System.out.print("\nFilestring at writeFile:\n" + fileString);
        File fold=new File(file.getPath());
        fold.delete();
        File fnew=new File(file.getPath());

        try {
        FileWriter f2 = new FileWriter(fnew, false);
        f2.write(fileString);
        f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }               
    }
    
    public void showErrorDialog(String s) {
        // Generic error dialog with settable message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }    
}
