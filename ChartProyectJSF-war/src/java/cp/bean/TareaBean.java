/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.TareaFacade;
import cp.entity.Tarea;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rocio
 */
@ManagedBean
@SessionScoped
public class TareaBean {
    @EJB
    private TareaFacade tareaFacade;
    
    protected Tarea tarea;
    /*
     * Creates a new instance of TareaBean
     */
    public TareaBean() {
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
}
