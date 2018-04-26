package Main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FinishedImage {
		
	private String name;
        private ArrayList<String> tags = new ArrayList<>();
	
	public FinishedImage(){};

	public FinishedImage(String name) {
            this.name = name;
	}
        
        public FinishedImage(HashMap input) {
            name = (String) input.get("name");
            try {
                for (int i = 0; i < 4; i++) {
                    tags.add((String) input.get("tag" + i));
                }
            } catch (Exception e) {}
        }

	public String getName() {
            return name;
	}

	public void setName(String name) {
            this.name = name;
	}
        
        public void addTag(String tag) {
            this.tags.add(tag.toLowerCase());
        }
        
        public boolean checkTag(String check) {
            return(this.tags.contains(check.toLowerCase()));
        }	

    public ObjectNode serializeAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode returnNode = mapper.createObjectNode();
        
        returnNode.put("name", name);
        for (int i = 0; i < tags.size(); i++) {
            returnNode.put("tag" + i, tags.get(i));
        }
        
        return returnNode;
    }
    
    public static FinishedImage deSerializeJSON(ObjectNode deSerializeMe) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.convertValue(deSerializeMe, Map.class);
        FinishedImage toReturn = new FinishedImage((HashMap) result);
        System.out.println(toReturn.name);
        return toReturn;
    }

    int getTagCount() {
        return tags.size();
    }

}
