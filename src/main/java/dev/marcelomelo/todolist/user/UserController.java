package dev.marcelomelo.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadores:
 *  public
 *  private
 *  protected
 */

//Definindo que o UserController sera a camada principal relacionado ao usuario
@RestController
@RequestMapping("/users")
public class UserController {
  /**
   * String (texto)
   * Integer (int) numeros inteiros
   * Double (double) Numeros 0.0000
   * Float (float) numeros 0.000
   * char (A C)
   * Date (data)
   * void - quando o metodo nao tem retorno, apenas é executado
  */

  //Body - As informaçoes vem do body, assim utilizamos o @RequestBody
  
  @Autowired //gerenciar o ciclo de vida do repositorio, estanciar, etc.
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel){
    
    //customizando a mensagem caso o usuario tente cadastrar um username ja criado
    var user = this.userRepository.findByUsername(userModel.getUsername());

    if(user != null){
      //Mensagem de erro
      //Status code
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
    }

    var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

    userModel.setPassword(passwordHashred);

    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.OK).body(userCreated);
  }
}

//quando queremos exibir alguma mensagem no terminal, utilizamos o comando: System.out.println()
// System.out.println(UserModel.getUsername());