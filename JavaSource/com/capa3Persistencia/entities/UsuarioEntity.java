package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
//usuario
@Table(name = "UsuarioEntity")
public class UsuarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@SequenceGenerator(name = "SEQUENCE2", sequenceName = "SEQUENCE2", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE2")
	private Long id;
	@Column(unique = true)
	private String nickname;
	private String nombre;
	private String apellido;
	private String email;
	private String clave;
	private String rol;
	private String titulo;
	private String cedula;
	private String horas;
	private Boolean borrado;

	
	
	
	public UsuarioEntity(String nickname, String nombre, String apellido, String email, String clave, String rol,
			String titulo, String cedula, String horas, Boolean borrado) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.clave = clave;
		this.rol = rol;
		this.titulo = titulo;
		this.cedula = cedula;
		this.horas = horas;
		this.borrado = borrado;
	}



	public UsuarioEntity(Long id, String nickname, String nombre, String apellido, String email, String clave,
			String rol, String titulo, String cedula, String horas, Boolean borrado) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.clave = clave;
		this.rol = rol;
		this.titulo = titulo;
		this.cedula = cedula;
		this.horas = horas;
		this.borrado = borrado;
	}



	public Long getId() {
		return id;

	}
	
	

	public Boolean getBorrado() {
		return borrado;
	}



	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}



	public UsuarioEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioEntity(String nickname, String nombre, String apellido, String email, String clave, String rol,
			String titulo, String cedula, String horas) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.clave = clave;
		this.rol = rol;
		this.titulo = titulo;
		this.cedula = cedula;
		this.horas = horas;

	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getrol() {
		return rol;
	}

	public void setrol(String rol) {
		this.rol = rol;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

}
