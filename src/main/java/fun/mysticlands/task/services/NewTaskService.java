package fun.mysticlands.task.services;

import fun.mysticlands.task.model.Task;
import fun.mysticlands.task.model.User;
import fun.mysticlands.task.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor

public class NewTaskService implements TaskService{
    private final TaskRepository taskRepository;

    public long countTaskByIdOwner(long id_owner) {

        return taskRepository.findAll().stream().filter(task -> task.getId_owner() == id_owner).count();
    }
    public long countTaskByIdOwnerAndIsNoDone(long id_owner){
        return taskRepository.findAll().stream().filter(task -> task.getId_owner() == id_owner && task.isDone()).count();
    }


    @Override
    public Task createTask(Task task) {
        return null;
    }

    @Override
    public Task updateTask(long id, Task task) {
        return null;
    }

    @Override
    public void deleteTask(long id) {

    }

    @Override
    public Task getTaskById(long id) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public int getCountTaskByNameOwner(String name_owner) {
        return 0;
    }

    @Override
    public int countTasksInProgressByUser(User user) {
        return 0;
    }

    @Override
    public boolean existsByIdAndDone(Long id, boolean done) {
        return false;
    }
}
