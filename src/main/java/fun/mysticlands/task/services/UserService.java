package fun.mysticlands.task.services;

import fun.mysticlands.task.config.MyUserDetails;
import fun.mysticlands.task.model.User;
import fun.mysticlands.task.repository.TaskRepository;
import fun.mysticlands.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
@Autowired
    private UserRepository repository;
@Autowired
private TaskRepository taskRepository;
@Autowired
private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findUserByName(username);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not Found"));
    }
    public void addUser(User user){

        repository.save(user);
    }
    public int getCountTaskByName(String name){
        return taskRepository.countTasksByOwner(name);
    }
    public String getNameById(long id){
        return repository.getReferenceById(id).getName();
    }
    public int countTasksForCurrentUser() {
        // Получаем аутентификацию из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, что пользователь аутентифицирован
        if (authentication != null && authentication.isAuthenticated()) {
            // Получаем имя текущего пользователя
            String owner = authentication.getName();

            // Используем имя пользователя для подсчета задач
            return taskRepository.countTasksByOwner(owner);
        }

        // Возвращаем 0, если пользователь не аутентифицирован или нет задач
        return 0;
    }
}
