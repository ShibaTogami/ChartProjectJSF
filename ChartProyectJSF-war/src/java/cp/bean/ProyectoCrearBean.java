/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.ProyectoFacade;
import cp.entity.Proyecto;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author pablo
 */
@ManagedBean
@RequestScoped
public class ProyectoCrearBean {

    @EJB
    private ProyectoFacade proyectoFacade;
    
    Proyecto proyecto;
    /**
     * Creates a new instance of ProyectoCrearBean
     */
    public ProyectoCrearBean() {
    }
    
    
    @PostConstruct
    public void init() {
        proyecto = new Proyecto();
    }
    
    public String doCrear() {
        proyectoFacade.create(proyecto);
        return "principal";
    }
    
    
    
}
