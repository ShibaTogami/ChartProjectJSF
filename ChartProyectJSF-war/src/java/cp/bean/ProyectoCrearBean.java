/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.bean;

import cp.ejb.ProyectoFacade;
import cp.ejb.UsuarioFacade;
import cp.entity.Proyecto;
import cp.entity.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author pablo
 */
@ManagedBean
@SessionScoped
public class ProyectoCrearBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private ProyectoFacade proyectoFacade;
    
    protected Proyecto proyecto;

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    /**
     * Creates a new instance of ProyectoCrearBean
     */
    public ProyectoCrearBean() {
    }
    
    
    @PostConstruct
    public void init() {
        proyecto = new Proyecto();
    }
    
    public String doCrear(Usuario lider) {
        proyecto.setEstado("En Proceso");
        proyecto.setLider(lider);
        List<Proyecto> proyectosUsuario = (List<Proyecto>) lider.getProyectoCollection();
        proyectosUsuario.add(proyecto);
        usuarioFacade.edit(lider);
        proyectoFacade.create(proyecto);
        return "principal";
    }
}
