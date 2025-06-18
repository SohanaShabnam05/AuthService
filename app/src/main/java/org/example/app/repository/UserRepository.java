package org.example.app.repository;

import org.example.app.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfom, Long>
{
    public UserInfo findByUsername(String username);
}
