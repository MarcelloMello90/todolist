package dev.marcelomelo.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
  //listar as tasks somente do usuario solicitante
  List<TaskModel> findByIdUser(UUID idUser);
}
