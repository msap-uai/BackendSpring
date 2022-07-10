package com.porfolio.MS.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profiles")
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facebook;
    private String github;
    private String codepen;
    private String instagram;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "info_personal")
    private String infoPersonal;

    private String experienciaTitulo1;
    private String experienciaTexo1;
    private String experienciaTitulo2;
    private String experienciaTexo2;
    private String experienciaTitulo3;
    private String experienciaTexo3;

    private String educacionTitulo1;
    private String educacionTexo1;
    private String educacionTitulo2;
    private String educacionTexo2;
    private String educacionTitulo3;
    private String educacionTexo3;

    private String proyectosTitulo1;
    private String proyectosTexo1;
    private String proyectosTitulo2;
    private String proyectosTexo2;
    private String proyectosTitulo3;
    private String proyectosTexo3;


    private Integer css;
    private Integer html;
    private Integer java;
    private Integer python;
    private Integer comunicacion;
    private Integer relaciones;



    @OneToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
