package com.ngsoftware.leon.persistence.repos;

import com.ngsoftware.leon.persistence.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, String> {
}
