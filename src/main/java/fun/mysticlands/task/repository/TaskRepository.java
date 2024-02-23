package fun.mysticlands.task.repository;

import fun.mysticlands.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    void deleteAll();


    void deleteById(Long id);
    Task save(Task task);
}
