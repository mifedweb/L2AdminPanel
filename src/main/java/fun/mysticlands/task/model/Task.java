package fun.mysticlands.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.extras.springsecurity6.auth.Authorization;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String owner;
    private LocalDateTime localDateTime;

    public Task(String name, String description, long id_owner,String name_owner) {
        this.name = name;
        this.description = description;
        this.id_owner = id_owner;
        this.owner = name_owner;
        this.localDateTime= LocalDateTime.now();

    }

    public Task() {

    }

    public String getDataFormat(){
        LocalDateTime now = localDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return now.format(formatter);
    }


    public void setDone(boolean done) {
        this.done = done;
    }

}
