package com.capa1presentacion;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;

import com.capa1Clases.Peso;
import com.capa1Clases.Usuario;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;

import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;


@Named(value = "gestionUsuario") // JEE8
@SessionScoped // JEE8
public class GestionUsuario implements Serializable {
	
    // Clave secreta para firmar el token JWT
	private static final String SECRET_KEY = "asfadsfgadkgfsdkgflsdjmgkdfsngkldfngmkdlfngdklfgndfkngdgkldfnnld";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionUsuarioService persistenciaBean;

	GestionUsuarioService gestionUsuarioService;
	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = false;
	private boolean modoNickname = false;

	private Usuario usuarioSeleccionado;

	public GestionUsuario() {
		super();
	}
	
	

    @PostConstruct
    public void init() {
        usuarioSeleccionado = new Usuario();
    }

	// Pasar a modo

    public String validarUsuario() {
        String rol = validarCredenciales(usuarioSeleccionado.getNickname(), usuarioSeleccionado.getClave());
   

        if (rol != null) {
            // Generar un token JWT si las credenciales son válidas
            String jwtToken = generarTokenJWT(usuarioSeleccionado.getNickname(), rol);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("jwtToken", jwtToken);
            
            switch (rol) {
                case "ADMINISTRADOR":
                    return "Pantalla_Principal_Administrador_copia";
                case "ENCARGADO":
                    return "Pantalla_principal_encargado";
                case "PERSONAL":
                    return "Pantalla_principal_personal";
                default:
                    return "nobienvenido";
            }
        } else {
            // Mostrar mensaje de error al usuario
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de autenticación", "Usuario o contraseña incorrectos"));
            return "";
        }
        
        }
   

    private String generarTokenJWT(String nickname, String rol) {

        // Fecha de expiración del token (1 hora en este ejemplo)
        long expirationTime = 3600000; // 1 hora en milisegundos
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        
        // Generar el token JWT
        String jwtToken = Jwts.builder()
            .setSubject(nickname)
            .claim("rol", rol)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
            .compact();
        
        // Aquí podrías devolver el token generado al cliente o almacenarlo para su uso posterior
        return jwtToken;
    }
    
    
    private String validarCredenciales(String nickname, String clave) {
        // Lógica para validar las credenciales en la base de datos
        Usuario usuarioBD = persistenciaBean.obtenerUsuarioPorCredenciales(nickname, clave);

        if (usuarioBD != null) {
            // Usuario encontrado, devuelve el rol
            return usuarioBD.getRol();
        } else {
            // Usuario no encontrado
            return null;
        }
    }

	public void preRenderViewListener() {

		if (id != null) {
			usuarioSeleccionado = persistenciaBean.buscarUsuario(id);
		} else {
			usuarioSeleccionado = new Usuario();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion = false;
			modoNickname = false;
		} else if (modalidad.contentEquals("update")) {
			modoEdicion = true;
			modoNickname = false;
		} else if (modalidad.contentEquals("insert")) {
			modoEdicion = true;
			modoNickname = true;
		} else {
			modoEdicion = false;
			modalidad = "view";
			modoNickname = false;

		}
	}

	public String salvarCambios() {
		if (usuarioSeleccionado.getId() == null) {
		if (usuarioSeleccionado.getNickname().length() < 8) {
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre de usuario debe tener al menos 8 caracteres.", "");
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            System.out.print("El nombre de usuario debe tener al menos 8 caracteres.");
	            return null; 
	        }
		// Validación de longitud de contraseña
        if (usuarioSeleccionado.getClave().length() < 8) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña debe tener al menos 8 caracteres.", "");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null; 
        }
        
        // Validación de caracteres alfanuméricos
        if (!usuarioSeleccionado.getClave().matches(".*[a-zA-Z].*") || !usuarioSeleccionado.getClave().matches(".*\\d.*")) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña debe contener letras y números.", "");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null; 
        }
        if (!usuarioSeleccionado.getEmail().toLowerCase().endsWith("@gmail.com") && 
                !usuarioSeleccionado.getEmail().toLowerCase().endsWith("@outlook.com")) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo electrónico debe ser de Gmail o Outlook.", "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return null; 
            }
        if (usuarioSeleccionado.getCedula() == null || !usuarioSeleccionado.getCedula().matches("\\d{8}")) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La cédula debe contener exactamente 8 números.", "");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null; 
        }
        int horas;
        try {
            horas = Integer.parseInt(usuarioSeleccionado.getHoras());
            if (horas < 1 || horas > 12) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las horas deben ser un número entre 1 y 12.", "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return null; // Retorna null para indicar que no se ha podido salvar los cambios
            }
        } catch (NumberFormatException e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las horas deben ser un número entero válido.", "");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null; // Retorna null para indicar que no se ha podido salvar los cambios
        }
        
        
			Usuario usuarioNuevo;
			try {
				usuarioNuevo = (Usuario) persistenciaBean.agregarUsuario(usuarioSeleccionado);
				this.id = usuarioNuevo.getId();

				usuarioSeleccionado = new Usuario();
				
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo Usuario",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(rootException);
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}

		} else if (modalidad.equals("update")) {

			try {
				persistenciaBean.actualizarUsuario(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el Usuario.", ""));

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
		}
		return "";
	}
	public String logOut() {
		return "login";
	}

	public String eliminarUsuario(Long id2) {
		Usuario usuarioParaBorrar;
		try {
			usuarioParaBorrar = (Usuario) persistenciaBean.buscarUsuario(id2);
			usuarioParaBorrar.setBorrado(true);
			persistenciaBean.borrarUsuario(usuarioParaBorrar);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha eliminado un usuario", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "listaUsuarios";

		} catch (PersistenciaException e) {

			Throwable rootException = ExceptionsTools.getCause(e);
			String msg1 = e.getMessage();
			String msg2 = ExceptionsTools.formatedMsg(rootException);
			// mensaje de actualizacion correcta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			this.modalidad = "update";

			e.printStackTrace();
		}

		return "listaUsuarios";
	}

	public String reset() {
		this.modalidad = "insert";
		usuarioSeleccionado = new Usuario();
		id = null;
		return "AltaUsuario_copia";
	}

	public GestionUsuarioService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionUsuarioService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public GestionUsuarioService getGestionUsuarioService() {
		return gestionUsuarioService;
	}

	public void setGestionUsuarioService(GestionUsuarioService gestionUsuarioService) {
		this.gestionUsuarioService = gestionUsuarioService;
	}

	public boolean isModoNickname() {
		return modoNickname;
	}

	public void setModoNickname(boolean modoNickname) {
		this.modoNickname = modoNickname;
	}

}
