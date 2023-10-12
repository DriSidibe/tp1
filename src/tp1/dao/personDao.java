/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1.dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tp1.controllers.jpa_controllers.PersonJpaController;
import tp1.controllers.jpa_controllers.exceptions.NonexistentEntityException;
import tp1.models.database_models.Person;

/**
 *
 * @author drissa
 */
public class personDao {
    private final PersonJpaController personJpaController;
    private EntityManagerFactory emf = null;
    
    public personDao(){
        emf = Persistence.createEntityManagerFactory("tp1PU");
        personJpaController = new PersonJpaController(emf);
    }
    
    public void addPerson(Person p){
        personJpaController.create(p);
    }
    
    public void deletePerson(Person p) throws NonexistentEntityException{
        personJpaController.destroy(p.getId());
    }
    
    public void modifyPerson(Person p) throws NonexistentEntityException, Exception{
        personJpaController.edit(p);
    }
    
    public List<Person> getAllPerson() throws NonexistentEntityException, Exception{
        return personJpaController.findPersonEntities();
    }
}
