/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp1.models.other_models;

/**
 *
 * @author drissa
 */
public class Personne {
    
    private int id;
    private  String nom;
    private  String prenom;

    public Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getNom(){
        return this.nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public void setNom(String new_nom){
        this.nom = new_nom;
    }
    public void setPrenom(String new_prenom){
        this.prenom = new_prenom;
    }
    @Override
    public String toString(){
        return(this.id+" ; "+ this.nom+" ; "+this.prenom);
    }
}
