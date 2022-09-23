package com.moorabi.springboot.crudrestfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moorabi.springboot.crudrestfulwebservices.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
