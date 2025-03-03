package com.ptit.event.services.user;

import com.ptit.event.configurations.JwtTokenUtil;
import com.ptit.event.entities.models.Role;
import com.ptit.event.entities.models.User;
import com.ptit.event.repositories.RoleRepository;
import com.ptit.event.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final RoleRepository roleRepository;
    @Override
    @Transactional
    public User findUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id = " + id));
    }

    @Override
    @Transactional
    public User findUserByJwt(String jwt) throws Exception {
        String email = jwtTokenUtil.getEmailFromToken(jwt);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    @Transactional
    public User updateRole(Long userId, List<Long> roleIds) throws Exception {
        User existingUser = findUserById(userId);
        Set<Role> roles = new HashSet<>();
        for(Long roleId : roleIds){
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role doesn't exist with id = "+ roleId));
            roles.add(role);
        }
        existingUser.setRoles(roles);
        return userRepository.save(existingUser);
    }


}
