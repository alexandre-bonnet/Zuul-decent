/**
* Classe Room - un lieu du jeu d'aventure Zuul.
*
* @Alexandre_Bonnet
*/

import java.util.HashMap;
import java.util.Set;

public class Room
{ 
    private String aDescription;
    private HashMap<String, Room>aExits;
    private String aImageName;
    private HashMap<String, Item>aItems;
    private HashMap<String, Character>aCharacters;
    private boolean     aIsLocked;
    private Item        aKey;

    /**
    * Constructeur de Rooms avec la description de la salle
    */
    public Room(final String pDescription, final String pImage){
        this.aDescription = pDescription;
        aExits = new HashMap<String, Room>();
        this.aImageName = pImage;
        this.aItems = new HashMap<String, Item>();
        this.aCharacters = new HashMap<String, Character>();
        aIsLocked = false;
    }
    
    /**
    * Sets the Room's item key
    */
    public void setKey(final String pKey){
        this.aKey = new Item(pKey, 0);
    }
    /**
    * Gets the Room's item key
    */
    public Item getKey(){
        return this.aKey;
    }
    /**
    * Checks if the Room has an item key
    */
    public boolean hasKey(){
        return this.aKey!=null;
    }
    
    /**
    * Checks if the Room is locked
    */
    public boolean isLocked(){
        return aIsLocked;
    }
    /**
    * Unlocks Room
    */
    public void unlockDoor(){
        aIsLocked = false;
    }
    /**
    * Locks Room
    */
    public void lockDoor(){
        aIsLocked = true;
    }
    
    /**
     * Ajoute un objet à la liste des objets possédés par le joueur.
     */
    public void addCharacter(final String pName,final Character pCharacter){
        this.aCharacters.put(pName,pCharacter);
    }
    
    /**
     * Supprime un objet de la liste des objets possédés par le joueur.
     */
    public void removeCharacter(final String pName,final Item pCharacter){
        this.aItems.remove(pName,pCharacter);
    }
    
    /**
     * Obtient un objet de la liste des objets possédés par le joueur.
     */
    public Character getCharacter(final String pNom){
        return this.aCharacters.get(pNom);
    } 
    
    /**
     * Obtient une représentation sous forme de chaîne des objets possédés par le joueur.
     */
    public String getCharacterString(){
        String txtCharacter = "Characters : ";
        Set<String> keys = aCharacters.keySet();
        for (String vCharacter : keys) {
            txtCharacter += ' ' + vCharacter;
        } 
        return txtCharacter;
    }
    
    /**
    * retourne la description de la room actuelle
    */
    public String getDescription(){
     return this.aDescription;
    } //retourne le String présent dans description
    
    /**
    * retourne une description plus complète de la room actuelle
    */
    public String getLongDescription(){
        String vDescription = this.aDescription + ".\n" + getItemString() + getCharacterString()+".\n"
        + getExitString() +".\n" ;
        return vDescription;
    }
    
    /**
    * Assigne les sorties de la room
    */
    public void setExits(final String pDirection, final Room pNextRoom){
       aExits.put(pDirection,pNextRoom);
    } // change les parametres 
    
    /**
    * retourne les sorties de la room
    */
    public Room getExit(final String pDirection){
        return aExits.get(pDirection);
    }
    
    /**
    * Checks if the Room in parameter is an exit if the Room
    */
    public boolean isExit(final Room pRoom){
        return this.aExits.containsValue(pRoom);
    }

    /**
    * Retourne les sorties de la room actuelle sous forme de string
    */
    public String getExitString(){
        String txtExit = "Exits :";
        Set<String> keys = aExits.keySet();
        for (String vExit : keys) {
            txtExit += ' ' + vExit;
        } 
        return txtExit;
    }
    
    /**
     * Ajoute un objet à la liste des objets possédés par le joueur.
     */
    public void addItem(final String pName,final Item pItem){
        this.aItems.put(pName,pItem);
    }
    
    /**
     * Supprime un objet de la liste des objets possédés par le joueur.
     */
    public void removeItem(final String pName,final Item pItem){
        this.aItems.remove(pName,pItem);
    }
    
    /**
     * Obtient un objet de la liste des objets possédés par le joueur.
     */
    public Item getItem(final String pNom){
        return this.aItems.get(pNom);
    } 
    
    /**
     * Obtient une représentation sous forme de chaîne des objets possédés par le joueur.
     */
    public String getItemString(){
        StringBuilder aItemDescription = new StringBuilder("Items : " + "\n");
        for (Item aItem : aItems.values()) {
            aItemDescription.append(aItem.getItemDescription() + "\n");
        }
        return aItemDescription.toString();
    }
    
    /**
     * Return a string describing the room's image name
     */
    public String getImageName(){
         return this.aImageName;
    }
} // Room
