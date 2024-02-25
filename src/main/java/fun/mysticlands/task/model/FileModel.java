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
import java.time.format.DateTimeFormatter;

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
    private FileType fileType; // Добавлено перечисление для типа файла

    public FileModel() {
        this.localDateTime = LocalDateTime.now();
    }
    public String getDataFormat(){
        LocalDateTime now = localDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return now.format(formatter);
    }
    // Enum для типа файла
    public enum FileType {
        SERVER_FILE("Server_File"),
        CLIENT_FILE("Client_File"),
        OTHER_FILE("Other_File");

        private final String displayName;

        FileType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}
