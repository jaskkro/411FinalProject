package Main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GalleryController {

	public GalleryController() {
	}

	@GetMapping("/")
	public String emptyURLHit(Model model) throws Exception {
            return "redirect:/gallery";
	}
        
        @GetMapping("/gallery")
	public String loadGallery(Model model) throws Exception {
            String population = Application.finishedImages.getGalleryHTML("");
            model.addAttribute("pop", population);
            return "gallery";
	}

}
