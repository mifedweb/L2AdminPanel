package fun.mysticlands.task.impl;

import fun.mysticlands.task.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
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
