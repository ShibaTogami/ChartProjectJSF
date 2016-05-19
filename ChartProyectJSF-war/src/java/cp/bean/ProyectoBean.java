/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.ProyectoFacade;
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

/**
 *
 * @author shiba
 */
@Named(value = "proyectoBean")
@SessionScoped
public class ProyectoBean implements Serializable {

    @EJB
    private ProyectoFacade proyectoFacade;
    
    protected Proyecto proyectoSeleccionado;
    protected String nombreProyecto;
    protected String descripcionProyecto;
    protected Usuario liderProyecto;
    protected List<Comentario> comentarioCollection;
    protected List<Comentario> comentarioCollection1;
    protected List<Usuario> participantesProyecto;
    protected Date fechaInicioProyecto;
    protected String estadoProyecto;
    protected BigDecimal idProyecto;

    /**
     * Creates a new instance of ProyectoBean
     */
    
    public ProyectoBean() {
    }

    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public Usuario getLiderProyecto() {
        return liderProyecto;
    }

    public void setLiderProyecto(Usuario liderProyecto) {
        this.liderProyecto = liderProyecto;
    }

    public List<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(List<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    public List<Comentario> getComentarioCollection1() {
        return comentarioCollection1;
    }

    public void setComentarioCollection1(List<Comentario> comentarioCollection1) {
        this.comentarioCollection1 = comentarioCollection1;
    }

    public List<Usuario> getParticipantesProyecto() {
        return participantesProyecto;
    }

    public void setParticipantesProyecto(List<Usuario> participantesProyecto) {
        this.participantesProyecto = participantesProyecto;
    }

    public Date getFechaInicioProyecto() {
        return fechaInicioProyecto;
    }

    public void setFechaInicioProyecto(Date fechaInicioProyecto) {
        this.fechaInicioProyecto = fechaInicioProyecto;
    }

    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public BigDecimal getIdProyecto() {
        return idProyecto;
    }
     
}
