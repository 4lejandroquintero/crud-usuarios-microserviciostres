package com.microrobot.task.persistence;

import com.microrobot.task.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUserId(Long assignedUserId);
}
