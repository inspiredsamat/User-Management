package kz.inspiredsamat.spring.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kz.inspiredsamat.spring.model.Status;
import kz.inspiredsamat.spring.model.User;
import kz.inspiredsamat.spring.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User userToSave = new User(newUser.getName(), newUser.getPassword(), newUser.getEmail(), LocalDateTime.now());
        userRepository.save(userToSave);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateLoginTime() {
        String email = getEmailOfLoggedInUser();
        User loggedInUser = userRepository.findUserByEmail(email);
        loggedInUser.setLastLoginTime(LocalDateTime.now());
        userRepository.save(loggedInUser);
    }

    @Override
    public String delete(Long id) {
        User userToDelete = userRepository.findById(id).get();
        User loggedInUser = userRepository.findUserByEmail(getEmailOfLoggedInUser());
        userRepository.delete(userToDelete);

        if (userToDelete.equals(loggedInUser))
            return "redirect:/login";

        return "redirect:/itransition/users";
    }

    @Override
    public String block(Long id) {
        User userToBlock = userRepository.findById(id).get();
        if (userToBlock.getStatus() == Status.BLOCKED)
            return "redirect:/itransition/users";

        String email = getEmailOfLoggedInUser();

        userToBlock.setStatus(Status.BLOCKED);
        userRepository.save(userToBlock);

        if (userRepository.findUserByEmail(email).equals(userToBlock))
            return "redirect:/login";
        return "redirect:/itransition/users";
    }

    @Override
    public void unblock(Long id) {
        User userToUnblock = userRepository.findById(id).get();
        if (userToUnblock.getStatus() == Status.ACTIVE)
            return;

        userToUnblock.setStatus(Status.ACTIVE);
        userRepository.save(userToUnblock);
    }

    private String getEmailOfLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return email;
    }
}