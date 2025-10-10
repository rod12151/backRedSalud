package redSalud.consolidado.redHuamanga.Security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
          MethodArgumentNotValidException ex
  ) {
      String cause = (ex.getCause() != null) ? ex.getCause().toString() : "Sin causa";
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
      errors.put("cause", cause);
    });

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Error en la validación de los campos")
            .details(errors)
            .build();

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .error("Authentication Failed")
            .message("Credenciales inválidas")
            .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("User Not Found")
            .message(ex.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(UsuarioNoExiste.class)
  public ResponseEntity<ErrorResponse> UsuarioNoExiste(RuntimeException ex) {
      Map<String, String> details = new HashMap<>();
      details.put("message", ex.getMessage());
      details.put("timestamp", LocalDateTime.now().toString());
      if (ex.getCause() != null) {
          details.put("cause", ex.getCause().toString());
      } else {
          details.put("cause", "No tienes Autorizacion");
      }
    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .error("Bad Request")
            .message(ex.getMessage())
            .details(details)

            .build();
     log.error("e: ", ex);

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
      Map<String, String> details = new HashMap<>();
      details.put("message", ex.getMessage());
      details.put("timestamp", LocalDateTime.now().toString());
      details.put("",ex.getCause().toString());
    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message("Ha ocurrido un error interno en el servidor")
            .details(details)
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
    @ExceptionHandler(UsuarioDuplicado.class)
    public ResponseEntity<ErrorResponse> UsuarioDuplicado(Exception ex) {
        Map<String, String> details = new HashMap<>();
        details.put("message", ex.getMessage());
        details.put("timestamp", LocalDateTime.now().toString());
        if (ex.getCause() != null) {
            details.put("cause", ex.getCause().toString());
        } else {
            details.put("cause", "usuario duplicado");
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("usuario duplicado")
                .message("el usuario ya existe")
                .details(details)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidTokenFormatException.class)
    public ResponseEntity<ErrorResponse> ErrorDeToken(InvalidTokenFormatException ex) {
      Map<String, String> details = new HashMap<>();
      details.put("message", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("sin credenciales")
                .message(ex.getMessage())
                .details(details)

                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
