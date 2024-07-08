package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.User;
import com.dish_dash.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepository implements UserRepository {
    @Autowired
    private SpringDataUserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
