package ru.gb.timesheet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gb.timesheet.model.Person;
import ru.gb.timesheet.model.Role;
import ru.gb.timesheet.repository.PersonRepository;
import ru.gb.timesheet.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String personName) throws UsernameNotFoundException {
        Person person = personRepository
                .findByName(personName)
                .orElseThrow(() -> new UsernameNotFoundException("пользователь не найден" + personName));
//        List<Role> userRoles  = person.getRoles().stream().toList();
        List<SimpleGrantedAuthority> rolesSG = person.getRoles().stream()
                                    .map(u -> new SimpleGrantedAuthority(u.getName())).toList();
        return new User(person.getName(), person.getPassword(), rolesSG);
    }
}
