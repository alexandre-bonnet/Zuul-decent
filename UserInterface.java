//import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
//import java.awt.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
//import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
//import java.awt.image.*;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import java.awt.GridLayout;

/**
 * This class implements a simple graphical user interface with a 
 * text entry area, a text output area and an optional image.
 * 
 * @author Alexandre_Bonnet
 * @version 1.0 (Jan 2003) DB edited (2023)
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    
    private JButton aButtonNorth;
    private JButton aButtonSouth;
    private JButton aButtonWest;
    private JButton aButtonEast;
    private JButton aButtonUp;
    private JButton aButtonDown;
    
    private JPanel aPanel;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
        this.aEngine.setGUI(this);
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the entry field.
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( pOnOff ) { // enable
            this.aEntryField.getCaret().setBlinkRate( 500 ); // cursor blink
            this.aEntryField.addActionListener( this ); // reacts to entry
        }
        else { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "José est posé" ); // change the title !
        this.aEntryField = new JTextField( 34 );
        this.aPanel = new JPanel();
        this.aPanel.setLayout(new GridLayout(3,3));

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );
        
        this.aButtonNorth = new JButton("north");
        this.aButtonSouth = new JButton("south");
        this.aButtonWest = new JButton("west");
        this.aButtonEast = new JButton("east");
        this.aButtonUp = new JButton("up");
        this.aButtonDown = new JButton("down");
        

        this.aImage = new JLabel();

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aPanel, BorderLayout.EAST );
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );

        // add some event listeners to some components
        this.aEntryField.addActionListener(this);
        this.aButtonNorth.addActionListener(this);
        this.aButtonSouth.addActionListener(this);
        this.aButtonWest.addActionListener(this);
        this.aButtonEast.addActionListener(this);
        this.aButtonUp.addActionListener(this);
        this.aButtonDown.addActionListener(this);
        
        this.aPanel.add(this.aButtonNorth);
        this.aPanel.add(this.aButtonSouth);
        this.aPanel.add(this.aButtonWest);
        this.aPanel.add(this.aButtonEast);
        this.aPanel.add(this.aButtonUp);
        this.aPanel.add(this.aButtonDown);

        // to end program when window is closed
        this.aMyFrame.addWindowListener(new WindowAdapter() {
                // anonymous class
                @Override public void windowClosing(final WindowEvent pE)
                {
                    System.exit(0);
                }
            } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     */
    public void actionPerformed( final ActionEvent pE ) {
        if(this.aButtonNorth.equals(pE.getSource())){
            this.processCommand("go north");
        }else if(this.aButtonSouth.equals(pE.getSource())){
            this.processCommand("go south");
        }
        else if(this.aButtonEast.equals(pE.getSource())){
            this.processCommand("go east");
        }
        else if(this.aButtonWest.equals(pE.getSource())){
            this.processCommand("go west");
        }
        else if(this.aButtonUp.equals(pE.getSource())){
            this.processCommand("go up");
        }
        else if(this.aButtonDown.equals(pE.getSource())){
            this.processCommand("go down");
        } else {
            this.processCommand(this.aEntryField.getText());
        }
    } // actionPerformed(.)

    /**
     * A command has been entered in the entry field.  
     * Read the command and do whatever is necessary to process it.
     */
    private void processCommand(final String pInput)
    {
        String vInput = pInput;
        this.aEntryField.setText( "" );

        this.aEngine.interpretCommand( vInput );
    } // processCommand()
} // UserInterface 
