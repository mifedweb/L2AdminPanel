package fun.mysticlands.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Data
@Table(name = "FileModel")
public class FileModel {
    @Id
    @GeneratedValue
    private int id;
    private long id_owner;
    private String fileName;
    private String contentType;
    private byte[] data;
    private String FilePath;
    private long fileSize; // Добавлено поле для размера файла
    private LocalDateTime localDateTime;
    private String version;
    private String description;

    public FileModel() {
        this.localDateTime = LocalDateTime.now();
    }


}
