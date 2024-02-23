package fun.mysticlands.task.services;

import fun.mysticlands.task.config.MyUserDetails;
import fun.mysticlands.task.model.User;
import fun.mysticlands.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getNameById(long id){
        return repository.getReferenceById(id).getName();
    }
}
