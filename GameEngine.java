import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019)
 */
public class GameEngine
{
    private Parser        aParser;
    private UserInterface aGui;
    private Player        aPlayer;
    public  String        aLastStation; //nom de la station aléatoire où se trouve le nid de José
    static final String[] aValidFoods= {"cookie","pain","miettes","donut"}; //Définit la liste de nourritures possibles
    
    /**
     * Attributes for all the minigames.
     */
    public  boolean       aMiniGamePFC;
    public  boolean       aMiniGameTux;
    public  int           aMiniGameTuxA;
    public  int           aMiniGameTuxB;
    public int            aMiniGameTuxAnswerCount;
    public  boolean       aMiniGameChiper;
    public boolean        aMiniGameDonut;
    
    /**
     * Attributes that verify is the game has been played or not
     */
    public  boolean       aMiniGamePFCPlayed;
    public  boolean       aMiniGameTuxPlayed;
    public  boolean       aMiniGameChiperPlayed;
    public boolean        aMiniGameDonutPlayed;
    
    /**
     * Creation of all the rooms
     */
    Room vJardinDuTrocadero = new Room("Vous êtes dans le jardin du Trocadéro","Images/_JardinDuTrocadero.jpg");
    Room vPremierEtageTourEiffel = new Room("Vous êtes au premier étage de la Tour Eiffel","Images/_PremierEtageTourEiffel.jpg");
    Room vDeuxiemeEtageTourEiffel = new Room("Vous êtes au deuxième étage de la Tour Eiffel","Images/_DeuxiemeEtageTourEiffel.jpg");
    Room vDernierEtageTourEiffel = new Room("Vous êtes au dernier étage de la Tour Eiffel","Images/_TroisiemeEtageTourEiffel.jpg");
    Room vArcDeTriomphe = new Room("Vous êtes à l'arc de trimomphe","Images/_ArcDeTriomphe.jpg");
    Room vAvenueKleber = new Room("Vous êtes dans l'avenue Kebler","Images/_AvenueKleber.jpg");
    Room vCafeKleber = new Room("Vous voilà sur la terasse du café Kleber","Images/_CafeKleber.jpg");
    Room vAvenueDesChampsElysees = new Room("Vous êtes dans l'avenue des champs élysées","Images/_AvenueDesChampsElysees.jpg");
    Room vStationDeMetro = new Room("Vous entrez dans la station de métro vous êtes sur la ligne 8\n ATTENTION ! Si vous rentrez dans une rame vous ne pourrez plus revenir en arrière","Images/_StationDeMetro.jpg");
    Room vRameDeMetro = new Room("Vous entrez dans la rame de métro, \n Attention de descendre au bon endroit!\nVous avez débloqué la commande 'descendre'\n Choissisez la bonne station","Images/_RameDeMetro.jpg");
    Room vCabineTelephonique = new Room("Vous voici dans le transporteur magique\n Vous avez débloqué la commande 'voyager' \n elle vous permet de vous téléporter à un lieu random","Images/_Cabine.jpg");
       
    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine(){
        this.aParser = new Parser();
        this.createRooms();
        this.aLastStation = chooseRandomStation();
        this.aPlayer.setSavedLocation(null);
        miniGamesToFalse();
        aMiniGameTuxAnswerCount = 0;
        this.aMiniGamePFCPlayed = false;
        this.aMiniGameTuxPlayed = false;
        this.aMiniGameChiperPlayed = false;
        this.aMiniGameDonutPlayed = false;
    }
    
    /**
     * Resets all miniGames to Inactive.
     */
    public void miniGamesToFalse(){
        this.aMiniGamePFC = false;
        this.aMiniGameTux = false;
        this.aMiniGameChiper = false;
        this.aMiniGameDonut = false;
    }
    
