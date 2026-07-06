package com.cristian.programaregistro.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity // Clase persistida como tabla SQL
public class EstadoTrabajo {
    @Id //Marca la PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Automatiza la generacion de ID´s en SQL
    private Long id;
    //Long = null(Objeto)

    @NotBlank(message = "El nombre del estado es oblicagotio")
    private String nombre;

    //Constructor
    public EstadoTrabajo(){

    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

}
