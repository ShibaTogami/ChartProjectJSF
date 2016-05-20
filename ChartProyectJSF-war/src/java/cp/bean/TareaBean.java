/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.ProyectoFacade;
import cp.ejb.TareaFacade;
import cp.entity.Proyecto;
import cp.entity.Tarea;
import cp.entity.TareaPK;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
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
    @EJB
    private ProyectoFacade proyectoFacade;
    
    protected Tarea tarea;
    protected Proyecto proyecto;

    public Proyecto getProyecto() {
        return proyecto;
    }

    /*
     * Creates a new instance of TareaBean
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public TareaBean() {
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
    
    public String doEncontrarTarea(BigInteger idTarea, BigInteger idProyecto){
        TareaPK tareaPK = new TareaPK(idTarea, idProyecto);
        tarea = this.tareaFacade.find(tareaPK);
        BigDecimal idProy = new BigDecimal(idProyecto.toString());
        proyecto = this.proyectoFacade.findByIdProyecto(idProy);
        return "tarea";
    }
    
    public String getNombreTarea(){
        return tarea.getNombre();
    }
    
    public String doEditar(){
        return "nuevaTarea";
    } 
    
    public String doBorrar(){
        proyecto.getTareaCollection().remove(tarea);
        this.proyectoFacade.edit(proyecto);
        this.tareaFacade.remove(tarea);
        return "proyecto";
    }
    
    public String doGuardar(){
        if(tarea.getTareaPK()!=null){
            this.tareaFacade.edit(tarea);
        }else{
            //Falta crear una PK de la tarea
            this.tareaFacade.create(tarea);
            this.proyecto.getTareaCollection().add(tarea);
            this.proyectoFacade.edit(proyecto);
        }
        return "tarea";
    }
}
