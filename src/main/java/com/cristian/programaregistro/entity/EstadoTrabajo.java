
package com.cristian.programaregistro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity //Indica a Hibernate converti en tabla SQL
public class EstadoTrabajo {
    @Id //Marca la PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Automatiza la generacion de ID´s en SQL
    private Long id;
    //Long = null(Objeto)

    private String nombre;


}
