package Main;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FinishedImage {
	
	@Id
	@GeneratedValue
	private Long id;	
	private String name;
        private ArrayList<String> tags;
	
	public FinishedImage(){};

	public FinishedImage(String name) {
            this.name = name;
	}

	public Long getId() {
            return id;
	}

	public void setId(Long id) {
            this.id = id;
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

	@Override
	public String toString() {
            return "Image [id=" + id + ", name=" + name + "]";
	}	

}
