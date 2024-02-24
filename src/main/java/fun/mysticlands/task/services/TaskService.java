package fun.mysticlands.task.services;

import fun.mysticlands.task.model.Task;
import fun.mysticlands.task.model.User;

import java.util.List;

public interface TaskService {
        Task createTask(Task task);
        Task updateTask(long id, Task task);
        void deleteTask(long id);
        Task getTaskById(long id);
        List<Task> getAllTasks();
        int getCountTaskByNameOwner(String name_owner);
        int countTasksInProgressByUser(User user);

    boolean existsByIdAndDone(Long id, boolean done);



}
