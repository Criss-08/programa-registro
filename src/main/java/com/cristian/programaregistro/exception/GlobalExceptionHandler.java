package com.cristian.programaregistro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresDeValidacion(
        MethodArgumentNotValidException ex){

        Map<String, String> detalles = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            detalles.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("error","Validacion");
        respuesta.put("mensaje", "Los datos enviados no son validos");
        respuesta.put("detalles", detalles);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);


    }





}
