/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Big Bertha
 */
public class CleanImageList {
    
    private ArrayList cleanImages = new ArrayList<CleanImage>();
    
    public CleanImageList() {
        cleanImages = new ArrayList<>();
    }
    
    public void addImage(CleanImage toAdd) {
        cleanImages.add(toAdd);
    }
    
    public CleanImage getRandomImage() {
        Random rand = new Random();
        int r = rand.nextInt(cleanImages.size());
        return (CleanImage) cleanImages.get(r);
    }
    
    public static CleanImageList deserializeJSON(ArrayNode deSerializeMe) {
        CleanImageList toReturn = new CleanImageList();
        
        if (deSerializeMe.isArray()) {
            for (final JsonNode objNode : deSerializeMe) {
                toReturn.addImage(CleanImage.deSerializeJSON((ObjectNode) objNode));
            }
        }
      
        return toReturn;
    }
    
    public CleanImageList readJSONFromFile (String filename) throws IOException {
        CleanImageList toReturn = new CleanImageList();
        File checkDir = new File(filename);
        
        boolean existingFile = checkDir.exists();
        if (existingFile) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode read = (ArrayNode) mapper.readTree(checkDir);
            toReturn = CleanImageList.deserializeJSON(read);
        }
        
        return toReturn;
    }
}
