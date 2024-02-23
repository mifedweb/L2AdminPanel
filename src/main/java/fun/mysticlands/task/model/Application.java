package fun.mysticlands.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Application {

    private long id;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
}
