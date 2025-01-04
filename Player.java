import java.util.Stack;
import java.util.HashMap;
/**
 * Décrivez votre classe Player ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Player
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private String      aPseudo;
    private Room        aCurrentRoom;
    private Stack<Room> aPreviousRooms = new Stack<Room>();
    private HashMap<String, Item> aItems;
    private int         aPoidsMax;
    private int         aPoids;
    private int         aMovesMax;
    private int         aMoves;
    private Room        aSavedLocation;

    /**
     * Constructeur d'objets de classe Player
     */
    public Player(final String pPseudo,final Room pFirstRoom){
        this.aPseudo = pPseudo;
        this.aCurrentRoom = pFirstRoom;
        this.aPreviousRooms = new Stack<Room>();
        this.aItems = new HashMap<String, Item>();
        this.aPoids = 0;
        this.aPoidsMax = 10;
        this.aMoves=0;
        this.aMovesMax = 40;
    }
    
    /**
     * Obtient le pseudo du joueur.
     */
    public String getPseudo(){
        return this.aPseudo;
    }
    
    /**
     * Obtient la salle actuelle du joueur.
     */
    public Room getCurrentRoom(){
        return this.aCurrentRoom;
    }
    
    /**
     * Définit la salle actuelle du joueur.
     */
    public void setCurrentRoom(final Room pCurrentRoom){
        this.aCurrentRoom = pCurrentRoom;
    }
    
    /**
     * Checks player inventory for nextRoom Key
     */
    public boolean insertKey(final Item pKey){
        return this.getItem(pKey.getDescription())!=null;
    }
    
    /**
     * gets the number of moves made by player
     */
    public int getMoves(){
        return this.aMoves;
    }
    
    /**
     * sets the number of moves made by player
     */
    public void addMove(){
        aMoves++;
    }
    
    /**
     * gets the Maximum number of moves the player can do
     */
    public int getMovesMax(){
        return this.aMovesMax;
    }
    
    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    public String goRoom( final String pDirection){
        Room vNextRoom = this.aCurrentRoom.getExit( pDirection );
        if ( vNextRoom == null ) {
            return "There is no door!";
        } else if(vNextRoom.isLocked()){
            if (!vNextRoom.hasKey()){
                return "this room is blocked by an npc, interract with it to unlock it";
            } else if(!insertKey(vNextRoom.getKey())){
                return "this room is locked, you need " +vNextRoom.getKey().getDescription()+" to unlock it";
            } else {
                vNextRoom.unlockDoor();
                return "the door has been unlocked, you can now go through";
            }
        }else {
            this.aPreviousRooms.push(this.aCurrentRoom);
            this.aCurrentRoom = vNextRoom;
            return null;
        }
        
    }
    
    /**
     * Retourne à la salle précédente visitée par le joueur.
     */
    public String back(){
        if(!this.aCurrentRoom.isExit(this.aPreviousRooms.peek())){
            return "this was a one-way door";
        } else if(!this.aPreviousRooms.empty()){
            this.aCurrentRoom = this.aPreviousRooms.pop();
            return null;
        } else {
            return "no previous room";
        }
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
        StringBuilder aItemDescription = new StringBuilder("Your items are : " + "\n");
        for (Item aItem : aItems.values()) {
            aItemDescription.append(aItem.getDescription() + "\n");
        }
        aItemDescription.toString();
        
        return aItemDescription  + getItemsWeight();
    }
    
    /**
     * Nous retourne le poid combiné des objets portés par le joueur.
     */
    public String getItemsWeight(){
        int vPoids = 0;
        for (Item aItem : aItems.values()) {
            vPoids +=aItem.getPoids();
        } return "Le total pèse : "+ vPoids;
    }
    
    /**
     * gets the weight the player is carrying
     */
    public int getPoids(){
        return this.aPoids;
    }
    /**
     * sets the weight the player is carrying
     */
    public void setPoids(final int pPoids){
        this.aPoids = pPoids;
    }
    /**
     * gets the Maximum weight the player can carry
     */
    public int getPoidsMax(){
        return this.aPoidsMax;
    }
    /**
     * sets the Maximum weight the player can carry
     */
    public void setPoidsMax(final int pPoids){
        this.aPoidsMax = pPoids;
    }
    
    /**
     * gets the Location saved by the beamer
     */
    public Room getSavedLocation(){
        return this.aSavedLocation;
    }
    /**
     * sets the Location saved by the beamer
     */
    public void setSavedLocation(final Room pRoom){
        this.aSavedLocation = pRoom;
    }
}
