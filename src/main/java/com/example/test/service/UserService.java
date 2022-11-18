package com.example.test.service;

import java.util.List;
import java.util.Optional;
import java.util.LinkedList;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.dto.user.UserDto;
import com.example.test.exception.EmailNotDelivered;
import org.springframework.data.domain.Page;
import com.example.test.repository.UserRepo;

import javax.persistence.criteria.Predicate;

import com.example.test.exception.BadRequest;
import org.springframework.stereotype.Service;
import com.example.test.dto.user.UserFilterDto;
import com.example.test.dto.user.UserCreateDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@Service
public class UserService {
    private final UserRepo userRepo;

    private final AuthService authService;


    public UserService(UserRepo userRepo, AuthService authService) {
        this.userRepo = userRepo;
        this.authService = authService;
    }

    public UserDto create(UserCreateDto createDto) {
        User user = new User();
        user.setName(createDto.getName());
        user.setEmail(createDto.getEmail());
        user.setAge(createDto.getAge());
        user.setCreatedAt(LocalDateTime.now());
        userRepo.save(user);
        return convertToDto(user, new UserDto());
    }

    public UserDto get(Integer id) {
        return convertToDto(getEntity(id), new UserDto());
    }

    public User getEntity(Integer id) {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequest("User not found");
        }
        return optional.get();
    }

    public UserDto convertToDto(User user, UserDto dto) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        return dto;
    }

    public List<UserDto> filter(UserFilterDto dto) {
        String sortBy = ("createdAt");
        if (dto.getSortBy() != null) {
            sortBy = dto.getSortBy();
        }
        PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<Predicate> predicateList = new LinkedList<>();
        Specification<User> specification = ((root, query, criteriaBuilder) -> {
            if (dto.getName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
            }
            if (dto.getEmail() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("email"),
                        dto.getEmail()));
            }
            if (dto.getMinAge() != null && dto.getMaxAge() != null) {
                predicateList.add(criteriaBuilder.between(root.get("age"),
                        dto.getMinAge(), dto.getMaxAge()));
            }
            if (dto.getMinAge() != null && dto.getMaxAge() == null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("age"),
                        dto.getMinAge()));
            }
            if (dto.getMinAge() == null && dto.getMaxAge() != null) {
                predicateList.add(criteriaBuilder.lessThan(root.get("age"),
                        dto.getMaxAge()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.between(root.get("createdAt"),
                        dto.getMinCreatedDate(), dto.getMaxCreatedDate()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() == null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"),
                        dto.getMinCreatedDate()));
            }
            if (dto.getMinCreatedDate() == null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"),
                        dto.getMaxCreatedDate()));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
        Page<User> page = userRepo.findAll(specification, pageable);
        return page.stream().map(user -> convertToDto(user, new UserDto())).collect(Collectors.toList());
    }

    public UserDto update(Integer userId, UserCreateDto dto) {
        User user = getEntity(userId);
        if (dto.getEmail() != null) {
            if (!authService.sendMessageToEmail(user)) {
                throw new EmailNotDelivered("Email Delivered");
            }
        }
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setRole(Role.ROLE_ADMIN);
        user.setEmail(null);
        user.setStatus(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
        return convertToDto(user, new UserDto());
    }

    public List<UserDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userRepo.findAll(pageRequest);

        List<UserDto> userDtoList = new LinkedList<>();
        for (User u : userPage) {
            userDtoList.add(convertToDto(u, new UserDto()));
        }
        return userDtoList;
    }
    public boolean delete(Integer id) {
        User user = getEntity(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepo.save(user);
        return true;
    }

    public UserDto password(Integer id, UserDto dto) {
        return null;
    }

    public String block(Integer id) {
       User user = getEntity(id);
       user.setStatus(false);
       userRepo.save(user);
        return "User bocked !";
    }
}
