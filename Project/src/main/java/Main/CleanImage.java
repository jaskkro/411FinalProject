/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Big Bertha
 */
public class CleanImage {
    
    private String name;
    
    public CleanImage(HashMap input) {
        name = (String) input.get("name");        
     }

    public String getName() {
        return name;
    }
    
    public ObjectNode serializeAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode returnNode = mapper.createObjectNode();
        
        returnNode.put("name", name);
        
        return returnNode;
    }
    
    public static CleanImage deSerializeJSON(ObjectNode deSerializeMe) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.convertValue(deSerializeMe, Map.class);
        CleanImage toReturn = new CleanImage((HashMap) result);
        System.out.println(toReturn.name);
        return toReturn;
    }
}
