/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ump.scms.bict.user.models;

/**
 *
 * @author mphep
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;;

public class TestJpa {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Users user = new Users("John Doe");
        em.persist(user);
        em.getTransaction().commit();
        
        System.out.println("User saved: " + user.getId());
        
        em.close();
        emf.close();
    }
}
