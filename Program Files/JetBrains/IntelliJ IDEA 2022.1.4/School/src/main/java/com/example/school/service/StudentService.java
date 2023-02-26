package com.example.school.service;

import com.example.school.dto.StudentDto;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDto create(StudentDto dto) {

        return null;
    }

}
