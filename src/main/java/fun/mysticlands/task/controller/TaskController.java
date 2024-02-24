package fun.mysticlands.task.controller;

import fun.mysticlands.task.model.Task;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import fun.mysticlands.task.services.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private TaskService service;
    @GetMapping
    public List<Task> AllTask(){
        return service.getAllTasks();
    }
    @PostMapping("add")
    public Task addTask(
            @RequestParam boolean done,
            @RequestParam String name,
            @RequestParam String description
    ) {
        Task task = new Task();
        task.setDone(done);
        task.setName(name);
        task.setDescription(description);

        return service.createTask(task);
    }
    @GetMapping("/list")
    public String listTasks(Model model) {
        List<Task> tasks = service.getAllTasks();
        model.addAttribute("index", tasks);
        return "index"; // это название HTML-шаблона без расширения
    }
}
