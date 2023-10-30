//validaçao se o usuario ja existe

package dev.marcelomelo.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.marcelomelo.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //para toda class que queremos que o spring controle a class
public class FilterTaskAuth extends OncePerRequestFilter {

  //para validar o usuario
  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    
    //validar usuario de estar na rota de tasks, para depois validar as credenciais

    var serveletPath = request.getServletPath();

    if(serveletPath.startsWith("/tasks/")){
      //Pegar a autenticaçao (Usuario e senha)
      var authorization = request.getHeader("Authorization");
      
      //Identificar a codificaçao da autenticaçao (base64)
      var authEncoded = authorization.substring("Basic".length()).trim();
      //exemplo: "Basic bGVhbzoxMjM="
        /**
        * substring => retira caracteres string
        * substring + lenght => ele retira o total de string
        * .trim() => Retira todos os espaços em branco
      */
  
      //decoder (pegar os caracteres separados)
      byte[] authDecode =  Base64.getDecoder().decode(authEncoded);
      //resultado = "[B@24665cb6"
  
      var authString = new String(authDecode);
      //resultado = "leao:123"
  
      //separando o usuario da senha dentro do array ["tricolor", "123"]
      String[] credentials = authString.split(":");
      String username = credentials[0];
      String password = credentials[1];
      // resultado (cada credencial, usuario e senha estao emlinhas diferentes) 
  
      //Validar usuario
      var user = this.userRepository.findByUsername(username);
      if(user == null){
        response.sendError(401);
      } else{
        //Validar senha
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if(passwordVerify.verified){
          //Segue viagem
          request.setAttribute("idUser", user.getId()); //pegar o usuario cadastrado na autenticaçao
          filterChain.doFilter(request, response);
        }else{
          response.sendError(401);
        }
        // System.out.println("Authorization");
        // System.out.println(username);
        // System.out.println(password);
      }
    } else{
      filterChain.doFilter(request, response);

    }
  }
}
