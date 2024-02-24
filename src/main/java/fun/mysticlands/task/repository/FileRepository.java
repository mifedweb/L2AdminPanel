package fun.mysticlands.task.repository;

import fun.mysticlands.task.model.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface FileRepository extends JpaRepository<FileModel, Long> {

    Logger logger = LoggerFactory.getLogger(FileRepository.class);

    FileModel getFileModelById(Long id);


}
