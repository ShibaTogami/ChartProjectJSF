/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.ComentarioFacade;
import cp.ejb.ProyectoFacade;
import cp.ejb.UsuarioFacade;
import cp.entity.Comentario;
import cp.entity.Proyecto;
import cp.entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author shiba
 */
@Named(value = "proyectoBean")
@SessionScoped
public class ProyectoBean implements Serializable {
    @ManagedProperty(value="#{usuarioBean}")

    private UsuarioBean usuarioB;
    @EJB
    private ProyectoFacade proyectoFacade;
    private UsuarioFacade usuarioFacade;
    private ComentarioFacade comentarioFacade;
    
    protected Proyecto proyectoSeleccionado;
    protected String usuarioBuscar;
    protected List<Usuario> usuariosEncontrados;
    protected List<Usuario> seleccion;
    protected String comentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<Usuario> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(List<Usuario> seleccion) {
        this.seleccion = seleccion;
    }

    public List<Usuario> getUsuariosEncontrados() {
        return usuariosEncontrados;
    }

    public void setUsuariosEncontrados(List<Usuario> usuariosEncontrados) {
        this.usuariosEncontrados = usuariosEncontrados;
    }

    public String getUsuarioBuscar() {
        return usuarioBuscar;
    }

    

    public void setUsuarioBuscar(String usuarioBuscar) {
        this.usuarioBuscar = usuarioBuscar;
    }
    
    
    /**
     * Creates a new instance of ProyectoBean
     */
    
    public ProyectoBean() {
    }

    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(BigDecimal idProyecto) {
        this.proyectoSeleccionado = proyectoFacade.findByIdProyecto(idProyecto);
    }
     
    public String buscarUsuarios(){
        setUsuariosEncontrados(usuarioFacade.getUsuarioPorNicknameParecido(usuarioBuscar));
        if(usuariosEncontrados == null){
            return "error";
        }
        else{
        return "busqueda";
        }
    }
    public String doActualizar(){
        proyectoFacade.edit(proyectoSeleccionado);
        Comentario comment = new Comentario();
        comment.setTexto("el usuario " + usuarioB.usuario.getNickname()+ " actualizó el proyecto");
        comment.setIdProyecto2(proyectoSeleccionado);
        comment.setNickname(usuarioB.usuario);
        comentarioFacade.create(comment);
        proyectoSeleccionado.getComentarioCollection1().add(comment);
        return "proyecto";
        
    }
    
    public String doComentar(){
        Comentario comment = new Comentario();
        comment.setTexto(comentario);
        comentario = "";
        comment.setIdProyecto2(proyectoSeleccionado);
        comment.setNickname(usuarioB.usuario);
        comentarioFacade.create(comment);
        proyectoSeleccionado.getComentarioCollection().add(comment);
        return "proyecto";
    }
    
    public String doEliminar(){
        List<Usuario> usuarios = (List<Usuario>) proyectoSeleccionado.getUsuarioCollection();
        for(Usuario u : usuarios){
            List<Proyecto> proyectos = (List<Proyecto>) u.getProyectoCollection();
            for(int i = 0; i < proyectos.size(); i++){
                if(proyectos.get(i).equals(proyectoSeleccionado)){
                    proyectos.remove(i);
                    break;
                }
            }
        }
        proyectoFacade.remove(proyectoSeleccionado);
        return "principal";
    }
    
    public String doTerminar(){
        proyectoSeleccionado.setEstado("FINALIZADO");
        Comentario comment = new Comentario();
        comment.setTexto("el usuario " + usuarioB.usuario.getNickname()+ " finalizó el proyecto");
        comment.setIdProyecto2(proyectoSeleccionado);
        comment.setNickname(usuarioB.usuario);
        comentarioFacade.create(comment);
        proyectoSeleccionado.getComentarioCollection1().add(comment);
        proyectoFacade.edit(proyectoSeleccionado);
        return "principal";
    }
    
    public boolean doRenderizar(){
        boolean res = true;
        if(proyectoSeleccionado.getLider().equals(usuarioB.usuario) || proyectoSeleccionado.getEstado().equals("FINALIZADO")){
            res = false;
        }
        return res;
    }
    
    public String añadirSeleccion(Usuario usuario){
        seleccion.add(usuario);
        return "busqueda";
    }
    
    public String actualizar(){
        for(Usuario usuario: seleccion){
            proyectoSeleccionado.getUsuarioCollection().add(usuario);
            usuario.getProyectoCollection().add(proyectoSeleccionado);
        }
        Comentario comment = new Comentario();
        comment.setTexto("el usuario " + usuarioB.usuario.getNickname()+ " actualizó el proyecto");
        comment.setIdProyecto2(proyectoSeleccionado);
        comment.setNickname(usuarioB.usuario);
        comentarioFacade.create(comment);
        proyectoSeleccionado.getComentarioCollection1().add(comment);
        proyectoFacade.edit(proyectoSeleccionado);
        
        return "proyecto";
    }
 
    public String doMostrar(BigDecimal idProyecto) {
        this.setProyectoSeleccionado(idProyecto);
        return "proyecto";
    }

    
}