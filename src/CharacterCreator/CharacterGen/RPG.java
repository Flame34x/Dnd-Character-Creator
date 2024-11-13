package CharacterCreator;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
public class RPG {

  static PlayerCharacter pc;
  static List < CharClass > classes = new ArrayList < > (); // Use a dynamic list instead of a fixed-size array
  static List < CharRace > races = new ArrayList < > ();

  public static void main(String[] args) {
    RPG rpg = new RPG();
    rpg.MainMenu(rpg);
  }

  public void MainMenu(RPG rpg) {
    System.out.println("Current working directory: " + System.getProperty("user.dir"));

    rpg.InitializeClasses();
    rpg.InitializeRaces();

    JFrame mainMenu = new JFrame("D&D Character Generator");
    mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainMenu.setSize(300, 300);
    mainMenu.setLayout(new FlowLayout());
    JButton buttonGen = new JButton("Generate Character");
    mainMenu.add(buttonGen);
    mainMenu.setVisible(true);
    JButton buttonCC = new JButton("Create a Class");
    mainMenu.add(buttonCC);
    JButton buttonCR = new JButton("Create a Race");
    mainMenu.add(buttonCR);
    JButton buttonLoad = new JButton("Load Character");
    mainMenu.add(buttonLoad);
    JButton buttonExit = new JButton("Exit");
    mainMenu.add(buttonExit);
    buttonGen.addActionListener(e -> CharacterGeneration.InitializeGeneration(pc, races, classes, rpg, mainMenu));
    buttonCC.addActionListener(e -> {
      ClassCreator.CreateClass(rpg, classes);
      mainMenu.dispose();
    });
    buttonLoad.addActionListener(e -> SaveLoad.Load(rpg, mainMenu, classes, races));
    buttonExit.addActionListener(e -> System.exit(0));
  }

  public void InitializeRaces() {
    // open Classes folder, read all files, create CharClass objects
    races.clear();
    for (File file: new File("src/CharacterCreator/Races").listFiles()) {
      CharRace r = new CharRace();
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        r.raceName = reader.readLine();
        r.raceDescription = reader.readLine();
        r.raceID = reader.readLine();
      } catch (FileNotFoundException e) {
        Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      } catch (IOException e) {
        Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      }
      races.add(r);
    }
  }

  public void InitializeClasses() {
    classes.clear();
    // open Classes folder, read all files, create CharClass objects
    for (File file: new File("src/CharacterCreator/Classes").listFiles()) {
      CharClass c = new CharClass();
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        c.className = reader.readLine();
        c.classDescription = reader.readLine();
        c.classID = reader.readLine();
      } catch (IOException e) {
        Logger.getLogger(RPG.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      }
      classes.add(c);
    }
  }

}