
/**
 * Décrivez votre classe Item ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Item
{
    private String aDescription;
    private int aPoids;
    
    /**
     * Constructeur d'objets de classe Item.
     */
    public Item(final String pDescription, final int pPoids){
        this.aDescription = pDescription;
        this.aPoids = pPoids;
    }
    
    /**
     * gets the weight of the item
     */
    public int getPoids(){
        return this.aPoids;
    }
    /**
     * sets the weight of the item
     */
    public void setPoids(final int pPoids){
        this.aPoids = pPoids;
    }
    
    /**
     * gets the description of the item
     */
    public String getDescription(){
        return this.aDescription ;
    }
    
    /**
     * Obtient la description de l'objet.
     */
    public String getItemDescription(){
        return "Il y a : " + this.aDescription + " . Cela pèse " + aPoids + " Joukous";
    }
}
