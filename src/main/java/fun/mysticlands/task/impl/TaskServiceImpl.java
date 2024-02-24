package fun.mysticlands.task.impl;

import fun.mysticlands.task.model.Task;
import fun.mysticlands.task.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import fun.mysticlands.task.repository.TaskRepository;
import fun.mysticlands.task.services.TaskService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    @Override
    public Task createTask(Task task) {
        return repository.save(task);
    }

    @Override
    public int getCountTaskByNameOwner(String name_owner){
        int count = 0;
        count= (int)repository.findAll().stream()
                .filter(task -> task.getOwner().equals(name_owner) && !task.isDone())
                .count();
        return count;
    }

    public long countTaskByIdOwner(long id_owner) {

        return repository.findAll().stream().filter(task -> task.getId_owner() == id_owner).count();
    }
    public long countTaskByIdOwnerAndIsNoDone(long id_owner){
        return repository.findAll().stream().filter(task -> task.getId_owner() == id_owner && task.isDone()).count();
    }
    @Override
    public int countTasksInProgressByUser(User user) {
        return repository.countTasksByOwner(user.getName());
    }

    @Override
    public Task updateTask(long id, Task task) {
        repository.deleteById(id);
        repository.save(task);
        return task;
    }


    @Override
    public void deleteTask(long id) {
        repository.deleteById(id);
    }

    @Override
    public Task getTaskById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }


    @Override
    public boolean existsByIdAndDone(Long id, boolean done){
        Task task = repository.getReferenceById(id);
        if(task != null){
            task.setDone(done);
            return true;
        }else{
            return false;
        }
    }
}
