package Main;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GalleryController {
    
    String search = "";

	public GalleryController() {
	}

	@GetMapping("/")
	public String emptyURLHit(Model model) throws Exception {
            return "redirect:/gallery";
	}
        
        @GetMapping("/gallery")
	public String loadGallery(Model model) throws Exception {
            List<String> files = Application.finishedImages.getGalleryHTML("");
            model.addAttribute("files", files);
            model.addAttribute("search", this.search);
            return "gallery";
	}
        
        @PostMapping("/gallery")
        public String filterGallery(Model model, @RequestParam String search) throws Exception {
            this.search = search;
            System.out.println(this.search);
            List<String> files = Application.finishedImages.getGalleryHTML(this.search);
            model.addAttribute("files", files);
            model.addAttribute("search", this.search);            
            return "gallery";
        }

}