    /**
     * Définit l'interface utilisateur et affiche le message de bienvenue.
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Create all the rooms,link their exits together,inisialises it's items and defines a player.
     */
    private void createRooms()
    {
        Room[] vPossibleRooms = new Room[] {vJardinDuTrocadero,vPremierEtageTourEiffel,
        vDeuxiemeEtageTourEiffel,vDernierEtageTourEiffel,vArcDeTriomphe,vAvenueKleber,vAvenueDesChampsElysees};
        
        vJardinDuTrocadero.setExits("up",vPremierEtageTourEiffel);
        vJardinDuTrocadero.setExits("east",vAvenueKleber);
        vPremierEtageTourEiffel.setExits("up",vDeuxiemeEtageTourEiffel);
        vPremierEtageTourEiffel.setExits("down",vJardinDuTrocadero);
        vDeuxiemeEtageTourEiffel.setExits("up",vDernierEtageTourEiffel);
        vDeuxiemeEtageTourEiffel.setExits("down",vPremierEtageTourEiffel);
        vDernierEtageTourEiffel.setExits("down",vDeuxiemeEtageTourEiffel);
        vDernierEtageTourEiffel.setExits("south",vArcDeTriomphe);
        vDernierEtageTourEiffel.setExits("west",vJardinDuTrocadero);
        vDernierEtageTourEiffel.setExits("east",vAvenueKleber);
        vArcDeTriomphe.setExits("north",vCabineTelephonique);
        vArcDeTriomphe.setExits("south",vAvenueKleber);
        vCabineTelephonique.setExits("south",vArcDeTriomphe);
        vAvenueKleber.setExits("north",vArcDeTriomphe);
        vAvenueKleber.setExits("east",vAvenueDesChampsElysees);
        vAvenueKleber.setExits("west",vJardinDuTrocadero);
        vAvenueKleber.setExits("south",vCafeKleber);
        vCafeKleber.setExits("north",vAvenueKleber);
        vAvenueDesChampsElysees.setExits("west",vAvenueKleber);
        vAvenueDesChampsElysees.setExits("north",vStationDeMetro);
        vStationDeMetro.setExits("north",vRameDeMetro);
        vStationDeMetro.setExits("south",vAvenueDesChampsElysees);
        
        vJardinDuTrocadero.addItem("pain", new Item("pain", 4));
        vJardinDuTrocadero.addItem("lunettes", new Item("lunettes", 9));
        vPremierEtageTourEiffel.addItem("cookie", new Item("cookie", 3));
        vDeuxiemeEtageTourEiffel.addItem("donut", new Item("donut", 3));
        vDernierEtageTourEiffel.addItem("pain", new Item("pain", 4));
                                                            //vArcDeTriomphe.addItem("sucette",new Item("sucette", 2));
                                                            //vAvenueKleber.addItem("passNavigo", new Item("passNavigo", 3));
        vAvenueDesChampsElysees.addItem("feuille", new Item("feuille", 1));
        vStationDeMetro.addItem("chewing-gum", new Item("chewing-gum", 2));
        vRameDeMetro.addItem("déchets", new Item("déchets", 4));
        
        vJardinDuTrocadero.addCharacter("André", new Character("André","Ahh José, mon ennemi juré ! \nTu devras me battre pour pouvoir monter en haut de la Tour Eiffel","pfc"));
        vDernierEtageTourEiffel.addCharacter("Sage", new Character("Sage","Bienvenue dans mon Sanctuaire","reveal"));
        vCafeKleber.addCharacter("Passant", new Character("Passant","*sips tea*","braquage"));
        vStationDeMetro.addCharacter("Controleur", new Character("Controleur","Vous ne passerez pas...apres... si vous avez de la nourriture on peut s'arranger...","passNavigo"));
        vArcDeTriomphe.addCharacter("Enfants", new Character("Enfants","*iels crient beaucoup*","tuxMaths"));
        
        vPremierEtageTourEiffel.lockDoor();
        
        vAvenueDesChampsElysees.lockDoor();
        vAvenueDesChampsElysees.setKey("sucette");
        
        vRameDeMetro.lockDoor();
        vRameDeMetro.setKey("passNavigo");
        
        aPlayer = new Player("José", vJardinDuTrocadero);
    }
    
