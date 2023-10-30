package dev.marcelomelo.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
   * Estrutura da tabela de tarefas:
   * ID
   * Usuario (ID_USUARIO)
   * Descriçao
   * Titulo
   * Data de Inicio
   * Data de termino
   * Prioridade
   */

@Data
@Entity(name = "tb_tasks")

public class TaskModel {

  @Id
  @GeneratedValue(generator = "UUID")//Gerador de id automatico
  private UUID id;
  private String description;

  @Column(length = 50)//limitador de caracteres para o titulo
  private String title;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private String priority;
  
  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createdAt;

  //verificar se o title tem 50 caracteres, conforme setado.
  public void setTitle(String title) throws Exception{
    // throws - ele trasnfere a responsabilidade de tratar a exceçao para quem chamar a funçao
    if(title.length()>50){
      throw new Exception("O campo title deve conter no maximo 50 caracteres");
    }
    this.title = title;
  }
}
