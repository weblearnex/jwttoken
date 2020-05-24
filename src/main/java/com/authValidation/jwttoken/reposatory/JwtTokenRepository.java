package com.authValidation.jwttoken.reposatory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.authValidation.jwttoken.model.entity.TokenDetails;

@Repository
public interface JwtTokenRepository extends CrudRepository<TokenDetails,String> {
}
