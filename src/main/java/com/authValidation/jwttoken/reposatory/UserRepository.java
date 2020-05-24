package com.authValidation.jwttoken.reposatory;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.authValidation.jwttoken.model.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
