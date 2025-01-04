/**
* Décrivez votre classe CommandWords ici.
*
* @author Alexandre_Bonnet
*/
public class CommandWords
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    
    /**
     * on crée un tableau de String qui contient tout les mots valides
     */
    private static final String[] aValidCommands 
    = {"go","quit","help","look","eat","back",
        "test","take","drop","interract","saveLoc","goLoc"}; //List of valid commands
        
    private static final String[] aHiddenValidCommands
    ={"descendre","voyager","pierre","feuille","ciseaux","reponse","chiper","give"}; //List of valid commands that don't show up with 'help' (used for room Specific commands)
    
    /**
     * on compare le mot entré et le tableau
     * afin de savoir si la commande est
     */
    public boolean isCommand( final String pString )
    {
        for ( int i=0; i<this.aValidCommands.length; i++ ) {
            if ( this.aValidCommands[i].equals( pString ))
                return true;
        } for ( int i=0; i<this.aHiddenValidCommands.length; i++ ) {
            if ( this.aHiddenValidCommands[i].equals( pString ))
                return true;
        }
        // commande execitée seulement si la commande est pas valide
        return false;
    }
    
    /**
     * returns a String list of valid commands
     */
    public String getCommandList(){
        String vCommand = "";
        for(String command : aValidCommands){
            vCommand += command + ", ";
        } return vCommand;
    }
}
