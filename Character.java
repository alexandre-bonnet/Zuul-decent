
/**
 * Décrivez votre classe Character ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Character
{
    private String aName;
    private String aDialog;
    private String aMinigame;
    
    /**
     * Constructeur d'objets de classe Item.
     */
    public Character(final String pName,final String pDialog,final String pMinigame){
        this.aName = pName;
        this.aDialog = pDialog;
        this.aMinigame = pMinigame;
    }
    
    /**
     * gets the name of the character
     */
    public String getName(){
        return this.aName ;
    }
    
    /**
     * gets the dialog of the character
     */
    public String getDialog(){
        return this.aDialog ;
    }
    
    /**
     * gets the miniGame of the character
     */
    public String getMinigame(){
        return this.aMinigame ;
    }
}
