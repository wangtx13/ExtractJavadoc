/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package extractjavadoc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author apple
 */
public class TraversalFiles {
    
    //traversal all files
    public static void fileList(File inputFile, int node, ArrayList<String> path, String folderPath) {
        node ++;
        File[] files = inputFile.listFiles();
        if (!inputFile.exists()){
            System.out.println("File doesn't exist!");
        } else if(inputFile.isDirectory()) {
            path.add(inputFile.getName());
            
            for (File f : files) {
                for(int i = 0; i < node - 1; i ++) {
                    System.out.print(" ");
                }
                System.out.print("|-" + f.getPath());

                String ext = FilenameUtils.getExtension(f.getName());
                if(ext.equals("html")) {
                    try {
                        System.out.println(" => extracted");
                        
                        //Get extracted file location and add it to output file name,
                        //in order to avoid files in different folder 
                        //have the same name.
                        String fileLocation = "";
                        for(String tmpPath: path) {
                            fileLocation += "-" + tmpPath ;
                        }
                        
                        String outFilePath = folderPath + "/" + f.getName() + fileLocation + "-html.txt";
                        
                        //create output file
                        File outputFile = new File(outFilePath);
                        if(outputFile.createNewFile()) {
                            System.out.println("Create successful: " + outputFile.getName());
                        }   
                        
                        //extract comments
                        ExtractHTMLContent.extractHTMLContent(f, outputFile);
                    } catch (IOException ex) {
                        Logger.getLogger(TraversalFiles.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                   System.out.println(" isn't a html file.");
                } 
                fileList(f, node, path, folderPath);
            }
            path.remove(node - 1);
        }
    }
    
    
}
