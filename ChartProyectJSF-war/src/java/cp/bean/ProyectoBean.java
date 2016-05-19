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
import javax.faces.bean.ManagedBean;

/**
 *
 * @author shiba
 */
@ManagedBean
@SessionScoped
public class ProyectoBean implements Serializable {

    @EJB
    private ProyectoFacade proyectoFacade;
    
    protected Proyecto proyectoSeleccionado;

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
     
}
