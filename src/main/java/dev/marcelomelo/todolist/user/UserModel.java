//Arquivo destinado para ser o medelo de usuario
package dev.marcelomelo.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")//nomeando a tabela
public class UserModel {

  //inserindo a chave primaria no banco de dados
  @Id
  @GeneratedValue(generator = "UUID") //tipo de gerador da chave primaria
  private UUID id;

  @Column(unique = true) //deixa a coluna abaixo, username, com valor unico. Nao aceita criar com o mesmo nome
  private String username;
  private String name;
  private String password;

  //registrar o dia em que o banco de dados foi criado
  @CreationTimestamp
  private LocalDateTime createdAt;
}
//getters setters