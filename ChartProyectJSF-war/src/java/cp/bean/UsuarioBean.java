/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.UsuarioFacade;
import cp.entity.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author shiba
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;

    /**
     * Creates a new instance of UsuarioBean
     */
    
    protected Usuario usuario; //usuario Logueado
    protected String usuarioIntroducido; //usuario introducido en la web de log in o registro
    protected String passwordIntroducido; //pass introducido en la web de log in o en registro
    protected String passwordIntroducido2; //pass introducido en la web de log in o registro
    protected String emailIntroducido; //email introducidor en registro
    protected String emailIntroducido2; //repeticion del email para controlar errores
    protected String preguntaSecretaIntroducida; //pregunta de seguridad para recuperar contraseña en registro
    protected String respuestaSecretaIntroducida; //respuesta para recuperar contraseña en registor
    protected String errorLogin; //contendra el texto del error por el cual el login puede fallar
    protected String errorRegistro; //contendrá el texto a mostrar en caso de error al registrarse
    public UsuarioBean() {
        errorLogin="";
        errorRegistro="";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioIntroducido() {
        return usuarioIntroducido;
    }

    public void setUsuarioIntroducido(String usuarioIntroducido) {
        this.usuarioIntroducido = usuarioIntroducido;
    }

    public String getPasswordIntroducido() {
        return passwordIntroducido;
    }

    public void setPasswordIntroducido(String passwordIntroducido) {
        this.passwordIntroducido = passwordIntroducido;
    }

    public String getPasswordIntroducido2() {
        return passwordIntroducido2;
    }

    public void setPasswordIntroducido2(String passwordIntroducido2) {
        this.passwordIntroducido2 = passwordIntroducido2;
    }

    public String getEmailIntroducido() {
        return emailIntroducido;
    }

    public void setEmailIntroducido(String emailIntroducido) {
        this.emailIntroducido = emailIntroducido;
    }

    public String getEmailIntroducido2() {
        return emailIntroducido2;
    }

    public void setEmailIntroducido2(String emailIntroducido2) {
        this.emailIntroducido2 = emailIntroducido2;
    }

    public String getPreguntaSecretaIntroducida() {
        return preguntaSecretaIntroducida;
    }

    public void setPreguntaSecretaIntroducida(String preguntaSecretaIntroducida) {
        this.preguntaSecretaIntroducida = preguntaSecretaIntroducida;
    }

    public String getRespuestaSecretaIntroducida() {
        return respuestaSecretaIntroducida;
    }

    public void setRespuestaSecretaIntroducida(String respuestaSecretaIntroducida) {
        this.respuestaSecretaIntroducida = respuestaSecretaIntroducida;
    }

    public String doLoguear()
    {
        String redireccion = null;
        Usuario user = usuarioFacade.getUsuarioPorNickname(this.usuarioIntroducido);
        if (passwordMalicioso()) //si hay caracteres maliciosos en el password
        {
            errorLogin = "Error: La contraseña contiene caracteres no permitidos";
            redireccion = "index.xhtml";
        }
        else //comprobamos el pass con el del usuario recuperado de la BD
        {
            if (user==null) //si no se ha encontrado al usuario.
            {
                errorLogin="El usuario Introducido no existe";
                redireccion = "index.xhtml";
            }
            else
            {
                //si el usuario existe y los passwords coinciden
                if (user.getPassword().equals(this.passwordIntroducido))
                {
                    this.usuario = user;
                    redireccion = "principal.xhtml";
                }
                else //si no coincide
                {
                    errorLogin = "Error: La contraseña es incorrecta";
                    redireccion = "index.xhtml";
                }
            }
            //vaciamos datos irrelevantes. Obtenibles desde usuario.
            this.usuarioIntroducido=null;
            this.passwordIntroducido =null;
        }
        return redireccion;
    }
    
    public String doRegistrar()
    {
        String salida="";
        if (usuarioFacade.getUsuarioPorNickname(usuarioIntroducido)==null) //si el usuario es nuevo
        {    
            if (!passwordMalicioso()) //si el password no es malicioso
            {
                if (comprobarPassword()) //y coincide
                {
                    if (comprobarEmail()) //comprobamos el email y si coincide
                    {
                        //registramos
                        usuario = new Usuario(usuarioIntroducido,passwordIntroducido,new Date(),emailIntroducido);
                        usuario.setPregunta(preguntaSecretaIntroducida);
                        usuario.setRespuesta(respuestaSecretaIntroducida);
                        usuario.setUltimaConexion(new Date());
                        usuarioFacade.create(usuario);
                        salida = "principal.xhtml";
                    }
                    else
                    {
                        //indicar error al usuario
                        errorRegistro="Error: El email Introducido no se ha repetido correctamente";
                        salida = "registro.xhtml";
                    }
                }
                else
                {
                    //indicar error al usuario
                    errorRegistro = "Error: Las constraseñas introducidas no coinciden";
                    salida = "registro.xhtml";
                }
            }
            else 
            {
                //indicar error al usuario
                errorRegistro = "Error: La contraseña introducida contiene caracteres no permitidos";
                salida = "registro.xhtml";
            }
        }
        else
        {
            errorRegistro = "Error: Ese usuario ya había sido registrado.";
            salida = "registro.xhtml";
        }
        return salida;
    }

    private boolean passwordMalicioso() { //metodo que comprueba la presencia de caracteres
        //maliciosos en el password introducido
        boolean salida = false; //empezamos dando por hecho que no los contiene
        for (char aux : passwordIntroducido.toCharArray()) {
            if (aux == '\'' || aux == '"' || aux == '+' || aux == '-' || aux == '*' || aux == '/' || aux == '%') {
                salida = true; //si se encuentra con algun caracter no permitido se actualiza la variable
            }
        }
        return salida;
    }

    private boolean comprobarPassword() {
        //metodo que comprueba que ambos passwords introducidos en el registro coinciden
        return passwordIntroducido.equals(passwordIntroducido2);
    }

    private boolean comprobarEmail() {
        return emailIntroducido.equals(emailIntroducido2);
    }

    public String getErrorLogin() {
        return errorLogin;
    }

    public void setErrorLogin(String errorLogin) {
        this.errorLogin = errorLogin;
    }

    public String getErrorRegistro() {
        return errorRegistro;
    }

    public void setErrorRegistro(String errorRegistro) {
        this.errorRegistro = errorRegistro;
    }

    
}
