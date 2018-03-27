package Main;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<FinishedImage, Long>{

	FinishedImage findByName(String text);

	FinishedImage findFirstByName(String text);

}
