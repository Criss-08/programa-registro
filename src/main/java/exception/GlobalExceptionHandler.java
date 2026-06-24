package exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresDeValidacion(
        MethodArgumentNotValidException ex){

        Map<String, String> detalles = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            detalles.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("error","validacion");
        respuesta.put("mensaje", "Los datos enviados no son validos");
        respuesta.put("detalle", detalles);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);


    }





}
