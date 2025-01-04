 /**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author Alexandre_Bonnet
 */
public class Command
{ 
    private String aCommandWord;
    private String aSecondWord;
    
    /**
    * Met en paramêtre les mots tapés en commande
    */
    public Command(final String pCommandWord,final String pSecondWord){
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    }
    
    /**
    * Retroune le premier mot de la commande tapée
    */
    public String getCommandWord(){
     return this.aCommandWord;
    } 
    
    /**
    * Retroune le second mot de la commande tapée
    */
    public String getSecondWord(){
     return this.aSecondWord;
    } 
    
    /**
    * Vérifie qu'il y a un second mot dans la commande tapée
    */
    public boolean hasSecondWord(){
        return this.aSecondWord != null;
    }
    
    /**
    * Vérifie qu'il y a un premier mot dans la commande tapée
    */
    public boolean isUnknown(){
        return this.aCommandWord == null;
    }
} // Command
