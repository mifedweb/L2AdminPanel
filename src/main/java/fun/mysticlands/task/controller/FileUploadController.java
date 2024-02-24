package fun.mysticlands.task.controller;

import fun.mysticlands.task.impl.FileServiceImpl;
import fun.mysticlands.task.model.FileModel;
import fun.mysticlands.task.repository.FileRepository;
import fun.mysticlands.task.repository.TaskRepository;
import fun.mysticlands.task.repository.UserRepository;
import fun.mysticlands.task.services.FileService;
import fun.mysticlands.task.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // Путь к папке для сохранения файлов, указанный в файле application.properties
    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String description) {
        // Проверка наличия файла и обработка его
        if (!file.isEmpty()) {
            try {
                // Генерация уникального имени файла
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                // Путь к файлу, куда будет сохранен загруженный файл
                Path filePath = Paths.get(uploadDir + File.separator + fileName);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                // Сохранение файла на файловой системе
                Files.write(filePath, file.getBytes());

                // Создание объекта FileModel и сохранение информации о файле в базе данных
                FileModel fileModel = new FileModel();
                fileModel.setFileName(fileName);
                fileModel.setId_owner(userRepository.getUserByName(auth.getName()).getId());
                fileModel.setFilePath(filePath.toString()); // Сохраняем путь к файлу
                fileModel.setContentType(file.getContentType());
                fileModel.setDescription(description);
                fileRepository.save(fileModel);

                // Возвращаемся на страницу с сообщением об успешной загрузке
                return "files";
            } catch (IOException e) {
                e.printStackTrace();
                // Обработка ошибки
            }
        }
        // Если файл пустой или произошла ошибка, возвращаемся на страницу загрузки с сообщением об ошибке
        return "redirect:/uploadFailure";
    }
    @GetMapping("/upload")
    public String showFileList(Model model) {
        List<FileModel> files = fileService.findAll(); // Получаем список всех файлов из репозитория
        fileService.logAllFiles();
        model.addAttribute("fileservice", fileService); // Передаем список файлов в модель
        model.addAttribute("files", files); // Передаем список файлов в модель
        model.addAttribute("users", userRepository); // Передаем список файлов в модель
        model.addAttribute("userService", userService); // Передаем список файлов в модель
        System.out.println(files.size());
        return "files"; // Возвращаем имя представления (HTML страницы)
    }
}
