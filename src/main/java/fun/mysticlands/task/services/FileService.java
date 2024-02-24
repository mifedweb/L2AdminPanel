package fun.mysticlands.task.services;

import fun.mysticlands.task.model.FileModel;
import fun.mysticlands.task.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileService {
    void logAllFiles();

    List<FileModel> findAll();
}
