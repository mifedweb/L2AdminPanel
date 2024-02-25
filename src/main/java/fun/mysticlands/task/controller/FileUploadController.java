package fun.mysticlands.task.controller;

import fun.mysticlands.task.impl.FileServiceImpl;
import fun.mysticlands.task.model.FileModel;
import fun.mysticlands.task.repository.FileRepository;
import fun.mysticlands.task.repository.TaskRepository;
import fun.mysticlands.task.repository.UserRepository;
import fun.mysticlands.task.services.FileService;
import fun.mysticlands.task.services.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FileServiceImpl fileService;
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${upload.dir}")
    private String uploadDir;
    @GetMapping("/download")
    public ResponseEntity<UrlResource> downloadFile(@RequestParam Long id) {
        FileModel file = fileRepository.findById(id).orElse(null);
        if (file != null) {
            // Получение пути к файлу из объекта FileModel
            Path pathToFile = Paths.get(file.getFilePath());
            UrlResource resource;
            try {
                resource = new UrlResource(pathToFile.toUri());
                if (resource.exists() && resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(file.getContentType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                            .body(resource);
                } else {
                    // Обработка случая, когда файл не найден или недоступен
                    // Возможно, стоит вернуть ResponseEntity.notFound().
                }
            } catch (MalformedURLException e) {
                // Обработка ошибки, если URL файла неверный
                // Возможно, стоит вернуть ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
            }
        }
        // Обработка случая, когда файл не найден по указанному id
        // Возможно, стоит вернуть ResponseEntity.notFound().
        return null;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String description, @RequestParam String fileType, @RequestParam String version, Model model) {
        String errorMessage = null;

        if (!file.isEmpty()) {
            try {
                FileModel.FileType fileTypeEnum = FileModel.FileType.valueOf(fileType);
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + File.separator + fileName);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Files.write(filePath, file.getBytes());

                FileModel fileModel = new FileModel();
                fileModel.setFileName(fileName);
                fileModel.setVersion(version);
                fileModel.setFileType(fileTypeEnum);
                fileModel.setId_owner(userRepository.getUserByName(auth.getName()).getId());
                fileModel.setFilePath(filePath.toString());
                fileModel.setContentType(file.getContentType());
                fileModel.setDescription(description);
                fileRepository.save(fileModel);

                return "redirect:/files";
            } catch (IOException e) {
                errorMessage = "Ошибка при загрузке файла: " + e.getMessage();
            }
        } else {
            errorMessage = "Файл пустой";
        }

        model.addAttribute("errorMessage", errorMessage);
        List<FileModel> files = fileRepository.findAll();
        fileService.logAllFiles();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        model.addAttribute("fileservice", fileService);
        model.addAttribute("files", files);
        model.addAttribute("users", userRepository);
        model.addAttribute("userService", userService);
        return "files";
    }

    @GetMapping("/files")
    public String showFileList(Model model) {
        List<FileModel> files = fileRepository.findAll();
        fileService.logAllFiles();
        files.forEach(file -> logger.info("File ID: {}, Name: {}", file.getId(), file.getFileName()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        model.addAttribute("fileservice", fileService);
        model.addAttribute("files", files);
        model.addAttribute("users", userRepository);
        model.addAttribute("userService", userService);
        return "files";
    }
}
