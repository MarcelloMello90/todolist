package dev.marcelomelo.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.marcelomelo.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  
  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
    System.out.println("Chegou no Controler");
    var idUser = request.getAttribute("idUser");
    taskModel.setIdUser((UUID) idUser);

    //validar se a data de cadastro é anterior ou depois da data atual
    var currentDate = LocalDateTime.now();
    //ex 10/11/2023 - Current
    //   10/10/2023 - startAt
    if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
      //validando se a data inicial ou data final da tarefa sao menores que a data atual
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio / data determino devem ser maior que a data atual");
    }

    //validar se a data de termino da tarefa é menor que a data de inicio
    if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
      //validando se a data inicial ou data final da tarefa sao menores que a data atual
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a data determino");
    };
    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @GetMapping("/")
  // Listar todas as atividades cadastradas pelo usuario
  public List<TaskModel> list (HttpServletRequest request){
    var idUser = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser((UUID) idUser);
    return tasks;
  }

  //Realiza o Update da tarefa
  //@PathVariable pega o id da tarefa
  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
    var task = this.taskRepository.findById(id).orElse(null);

    if(task == null){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa nao encontrada");
    }

    var idUser = request.getAttribute("idUser");

    if(!task.getIdUser().equals(idUser)){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario nao tem permissao para alterar essa tarefa");
    }

    Utils.copyNonNullProperties(taskModel, task);

    var taskUpdated = this.taskRepository.save(task);
    return ResponseEntity.ok().body(taskUpdated);
  }
 
}