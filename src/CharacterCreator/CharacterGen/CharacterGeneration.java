package CharacterGen;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class CharacterGeneration {
  private static PlayerCharacter pc;
  static List < CharClass > classes = new ArrayList < > (); // Use a dynamic list instead of a fixed-size array
  static List < CharRace > races = new ArrayList < > ();
  private static RPG rpg;

  public static void InitializeGeneration(PlayerCharacter _pc, List < CharRace > _races, List < CharClass > _classes, RPG _rpg, JFrame _frame) {
    CharacterGeneration.pc = _pc;
    races = _races;
    classes = _classes;
    rpg = _rpg;

    Generate(_frame);
  }

  public static void Generate(JFrame frame) {
    JFrame frame2 = new JFrame("Character Generator");
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    pc = new PlayerCharacter();
    pc.WipeCharacter();

    frame2.setSize(300, 300);
    frame2.setLayout(new FlowLayout());
    frame.setVisible(false);
    frame2.setVisible(true);

    JLabel label = new JLabel("Enter your character's name: ");
    frame2.add(label);
    JTextArea textArea = new JTextArea(1, 20);
    frame2.add(textArea);
    JButton button = new JButton("Submit");
    frame2.add(button);

    button.addActionListener(e -> {
      pc.SetName(textArea.getText());
      Race(frame);
    });
  }

  public static void Race(JFrame frame) {
    JFrame frame3 = new JFrame("Character Generator");
    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame3.setSize(300, 300);
    frame3.setLayout(new BoxLayout(frame3.getContentPane(), BoxLayout.Y_AXIS));
    frame.setVisible(false);
    frame3.setVisible(true);

    JLabel nameLabel = new JLabel("Name: " + pc.GetName());
    frame3.add(nameLabel);
    JLabel label = new JLabel("Choose your race: ");
    frame3.add(label);

    for (CharRace r: races) {
      JButton raceButton = new JButton(r.raceName);
      frame3.add(raceButton);
      raceButton.addActionListener(e -> {
        pc.SetRace(r);
        Class(frame);
      });
    }
  }

  public static void Class(JFrame frame) {
    JFrame frame4 = new JFrame("Character Generator");
    frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame4.setSize(300, 300);
    frame4.setLayout(new BoxLayout(frame4.getContentPane(), BoxLayout.Y_AXIS));
    frame.setVisible(false);
    frame4.setVisible(true);

    JLabel nameLabel = new JLabel("Name: " + pc.GetName());
    frame4.add(nameLabel);
    JLabel raceLabel = new JLabel("Race: " + pc.GetRace().raceName);
    frame4.add(raceLabel);
    JLabel label = new JLabel("Choose your class: ");
    frame4.add(label);
    for (CharClass c: classes) {
      JButton classButton = new JButton(c.className);
      frame4.add(classButton);
      classButton.addActionListener(e -> {
        pc.SetClass(c);
        Stats(frame);
      });
    }

  }

  public static void Stats(JFrame frame) {
    int[] stats = StatGen();

    JFrame frame5 = new JFrame("Character Generator");
    frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame5.setSize(300, 300);
    frame5.setLayout(new BoxLayout(frame5.getContentPane(), BoxLayout.Y_AXIS));
    frame.setVisible(false);
    frame5.setVisible(true);

    JLabel nameLabel = new JLabel("Name: " + pc.GetName());
    frame5.add(nameLabel);
    JLabel raceLabel = new JLabel("Race :" + pc.GetRace().raceName);
    frame5.add(raceLabel);
    JLabel classLabel = new JLabel("Class: " + pc.GetClass().className);
    frame5.add(classLabel);
    pc.hitPoints = 10 + stats[2];
    JLabel hpLabel = new JLabel("HP: " + pc.hitPoints);
    frame5.add(hpLabel);
    pc.armorClass = 10 + stats[1];
    JLabel acLabel = new JLabel("AC: " + pc.armorClass);
    frame5.add(acLabel);
    JLabel strLabel = new JLabel("Strength: " + pc.GetStrength());
    frame5.add(strLabel);
    JLabel dexLabel = new JLabel("Dexterity: " + pc.GetDexterity());
    frame5.add(dexLabel);
    JLabel conLabel = new JLabel("Constitution: " + pc.GetConstitution());
    frame5.add(conLabel);
    JLabel intelLabel = new JLabel("Intelligence: " + pc.GetIntelligence());
    frame5.add(intelLabel);
    JLabel wisLabel = new JLabel("Wisdom: " + pc.GetWisdom());
    frame5.add(wisLabel);
    JLabel chaLabel = new JLabel("Charisma: " + pc.GetCharisma());
    frame5.add(chaLabel);

    JButton button = new JButton("Save Character");
    frame5.add(button);
    button.addActionListener(e -> SaveLoad.SaveMenu(pc));

    JButton button2 = new JButton("Return to Main Menu");
    frame5.add(button2);
    button2.addActionListener(e -> rpg.MainMenu(rpg));

  }

  public static int[] StatGen() {
    int[] stats = new int[6];
    for (int i = 0; i < 6; i++) {
      int[] rolls = new int[4];
      for (int j = 0; j < 4; j++) {
        rolls[j] = (int)(Math.random() * 6) + 1;
      }
      int min = rolls[0];
      for (int j = 1; j < 4; j++) {
        if (rolls[j] < min) {
          min = rolls[j];
        }
      }
      stats[i] = rolls[0] + rolls[1] + rolls[2] + rolls[3] - min;
    }

    pc.strength = stats[0];
    pc.dexterity = stats[1];
    pc.constitution = stats[2];
    pc.intelligence = stats[3];
    pc.wisdom = stats[4];
    pc.charisma = stats[5];
    return stats;
  }
}