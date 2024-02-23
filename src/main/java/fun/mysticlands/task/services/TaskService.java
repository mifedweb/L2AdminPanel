package fun.mysticlands.task.services;

import fun.mysticlands.task.model.Task;

import java.util.List;

public interface TaskService {
        Task createTask(Task task);
        Task updateTask(long id, Task task);
        void deleteTask(long id);
        Task getTaskById(long id);
        List<Task> getAllTasks();

    boolean existsByIdAndDone(Long id, boolean done);
}
