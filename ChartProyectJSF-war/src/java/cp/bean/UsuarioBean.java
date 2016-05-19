/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.UsuarioFacade;
import cp.entity.Usuario;
import java.io.Serializable;
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
    protected String usuarioIntroducido; //usuario introducido en la web de log in
    protected String passwordIntroducido; //pass introducido en la web de log in
    
    public UsuarioBean() {
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

    public String doLoguear()
    {
        String redireccion = null;
        Usuario user = usuarioFacade.find(this.usuarioIntroducido);
        if (passwordMalicioso()) //si hay caracteres maliciosos en el password
        {
            redireccion = "/index.xhtml";
        }
        else //comprobamos el pass con el del usuario recuperado de la BD
        {
            //si coincide
            if (user.getPassword().equals(this.passwordIntroducido))
            {
                this.usuario = user;
                redireccion = "/principal.xhtml";
            }
            else //si no coincide
            {
                redireccion = "/index.xhtml";
            }
            //vaciamos datos irrelevantes. Obtenibles desde usuario.
            this.usuarioIntroducido=null;
            this.passwordIntroducido =null;
        }
        return redireccion;
    }

    private boolean passwordMalicioso() {
        return true;
    }

    
}
