package com.suresh.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suresh.bean.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

}
