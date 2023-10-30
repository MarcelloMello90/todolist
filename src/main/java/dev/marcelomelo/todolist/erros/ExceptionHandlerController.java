//tratar erros do tipo HttpMessageNotReadableException
package dev.marcelomelo.todolist.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
  
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadableExcpetion(HttpMessageNotReadableException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    
    // O getMessage retornara a mensagem criada no TaskModel => throw new Exception("O campo title deve conter no maximo 50 caracteres");
  }
}
