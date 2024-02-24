package fun.mysticlands.task.repository;

import fun.mysticlands.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    void deleteAll();
    int countTaskByName(String name);
    Task getTaskById(long id);
    void deleteById(Long id);
    Task save(Task task);
    int countTasksByOwner(String owner);
    int countTasksByOwnerAndDoneIsFalse(String owner);

}
