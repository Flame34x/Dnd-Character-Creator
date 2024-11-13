public class PlayerCharacter {

    public String charName; // The name of the character
    public String charDescription; // A brief description of the character
    public CharClass charClass; // The class of the character
    public CharRace charRace; // The
    public int charLevel; // The level of the character
    public int strength; // The strength of the character
    public int dexterity; // The dexterity of the character
    public int constitution; // The constitution of the character
    public int intelligence; // The intelligence of the character
    public int wisdom; // The wisdom of the character
    public int charisma; // The charisma of the character
    public int hitPoints; // The hit points of the character
    public int armorClass; // The armor class of the character
    public String charID; // A unique identifier for the character
  
    public void SetName(String name) {
      charName = name;
    }
    public void SetLevel(int level) {
      charLevel = level;
    }
    public void SetDescription(String description) {
      charDescription = description;
    }
    public void SetClass(CharClass charClass) {
      this.charClass = charClass;
    }
    public void SetRace(CharRace charRace) {
      this.charRace = charRace;
    }
    public void SetStrength(int strength) {
      this.strength = strength;
    }
    public void SetDexterity(int dexterity) {
      this.dexterity = dexterity;
    }
    public void SetConstitution(int constitution) {
      this.constitution = constitution;
    }
    public void SetIntelligence(int intelligence) {
      this.intelligence = intelligence;
    }
    public void SetWisdom(int wisdom) {
      this.wisdom = wisdom;
    }
    public void SetCharisma(int charisma) {
      this.charisma = charisma;
    }
    public void SetHitPoints(int hitPoints) {
      this.hitPoints = hitPoints;
    }
    public void SetArmorClass(int armorClass) {
      this.armorClass = armorClass;
    }
  
    public String GetName() {
      return charName;
    }
    public int GetLevel() {
      return charLevel;
    }
    public String GetDescription() {
      return charDescription;
    }
    public int GetStrength() {
      return strength;
    }
    public int GetDexterity() {
      return dexterity;
    }
    public int GetConstitution() {
      return constitution;
    }
    public int GetIntelligence() {
      return intelligence;
    }
    public int GetWisdom() {
      return wisdom;
    }
    public int GetCharisma() {
      return charisma;
    }
    public int GetHitPoints() {
      return hitPoints;
    }
    public int GetArmorClass() {
      return armorClass;
    }
  
    public CharRace GetRace() {
      return charRace;
    }
  
    public CharClass GetClass() {
      return charClass;
    }
  
    public void WipeCharacter() {
      charName = "";
      charClass = null;
      charRace = null;
      charLevel = 0;
      strength = 0;
      dexterity = 0;
      constitution = 0;
      intelligence = 0;
      wisdom = 0;
      charisma = 0;
      hitPoints = 0;
      armorClass = 0;
      charID = "";
    }
  }