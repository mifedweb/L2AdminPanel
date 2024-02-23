package fun.mysticlands.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Data
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean done;
    private String name;
    private String description;
    private long id_owner;
    private String name_owner;

    public Task(String name, String description, long id_owner,String name_owner) {
        this.name = name;
        this.description = description;
        this.id_owner = id_owner;
        this.name_owner = name_owner;
    }

    public Task() {

    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
