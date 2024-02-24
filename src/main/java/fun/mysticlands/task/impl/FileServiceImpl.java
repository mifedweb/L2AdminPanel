package fun.mysticlands.task.impl;

import fun.mysticlands.task.model.FileModel;
import fun.mysticlands.task.repository.FileRepository;
import fun.mysticlands.task.services.FileService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class FileServiceImpl implements FileService {
@Autowired
    private final FileRepository fileRepository;
    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    public int size(){
        return fileRepository.findAll().size();
    }
    @Override
    public void logAllFiles() {
        List<FileModel> files = fileRepository.findAll();
        if (files.isEmpty()) {
            logger.debug("Репозиторий не содержит файлов");
        } else {
            logger.debug("Список файлов в репозитории:");
            for (FileModel file : files) {
                logger.debug("Имя файла: {}", file.getFileName());
            }
        }
    }
    @Override
    public List<FileModel> findAll() {
        logger.debug("Выполняется запрос на получение всех файлов");
        return fileRepository.findAll();
    }
}
