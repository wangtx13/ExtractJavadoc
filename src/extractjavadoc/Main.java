/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package extractjavadoc;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author apple
 */
public class Main {
    
    public static boolean createDir(String dirPath) {
        File dir = new File(dirPath);
        if(dir.exists()) {
            System.out.println("The folder has existed");
            return false;
        }
        if(!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        if(dir.mkdirs()) {
            System.out.println("create successful: " + dirPath);
            return true;
        } else {
            System.out.println("create fail...");
            return false;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Add timestamp to folder name
        Timestamp ts = new Timestamp(System.currentTimeMillis()); 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        String timeStampStr = sdf.format(ts);
        
        //Create a new folder
        String folderPath = "/Users/apple/NetBeansProjects/ExtractJavadoc/output/extract-javadoc-content-" + timeStampStr + "/";
        createDir(folderPath);
        
        File inputRootFile = new File("/Users/apple/NetBeansProjects/ExtractJavadoc/javadoc");
        ArrayList<String> path = new ArrayList<>();
        if(!inputRootFile.isDirectory()) {
            System.out.println("Please input a extisted directory.");
        } else {
            TraversalFiles.fileList(inputRootFile, 0, path, folderPath);
        }
        
        
    }
}
