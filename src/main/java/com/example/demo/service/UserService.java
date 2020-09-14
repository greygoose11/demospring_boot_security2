package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void save(User user) {
        Set<Role> roles = user.getRoles();
        if(roles==null){
            roles= new HashSet<>();
            roles.add(roleRepository.findById(1L).get());
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    @Transactional
    public List<User> listAll() {
        return userRepository.findAll();
    }
    @Transactional
    public User get(Long id) {
        return userRepository.findById(id).get();
    }
    @Transactional
    public void delete(Long id) {
        System.out.println("удаление юзера "+id);
        userRepository.deleteById(id);
    }


}
