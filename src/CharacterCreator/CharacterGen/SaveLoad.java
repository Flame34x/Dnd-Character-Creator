package CharacterGen;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class SaveLoad {
  static PlayerCharacter pc;
  static List < CharClass > classes = new ArrayList < > (); // Use a dynamic list instead of a fixed-size array
  static List < CharRace > races = new ArrayList < > ();
  public static void SaveMenu(PlayerCharacter _pc) {
    pc = _pc;
    JFrame frameSave = new JFrame("Character Generator");
    frameSave.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frameSave.setSize(300, 300);
    frameSave.setLayout(new BoxLayout(frameSave.getContentPane(), BoxLayout.Y_AXIS));

    frameSave.setVisible(true);

    JTextArea textArea = new JTextArea(1, 20);
    frameSave.add(textArea);

    // Browse button
    JButton button2 = new JButton("Browse");
    frameSave.add(button2);

    // Confirm button
    JButton button3 = new JButton("Confirm");
    frameSave.add(button3);

    // Cancel button
    JButton button4 = new JButton("Cancel");
    frameSave.add(button4);

    // Browse button action listener
    button2.addActionListener(e -> {
      // Open file chooser to select a directory
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Select Save Directory");
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only allow directory selection

      int result = fileChooser.showOpenDialog(frameSave);
      if (result == JFileChooser.APPROVE_OPTION) {
        // Get the selected directory
        File selectedDirectory = fileChooser.getSelectedFile();
        String directoryPath = selectedDirectory.getAbsolutePath();

        // Update the text area with the selected directory path
        textArea.setText(directoryPath);
      }
    });

    // Confirm button action listener
    button3.addActionListener(e -> {
      String directoryPath = textArea.getText();
      if (!directoryPath.isEmpty()) {
        // You can use directoryPath to save the file
        System.out.println("Selected directory to save: " + directoryPath);
        // Save logic here...
      } else {
        JOptionPane.showMessageDialog(frameSave, "Please select a directory first.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    button3.addActionListener(e -> {
      try {
        Save(textArea.getText());
      } catch (IOException ex) {
        Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(frameSave, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    // Cancel button action listener
    button4.addActionListener(e -> frameSave.setVisible(false));

  }

  public static void Save(String directory) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/" + pc.charName + ".txt"))) {
      writer.write(pc.GetName());
      writer.newLine();
      writer.write(String.valueOf(pc.GetRace().raceID)); // Convert enum to string
      writer.newLine();
      writer.write(String.valueOf(pc.GetClass().classID)); // Convert enum to string
      writer.newLine();
      writer.write(String.valueOf(pc.strength)); // Convert integers to strings
      writer.newLine();
      writer.write(String.valueOf(pc.dexterity));
      writer.newLine();
      writer.write(String.valueOf(pc.constitution));
      writer.newLine();
      writer.write(String.valueOf(pc.intelligence));
      writer.newLine();
      writer.write(String.valueOf(pc.wisdom));
      writer.newLine();
      writer.write(String.valueOf(pc.charisma));
      writer.newLine();
      writer.write(String.valueOf(pc.armorClass));
      writer.newLine();
      writer.write(String.valueOf(pc.hitPoints));
      writer.newLine();
      // Removed erroneous logging lines
    }

    JFrame frame6 = new JFrame("Notification");
    frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame6.setSize(300, 300);
    frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
    frame6.setVisible(true);

    JLabel label = new JLabel("Character saved to " + pc.GetName() + ".txt");
    frame6.add(label);

    JButton button = new JButton("Close");
    frame6.add(button);
    button.addActionListener(e -> frame6.setVisible(false));
  }

  public static void Load(RPG rpg, JFrame frame, List < CharClass > classes, List < CharRace > races) {
    // Open file chooser to select the file to load from
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select Character File");

    pc = new PlayerCharacter();
    pc.WipeCharacter();

    // Show the Open Dialog and get the result
    int result = fileChooser.showOpenDialog(frame);
    if (result != JFileChooser.APPROVE_OPTION) {
      JOptionPane.showMessageDialog(frame, "No file selected. Load canceled.");
      return; // Exit the method if no file is selected
    }

    // Get the selected file
    File selectedFile = fileChooser.getSelectedFile();
    String filePath = selectedFile.getAbsolutePath();

    // Create new JFrame to display character data
    JFrame frame7 = new JFrame("Character Generator");
    frame7.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame7.setSize(300, 300);
    frame7.setLayout(new BoxLayout(frame7.getContentPane(), BoxLayout.Y_AXIS));
    frame.setVisible(false);
    frame7.setVisible(true);

    // Get character from file
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      // Read each line and assign it to the corresponding attribute
      pc.SetName(reader.readLine());

      // Convert the string to the enum value for cRace
      String rIDToFind = reader.readLine();
      for (CharRace r: races) {
        if (r.raceID.equals(String.valueOf(rIDToFind))) {
          pc.SetRace(r);
          System.out.println(r.raceName);
          break;
        } else {
          throw new IllegalArgumentException();
        }
      }

      // Convert the string to the enum value for cclass
      String IDToFind = reader.readLine(); // Reads class ID
      for (CharClass c: classes) {
        if (c.classID.equals(String.valueOf(IDToFind))) {
          pc.SetClass(c);
          break;
        } else {
          throw new IllegalArgumentException("Invalid class ID in file.");
        }
      }

      // Read and convert integer cvalues
      pc.SetStrength(Integer.parseInt(reader.readLine()));
      pc.SetDexterity(Integer.parseInt(reader.readLine()));
      pc.SetConstitution(Integer.parseInt(reader.readLine()));
      pc.SetIntelligence(Integer.parseInt(reader.readLine()));
      pc.SetWisdom(Integer.parseInt(reader.readLine()));
      pc.SetCharisma(Integer.parseInt(reader.readLine()));
      pc.SetArmorClass(Integer.parseInt(reader.readLine()));
      pc.SetHitPoints(Integer.parseInt(reader.readLine()));

    } catch (IOException e) {
      Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, e);
    } catch (NumberFormatException e) {
      System.out.println("Error parsing integer values.");
      Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, e);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid enum value in file.");
      Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, "Invalid enum value in file.", e);
    }

    // Display character data in labels
    JLabel nameLabel = new JLabel("Name: " + pc.GetName());
    frame7.add(nameLabel);
    JLabel raceLabel = new JLabel("Race: " + pc.GetRace().raceName);
    frame7.add(raceLabel);
    JLabel classLabel = new JLabel("Class: " + pc.GetClass().className);
    frame7.add(classLabel);
    JLabel hpLabel = new JLabel("HP: " + pc.GetHitPoints());
    frame7.add(hpLabel);
    JLabel acLabel = new JLabel("AC: " + pc.GetArmorClass());
    frame7.add(acLabel);
    JLabel strLabel = new JLabel("Strength: " + pc.GetStrength());
    frame7.add(strLabel);
    JLabel dexLabel = new JLabel("Dexterity: " + pc.GetDexterity());
    frame7.add(dexLabel);
    JLabel conLabel = new JLabel("Constitution: " + pc.GetConstitution());
    frame7.add(conLabel);
    JLabel intelLabel = new JLabel("Intelligence: " + pc.GetIntelligence());
    frame7.add(intelLabel);
    JLabel wisLabel = new JLabel("Wisdom: " + pc.GetWisdom());
    frame7.add(wisLabel);
    JLabel chaLabel = new JLabel("Charisma: " + pc.GetCharisma());
    frame7.add(chaLabel);

    // Button to return to the main menu
    JButton button = new JButton("Return to Main Menu");
    frame7.add(button);

    // Action for "Return to Main Menu" button
    button.addActionListener(e -> {
      frame7.dispose(); // Close the current character display frame
      rpg.MainMenu(rpg); // Call main method to return to the main menu
    });
  }
}