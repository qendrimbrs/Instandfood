package com.example.AppDish.repository;

import com.example.AppDish.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByUsername(String username);
}