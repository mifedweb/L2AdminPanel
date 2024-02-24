package fun.mysticlands.task.controller;

import fun.mysticlands.task.model.Task;
import fun.mysticlands.task.model.User;
import fun.mysticlands.task.repository.TaskRepository;
import fun.mysticlands.task.repository.UserRepository;
import fun.mysticlands.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller // Помечаем класс как контроллер для веб-страниц
public class WebPageController {

    private final TaskService service;

    public WebPageController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/list") // Этот URL будет обрабатываться этим контроллером
    public String listTasks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Task> tasks = taskRepository.findAll()
                .stream()
                .filter(task -> Objects.equals(task.getOwner(), auth.getName()))
                .toList();
        String owner = auth.getName(); // Получаем имя аутентифицированного пользователя
        int taskCount = taskRepository.countTasksByOwner(owner);
        model.addAttribute("taskCount", taskCount);
        model.addAttribute("username", owner); // Передаем имя пользователя в представление
        model.addAttribute("tasks",tasks);
        model.addAttribute("name", "Index Html");
        return "task_list"; // Возвращает имя HTML-шаблона без расширения
    }
    @GetMapping("/") // Этот URL будет обрабатываться этим контроллером
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Получаем имя аутентифицированного пользователя
        model.addAttribute("username", username); // Передаем имя пользователя в представление
        model.addAttribute("name", "Index Html");
        return "index"; // Возвращает имя HTML-шаблона без расширения
    }
    @GetMapping("/add") // Этот URL будет обрабатываться этим контроллером
    public String addTask(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Получаем имя аутентифицированного пользователя
        List<User> users = userRepository.findAll().stream().filter(user -> Objects.equals(user.getRoles(), "ROLE_ADMIN")).toList();
        model.addAttribute("users", users);
        model.addAttribute("username", username); // Передаем имя пользователя в представление
        return "task-add"; // Возвращает имя HTML-шаблона без расширения
    }
    @PostMapping("/add") // Этот URL будет обрабатываться этим контроллером
    public String addTaskButton(@RequestParam String Name,@RequestParam String description,@RequestParam long userId, Model model) {
        String nameOwner=  userRepository.getReferenceById(userId).getName();
        Task task = new Task(Name,description,userId,nameOwner);
        taskRepository.save(task);
        return "redirect:/"; // Возвращает имя HTML-шаблона без расширения
    }
    @PostMapping("/done") // Этот URL будет обрабатываться этим контроллером
    public String TaskEnable(@RequestParam long taskId,@RequestParam(required = false) boolean completed, Model model) {
        Task task = taskRepository.getReferenceById(taskId);
        task.setDone(completed);
        //taskRepository.deleteById(taskId);
        taskRepository.save(task);

        return "redirect:/list"; // Возвращает имя HTML-шаблона без расширения
    }
    @PostMapping("/deleteko") // Этот URL будет обрабатываться этим контроллером
    public String deletTask(@RequestParam long taskId, Model model) {
        model.addAttribute("taskId", taskId);
        return "deleteTask"; // Возвращает имя HTML-шаблона без расширения
    }
    @PostMapping("/delete") // Этот URL будет обрабатываться этим контроллером
    public String TaskEnable(@RequestParam long taskId, Model model) {
        taskRepository.deleteById(taskId);
        return "redirect:/"; // Возвращает имя HTML-шаблона без расширения
    }



    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
}
