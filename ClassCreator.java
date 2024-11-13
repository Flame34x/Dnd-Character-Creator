import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClassCreator {
  static RPG rpg;
  static boolean uniqueID = false;

  public static void CreateClass(RPG _rpg, List < CharClass > classes) {
    rpg = _rpg;
    JFrame classCreator = new JFrame("Create a Class");
    classCreator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    classCreator.setSize(300, 300);
    classCreator.setLayout(new FlowLayout());
    classCreator.setVisible(true);

    classCreator.add(new JLabel("Class Name:"));
    JTextField className = new JTextField(20);
    classCreator.add(className);

    classCreator.add(new JLabel("Class Description:"));
    JTextField classDescription = new JTextField(20);
    classCreator.add(classDescription);

    classCreator.add(new JLabel("Unique Class ID: "));
    classCreator.add(new JLabel("This must be unique! If not, it will cause errors."));
    JTextField classID = new JTextField(20);
    classCreator.add(classID);

    JButton buttonCreate = new JButton("Create Class");
    classCreator.add(buttonCreate);

    JLabel errorLabel = new JLabel("");  // Label to show error messages
    classCreator.add(errorLabel);

    buttonCreate.addActionListener(e -> {
      uniqueID = true; // Reset unique ID check

      // Verify the class ID is unique
      for (CharClass c: classes) {
        if (c.classID.equals(classID.getText())) {
          errorLabel.setText("Class ID is not unique! Please enter a unique Class ID.");
          uniqueID = false;
          break;
        }
      }

      if (uniqueID) {
        try {
          CompileClass(className.getText(), classDescription.getText(), classID.getText());
          classCreator.dispose();
        } catch (IOException ex) {
          ex.printStackTrace();
          errorLabel.setText("Error: Unable to save class.");
        }
      }
    });
  }

  private static void CompileClass(String name, String description, String id) throws IOException {
    CharClass c = new CharClass();
    c.className = name;
    c.classDescription = description;
    c.classID = id;
    rpg.classes.add(c);
    SaveClass(c);
  }

  private static void SaveClass(CharClass c) throws IOException {
    // Save the class to a file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Classes/" + c.classID + ".txt"))) {
      writer.write(c.className);
      writer.newLine();
      writer.write(c.classDescription);
      writer.newLine();
      writer.write(c.classID);
      writer.newLine();
    }

    JFrame notiFrame = new JFrame("Notification");
    notiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    notiFrame.setSize(300, 300);
    notiFrame.setLayout(new FlowLayout());
    notiFrame.setVisible(true);

    JLabel label = new JLabel("Class saved to " + c.classID + ".txt");
    notiFrame.add(label);

    JButton button = new JButton("Close");
    notiFrame.add(button);
    button.addActionListener(e -> {
      rpg.MainMenu(rpg);
      notiFrame.dispose();
    });
  }
}
