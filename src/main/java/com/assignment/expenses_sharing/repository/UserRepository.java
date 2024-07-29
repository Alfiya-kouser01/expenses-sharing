package com.assignment.expenses_sharing.repository;

import com.assignment.expenses_sharing.entities.UserTB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTB, Integer> {
}
