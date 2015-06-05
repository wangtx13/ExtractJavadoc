/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractjavadoc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;

/**
 *
 * @author apple
 */
public class ExtractHTMLContent {

    private File inputFile;
    private File outputFile;
    private boolean ifGeneral;
    private Map<String, Boolean> libraryTypeCondition;

    public ExtractHTMLContent(File inputFile, File outputFile, boolean ifGeneral, Map<String, Boolean> libraryTypeCondition) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.ifGeneral = ifGeneral;
        this.libraryTypeCondition = libraryTypeCondition;
    }

    public void extractHTMLContent() {
        String content = "";
        String line = null;
        StringBuffer extractResult = new StringBuffer();
        try {
            try (
                    InputStream in = new FileInputStream(inputFile.getPath());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.getPath()))) {
                while ((line = reader.readLine()) != null) {
                    content += line;
                }

                Document doc = Jsoup.parse(content);
                Elements pre_content = doc.getElementsByTag("PRE");
                Elements dl_content = doc.getElementsByTag("DL");

                for (Element pre : pre_content) {
                    String tag = pre.tagName();
                    if (tag.equals("pre")) {
                        String pre_text = pre.text();
                        String pre_clean = Jsoup.clean(pre_text, Whitelist.none());
                        if (pre_clean.length() > 0) {
                            extractResult.append(pre_clean).append("\r\n");                          
                        }
                    }

                }

                for (Element dl : dl_content) {
                    String tag = dl.tagName();
                    if (tag.equals("dl")) {
                        String dl_text = dl.text();
                        String dl_clean = Jsoup.clean(dl_text, Whitelist.none());
                        if (dl_clean.length() > 0) {
                            extractResult.append(dl_clean).append("\r\n");
                        }
                    }

                }
                
                ParseWords parseWordsTool = new ParseWords(extractResult, ifGeneral, libraryTypeCondition);
                extractResult = parseWordsTool.parseAllWords();
                writer.write(extractResult.toString());
                writer.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ExtractHTMLContent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
