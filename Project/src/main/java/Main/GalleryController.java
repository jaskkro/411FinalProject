package Main;

import java.nio.file.Path;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GalleryController {

    private final Path rootLocation;
    private final ImageRepository imageRepository;

	public GalleryController(Path rootLocation, ImageRepository imageRepository) {
            this.rootLocation = rootLocation;
            this.imageRepository = imageRepository;
	}

	@GetMapping("/")
	public String emptyURLHit(Model model) throws Exception {
            return "redirect:/gallery";
	}
        
        @GetMapping("/gallery")
	public String loadGallery(Model model) throws Exception {
            return "gallery";
	}

}