    /**
     * Chooses random station for the end of the game
     */
    public String chooseRandomStation(){
        String[] stations = {"Liberté", "Charenton",
        "Ecole", "Maisons"};

        Random rand1 = new Random();
        int i = rand1.nextInt(stations.length);

        return stations[i];
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "> " + pCommandLine );
        Command vCommand = this.aParser.getCommand( pCommandLine );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "I don't know what you mean..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) )
            this.printHelp();
        else if ( vCommandWord.equals( "go" ) )
            this.goRoom( vCommand );
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        } else if ( vCommandWord.equals( "eat" ) ) {
            if (!vCommand.hasSecondWord()){
                aGui.println("Eat what?!");
            } else {
                eat(vCommand.getSecondWord());
            } 
        } else if ( vCommandWord.equals( "look" ) ) {
            this.aGui.print(this.aPlayer.getCurrentRoom().getLongDescription());
            this.aGui.println(" ");
        } else if ( vCommandWord.equals("back")){
            this.back(vCommand);
        } else if (vCommandWord.equals("test")){
            if(vCommand.hasSecondWord()){
                test(vCommand.getSecondWord()+".txt");
            } else {
                aGui.println("quel est le nom du fichier test");
            }
        } else if(vCommandWord.equals("take")) {
            if (!vCommand.hasSecondWord()){
                aGui.println("Take which object ?");
            } else {
                take(vCommand.getSecondWord());
            } 
        } else if(vCommandWord.equals("drop")) {
            if (!vCommand.hasSecondWord()){
                aGui.println("Drop which object ?");
            } else {
                drop(vCommand.getSecondWord());
            } 
        } else if(vCommandWord.equals("descendre")) {
            if (!vCommand.hasSecondWord()){
                aGui.println("Descendre où ?");
            } else {
                descendre(vCommand.getSecondWord());
            } 
        } else if(vCommandWord.equals("saveLoc")){
            saveLoc();
        }   else if (vCommandWord.equals("goLoc")){
            goLoc();
        } else if (vCommandWord.equals("interract")) {
            if (!vCommand.hasSecondWord()){
                aGui.println("Interract with who ?");
            } else {
                interract(vCommand.getSecondWord());
            } 
        } else if (vCommandWord.equals("pierre")||vCommandWord.equals("feuille")||vCommandWord.equals("ciseaux")){
            if(vCommand.hasSecondWord()){
                aGui.println("Just one word needed here");
            } else if(aMiniGamePFC){
                pierreFeuilleCiseaux  (vCommandWord);
            } else{
                aGui.println("No active Pierre-Feuille-Ciseaux game, go see a npc to start the game");
            } 
        } else if (vCommandWord.equals("reponse")){
            if(!vCommand.hasSecondWord()){
                aGui.println("What's the answer ?");
            } else if(aMiniGameTux){
                tuxMaths  (vCommand.getSecondWord());
            } else if(aMiniGameChiper){
                tuxMaths  (vCommand.getSecondWord());
            } else{
                aGui.println("No active game, go see a npc to start the game");
            }
        } else if (vCommandWord.equals("chiper")){
            if(!vCommand.hasSecondWord()){
                aGui.println("Steal what ?");
            } else if(aMiniGameChiper){
                chiper(vCommand.getSecondWord());
            } else {
                aGui.println("You need to interact with pnj to chipe their items");
            }
        } else if (vCommandWord.equals("give")){
            if(!vCommand.hasSecondWord()){
                aGui.println("Give what ?");
            } else if(aMiniGameDonut){
                donutGive(vCommand.getSecondWord());
            } else {
                aGui.println("You need to interact with pnj start this miniGame");
            }
        }
    }
    
    /**
     * Displays dialog from npcs and starts their minigame
     */
    private void interract(final String pCommand){
        Character vCharacter = this.aPlayer.getCurrentRoom().getCharacter(pCommand);
        if(vCharacter != null){
            this.aGui.println(vCharacter.getDialog());
            if(vCharacter.getMinigame().equals("reveal")){
                reveal();
                return;
            }else if(vCharacter.getMinigame().equals("pfc")){
                if(aMiniGamePFCPlayed){
                    this.aGui.println("Tu est revenu te moquer de moi ? Passe avant que je change d'avis");
                } else {
                    this.aMiniGamePFC=true;
                    this.aGui.println("Play your move 'pierre', 'feuille' or 'ciseaux'");
                }
                return;
            }
            else if(vCharacter.getMinigame().equals("tuxMaths")){
                if(aMiniGameTuxPlayed){
                    this.aGui.println("On a eu ce qu'on voulait, n'embêtons plus ces enfants");
                    return;
                } else {
                    this.aMiniGameTux = true;
                    this.aGui.println("Il faudrait récupérer la sucette des enfants \n"
                    +"pour cela il faut résoudre 3 multiplications");
                    tuxMaths("intro");
                    return;
                }
            } else if(vCharacter.getMinigame().equals("passNavigo")){
                if(aMiniGameDonutPlayed){
                    this.aGui.println("Controleurs : Je ne veux plus vous voir");
                }
                if(this.aPlayer.getItem("passNavigo")!=null){
                    this.aGui.println("Vous avez votre pass ? Parfait ! vous pouvez passer");
                } else if(this.aPlayer.getItem("donut")!=null) {
                    this.aGui.println("Vous avez un donut sur vous !");
                    this.aGui.println("avec la commande 'give' tentez de la donner au Contôleur pour passer !");
                    this.aGui.println("Vous avez 50% de chances que cela marche");
                    this.aMiniGameDonut = true;
                } else {
                    this.aGui.println("Vous n'avez rien à offrir au Controleur");
                }
                return;
            } else if(vCharacter.getMinigame().equals("braquage")){
                if(aMiniGameChiperPlayed){
                    this.aGui.println("N'embetons plus les pauvres passants");
                }else {
                    this.aGui.println("Un passNavigo dépasse de la poche du passant, avec la commande 'chiper' tu peux lui voler");
                    this.aGui.println("Vos chances de réussite sont de 30%, elles passent à 100% si vous portez des lunettes de soleil");
                    this.aMiniGameChiper =true;
                    return;
                }
            } 
        } else {
            this.aGui.println(pCommand + " is not here");
            return;
        }
    }
    
    /**
     * Saves player location with beamer item
     */
    private void saveLoc(){
        if(this.aPlayer.getItem("branche")!=null){
            this.aGui.println("your location is saved");
            this.aPlayer.setSavedLocation(this.aPlayer.getCurrentRoom());
            return;
        } else {
            this.aGui.println("no branche on you");
            return;
        }
    }
    
    /**
     * Loads a previously saved location with beamer
     */
    private void goLoc(){
        if(this.aPlayer.getItem("branche")!=null){
            if(this.aPlayer.getSavedLocation()==null){
                this.aGui.println("you didn't save your location");
                return;
            } else{
                this.aPlayer.setCurrentRoom(this.aPlayer.getSavedLocation());
                this.aGui.println("You have been transported to your saved location");
                this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
                this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
                this.aPlayer.setSavedLocation(null);
                return;
            }
        } else {
            this.aGui.println("no branche on you");
            return;
        }
    }
    
    /**
     * Allows player to eat various items
     */
    private void eat(final String pCommand){
        if(this.isCommestible(pCommand)){
            Item vItem = this.aPlayer.getItem(pCommand);
            if(vItem == null){
                this.aGui.println("You don't have any " + pCommand);
            } else {
            if(vItem.getDescription().equals("cookie")){
                this.aGui.println("You have eaten magic cookie you can now carry more items");
                    this.aPlayer.setPoidsMax(15);
            } 
            if(vItem.getDescription().equals("pain")){
                this.aGui.println("NomNomNom");
            } else if(vItem.getDescription().equals("miettes")){
                this.aGui.println("Eww c'est des cailloux");
            } else if(vItem.getDescription().equals("donut")){
                this.aGui.println("Miam, c'était un tres bon donut");
            } 
            this.aPlayer.removeItem(pCommand,vItem);
            this.aPlayer.setPoids(this.aPlayer.getPoids() - vItem.getPoids());
            this.aGui.println(this.aPlayer.getItemsWeight());
            } return;
        } else {
            aGui.println("This item is not commestible");
            return;
        }
    }
    
    /**
     * verifies if item is in the list of commestible foods
     */
    public boolean isCommestible( final String pString ){
        for ( int i=0; i<aValidFoods.length; i++ ) {
            if ( this.aValidFoods[i].equals( pString ))
                return true;
        }
        // commande execitée seulement si la commande est pas valide
        return false;
    }
    
    /**
     * allows player to choose the station to get off the metro
     */
    private void descendre(final String pCommand){
        if(!this.aPlayer.getCurrentRoom().getImageName().equals("Images/_RameDeMetro.jpg")){
            aGui.println("Vous n'êtes pas dans une rame de métro");
            return;
        } else {
            if(pCommand.equals(aLastStation)){
                win();
            } else {
                loose();
            } 
            endGame();
            return;
        } 
    }
    
    /**
     * allows player to transfer items from room to inventory
     */
    private void take(final String pCommand){
        Item vItem = this.aPlayer.getCurrentRoom().getItem(pCommand);
        if(vItem != null){
            int vTotPoids = this.aPlayer.getPoids() + vItem.getPoids();
            if(vTotPoids <= this.aPlayer.getPoidsMax()){
                this.aPlayer.addItem(pCommand,vItem);
                this.aPlayer.setPoids(vTotPoids);
                this.aPlayer.getCurrentRoom().removeItem(pCommand,vItem);
                this.aGui.println(this.aPlayer.getCurrentRoom().getItemString());
                this.aGui.println(this.aPlayer.getItemString());
            } else {
                this.aGui.println("You can't take more items");
            }
        } else {
            this.aGui.println("There is no " + pCommand + " here");
        }
    }
    
    /**
     * allows player to transfer items from inventory to room
     */
    private void drop(final String pCommand){
        Item vItem = this.aPlayer.getItem(pCommand);
        if(vItem != null){
            this.aPlayer.removeItem(pCommand,vItem);
            this.aPlayer.setPoids(this.aPlayer.getPoids() - vItem.getPoids());
            this.aPlayer.getCurrentRoom().addItem(pCommand,vItem);
            this.aGui.println(this.aPlayer.getCurrentRoom().getItemString());
            this.aGui.println(this.aPlayer.getItemString());
        } else {
            this.aGui.println("You don't have any " + pCommand);
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        this.aGui.println(" ");
        this.aGui.println("================");
        this.aGui.println("Bienvenue dans Paris !");
        this.aGui.println("Dépêches toi, tu dois Retrouver ton nid !");
        this.aGui.println("Tapes 'help' si tu as besoin d'aide");
        this.aGui.println("================");
        this.aGui.println(" ");
        printRoomInfo();
        //this.aGui.println("la station random est:"+aLastStation);
        this.aGui.println(this.aPlayer.getCurrentRoom().getDescription());
        if (this.aPlayer.getCurrentRoom().getImageName() != null )
            this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName() );
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp(){
        this.aGui.println("Pas d'inquiétude");
        this.aGui.println("Tu est dans Paris");
        this.aGui.println(" ");
        this.aGui.println("Les commandes possibles sont :");
        this.aGui.println(aParser.getCommandString());
    }

    /**
     * Affiche des informations sur la salle actuelle.
     */
    private void printRoomInfo(){
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
        this.aGui.println("");
        this.aGui.println("");
    }
    
    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    public void goRoom( final Command pCommand ){
        if ( ! pCommand.hasSecondWord() ) {
            // if there is no second word, we don't know where to go...
            this.aGui.println( "Go where?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();
        String vString = this.aPlayer.goRoom(vDirection);
        // Try to leave current room.
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );
        if(this.aPlayer.getMoves()+1 >= this.aPlayer.getMovesMax()){
            this.aGui.println( "You have reached the max move limit" );
            loose();
            return;
        }
        
        if ( vString == null ) {
            this.aPlayer.addMove();
            miniGamesToFalse();
            this.printRoomInfo();
            if (this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName() );
        } else {
            this.aGui.println(vString);
        } 
    }

    /**
     * Permet au joueur de revenir à la salle précédente.
     */
    private void back(final Command pCommand){
        if (pCommand.hasSecondWord()){
                aGui.println("No second word needed for 'back'");
                return;
        }
        
        String vString = this.aPlayer.back();
        
        if (vString==null){
            this.printRoomInfo();
            aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
        } else {
            aGui.println(vString);
        }
    }
    
    /**
     * Exécute les commandes contenues dans un fichier de test.
     */
    public void test(final String pName){
        Scanner vScan;
        try {
            vScan = new Scanner(new File (pName));
            while (vScan.hasNextLine()){
                String vLine = vScan.nextLine();
                interpretCommand(vLine);
            }
        } catch(final FileNotFoundException pFile) {
            
        }
    }
    
    /**
     * "Chiper" MiniGame
     */
    public void chiper(final String pString){
        if(!this.aPlayer.getCurrentRoom().getImageName().equals("Images/_CafeKleber.jpg")){
            aGui.println("Il y n'y a personne à chiper dans ce lieu");
            return;
        } else {
            if(pString.equals("passNavigo")){
                Random rand = new Random();
                int i = rand.nextInt(10);
                if(i<4||this.aPlayer.getItem("lunettes")!=null){
                    aGui.println(pString + " a été chipé avec succes");
                    aMiniGameChiper = false;
                    aMiniGameChiperPlayed = true;
                    this.aPlayer.addItem("passNavigo",new Item("passNavigo",3));
                    aGui.println(this.aPlayer.getItemString());
                    return;
                } else {
                    aGui.println("Passant : Eh oh! Satané pigeon, pars d'ici");
                    aMiniGameChiper = false;
                    return;
                }
            } else {
                aGui.println("Vous ne pouvez chiper cet objet");
                return;
            }
        } 
    }
    
    /**
     * "TuxMaths" MiniGame
     */
    public void tuxMaths(final String pString){
        if(pString.equals("intro")){
            tuxMathsRandomiser();
        } else {
            if (pString.equals(""+aMiniGameTuxA*aMiniGameTuxB)){
                aMiniGameTuxAnswerCount++;
                this.aGui.println("Bonne réponse !      Combo chargé à " +aMiniGameTuxAnswerCount+ "/3");
                tuxMathsRandomiser();
            } else {
                aMiniGameTuxAnswerCount=0;
                this.aGui.println("Mauvaise réponse :(      Combo chargé à " +aMiniGameTuxAnswerCount+ "/3");
                tuxMathsRandomiser();
            }
        }
        if(aMiniGameTuxAnswerCount>2){
            this.aGui.println("Vous avez réussi votre combo \n Un Enfant a fait tomber sa sucette !");
            this.aGui.println("");
            vArcDeTriomphe.addItem("sucette",new Item("sucette", 2));
            this.aGui.println(this.aPlayer.getCurrentRoom().getItemString());
            this.aMiniGameTux = false;
            this.aMiniGameTuxPlayed = true;
            return;
        }
    }
    /**
     * "TuxMaths" MiniGame Attribute Randomizer
     */
    public void tuxMathsRandomiser(){
        Random rand = new Random();
        int i = rand.nextInt(50);
        int j = rand.nextInt(50);
        aMiniGameTuxA = i;
        aMiniGameTuxB= j;
        
        this.aGui.println("avec la commande 'reponse' suivie du résultat, calculez \n"
            + aMiniGameTuxA + " x " +aMiniGameTuxB);
    }
    
    /**
     * "reveal" MiniGame
     */
    public void reveal(){
        //"Liberté", "Charenton","Ecole", "Maisons" aLastStation
        if (aLastStation.equals("Liberté")){
            this.aGui.println("mhhh Ton nid se trouve dans ma station préférée,\nc'est la sensation que tu ressens"
            +" quand tu voles avec tes ailes au dessus de Paris,\n une sentation que je ne suis pas prêt d'oublier\n"
            +"quand j'étais jeune je voyagais beaucoup,\n mais maintenant il est vrai que je reste plus ici, tranquilou");
        } else if (aLastStation.equals("Charenton")){
            this.aGui.println("mhhh Ton nid se trouve dans une station que j'aime beaucoup,\n elle me rapelle des souvenirs"
            +" quand j'étais jeune j'allais ici avec ma mère,\n elle m'offrait toujours une glace à la fin des balades"
            +"\n ahh que de souvenirs, je.. oh pardon.. votre nid se trouve à dans une station qui rime avec 'menton'");
        }else if (aLastStation.equals("Ecole")){
            this.aGui.println("mhhh Ton nid se trouve dans une station que je n'aime pas"
            +"\n elle me rapelle mon enfance, où j'étais enfermé des heures durant dans ces institutions"
            +"\n je m'y suis fait des copain c'est sùr, et j'y ait appris beaucoup c'est vrai"
            +"\n mais bon bien que cela soit un passage très formateur de la vie, j'en garde un souvenir terni");
        } else if (aLastStation.equals("Maisons")){
            this.aGui.println("mhhh Ton nid se trouve dans une station dont j'aime le nom"
            +"\n on s'y sent bien, c'est là qu'on se réunit à noël avec la famille"
            +"\n enfin Noël ET mon anniversaire, savez vous que je suis né le 24 Décembre ?"
            +"\n on me demande souvent ce que cela fait, mais boh, c'est un anniversaire comme un autre à vrai dire");
        }
    }
    
    /**
     * "donutGive" MiniGame
     */
     public void donutGive (final String pString){
         if(!this.aPlayer.getCurrentRoom().getImageName().equals("Images/_StationDeMetro.jpg")){
            aGui.println("Il y n'y a personne à qui donner cet objet ici");
            return;
        } else {
            if(pString.equals("donut")){
                Random rand = new Random();
                int i = rand.nextInt(10);
                if(i>5){
                    aGui.println(pString + " a été donné avec succes");
                    aMiniGameDonut = false;
                    aGui.println("La porte a été déverouillée");
                    aGui.println("Controleur : Passez avant que je change d'avis");
                    vRameDeMetro.unlockDoor();
                    return;
                } else {
                    aGui.println("Controleur : Ceci est un donut de pètre qualité, hors de ma vue !");
                    aMiniGameDonut = false;
                    return;
                }
            } else {
                aGui.println("Vous ne pouvez donner de " + pString);
                return;
            }
        } 
    }
    
    /**
     * "pierreFeuilleCiseaux" MiniGame
     */
    public void pierreFeuilleCiseaux  (final String pMove){
        String[] pnjMoves = {"pierre", "feuille","ciseaux"};
        Random rand = new Random();
        int i = rand.nextInt(pnjMoves.length);
        String pnjMove = pnjMoves[i];
        boolean victoire = pnjMove.equals("pierre")&&pMove.equals("feuille")
        ||pnjMove.equals("feuille")&&pMove.equals("ciseaux")
        ||pnjMove.equals("ciseaux")&&pMove.equals("pierre");
        boolean defaite = pMove.equals("pierre")&&pnjMove.equals("feuille")
        ||pMove.equals("feuille")&&pnjMove.equals("ciseaux")
        ||pMove.equals("ciseaux")&&pnjMove.equals("pierre");
        this.aGui.println("Adversaire : "+pnjMove+" !");
        if(pMove.equals(pnjMove)){
            this.aGui.println("it's a draw, let's try again");
            return;
        } else if(victoire){
            this.aGui.println("tu as gagné, tu peux passer");
            this.aMiniGamePFC = false;
            this.aMiniGamePFCPlayed = true;
            vJardinDuTrocadero.setExits("up",vPremierEtageTourEiffel);
            vPremierEtageTourEiffel.unlockDoor();
        } else if(defaite){
            this.aGui.println("tu as perdu. Retente ta chance");
            this.aMiniGamePFC = false;
        }
        return;
    }
    
    /**
     * affiche la fin négative du jeu.
     */
    public void loose(){
        this.aGui.showImage("Images/_Defaite.jpg");
        this.aGui.println("vous avez predu\n \n ");
        endGame();
    }
    
    /**
     * affiche la fin positive du jeu.
     */
    public void win(){
        this.aGui.showImage("Images/_Victoire.jpg");  
        this.aGui.println("vous avez gagné\n \n ");
        endGame();
    }
    
    /**
     * Met fin au jeu.
     */
    private void endGame(){
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false );
    }

}
