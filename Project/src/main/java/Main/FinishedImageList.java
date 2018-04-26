/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Big Bertha
 */
public class FinishedImageList {
    
    private ArrayList finishedImages;
    
    public FinishedImageList() {
        finishedImages = new ArrayList<FinishedImage>();
    }
    
    public void addImage(FinishedImage toAdd) {
        finishedImages.add(toAdd);
    }
    
    public ArrayList getAllImages() {
        return finishedImages;
    }
    
    public int getQuantity() {
        return finishedImages.size();
    }
    
    public FinishedImage getImageAt(int loc) {
        return (FinishedImage) finishedImages.get(loc);
    }
    
    public  ArrayNode serializeAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        
        for (int i = 0; i < this.getQuantity(); i++) {
            arrayNode.add(this.getImageAt(i).serializeAsJSON());
        }
        
        return arrayNode;
    }
    
    public static FinishedImageList deserializeJSON(ArrayNode deSerializeMe) {
        FinishedImageList toReturn = new FinishedImageList();
        
        if (deSerializeMe.isArray()) {
            for (final JsonNode objNode : deSerializeMe) {
                toReturn.addImage(FinishedImage.deSerializeJSON((ObjectNode) objNode));
            }
        }
      
        return toReturn;
    }
    
    public void saveJSONToFile (ArrayNode node, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(filename), node);
    }
    
    public FinishedImageList readJSONFromFile (String filename) throws IOException {
        FinishedImageList toReturn = new FinishedImageList();
        File checkDir = new File(filename);
        
        boolean existingFile = checkDir.exists();
        if (existingFile) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode read = (ArrayNode) mapper.readTree(checkDir);
            toReturn = FinishedImageList.deserializeJSON(read);
        }
        
        return toReturn;
    }
    
    public String getGalleryHTML(String filter) {
        String toReturn = "";
        if(finishedImages.size() > 0) {
            toReturn = "<div class=\"row\">";
            for (int i = 0; i < finishedImages.size(); i++) {
                if (i%4==0) {
                    toReturn += "<div class=\"column\">";
                }
                FinishedImage prep = (FinishedImage) finishedImages.get(i);
                String prepName = prep.getName();
                prepName = prepName.substring(26);
                toReturn += "<img th:src=\"@{"+prepName+"}\" height=\"250\"/></img>";
                if (i%4==3) {
                    toReturn += "</div>";
                }         
            }
            toReturn += "</div>";          
        }
       return toReturn; 
    }
    
}
