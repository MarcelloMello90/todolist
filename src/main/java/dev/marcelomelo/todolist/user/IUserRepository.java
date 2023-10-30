//interface é como se fosse um modelo ou "contrato" que temos dentro da aplicaçao.
//ou seja, temos apenas as regras sem sua aplicaçao.
//Para implantar a interface, precisamos utilizar uma classe

package dev.marcelomelo.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
  UserModel findByUsername(String username); //libera a funcionalidade do findBy no arquivo "UserController"
}
