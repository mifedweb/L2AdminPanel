package fun.mysticlands.task.impl;

import fun.mysticlands.task.repository.TaskRepository;
import fun.mysticlands.task.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl extends UserService {
    @Autowired
    private final TaskRepository taskRepository;

    @Override
    public int getCountTaskByName(String name){
        return taskRepository.countTasksByOwner(name);
    }
}
