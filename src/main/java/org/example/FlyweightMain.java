import java.io.*;
import java.util.*;

class CharacterProperties {
    String font;
    String color;
    int size;

    public CharacterProperties(String font, String color, int size) {
        this.font = font;
        this.color = color;
        this.size = size;
    }

    public String getFont() {
        return font;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
}

class Character {
    char character;
    CharacterProperties properties;

    public Character(char character, CharacterProperties properties) {
        this.character = character;
        this.properties = properties;
    }

    public char getCharacter() {
        return character;
    }

    public CharacterProperties getProperties() {
        return properties;
    }
}

class FlyweightFactory {
    private Map<String, CharacterProperties> flyweights = new HashMap<>();

    public CharacterProperties getFlyweight(String font, String color, int size) {
        String key = font + color + size;
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, new CharacterProperties(font, color, size));
        }
        return flyweights.get(key);
    }
}

class Document {
    private List<Character> document = new ArrayList<>();

    public void addCharacter(char character, CharacterProperties characterProperties) {
        document.add(new Character(character, characterProperties));
    }

    public void editCharacter(int index, char character, CharacterProperties characterProperties) {
        document.set(index, new Character(character, characterProperties));
        System.out.println("\nCharacter at index " + index + " edited!");
    }

    public String getDocumentString() {
        StringBuilder documentString = new StringBuilder();
        for (Character character : document) {
            documentString.append(character.getCharacter());
        }
        return documentString.toString();
    }

    public void save(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Character character : document) {
                CharacterProperties properties = character.getProperties();
                writer.write(character.getCharacter() + ", " + properties.getFont() + ", " + properties.getColor() + ", "
                        + properties.getSize() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error occurred in saveFile()!");
            e.printStackTrace();
        }
    }

    public void load(String filePath, FlyweightFactory flyweightFactory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            document.clear();
            while ((line = reader.readLine()) != null) {
                String[] splitter = line.split(",");
                if (splitter.length == 4) {
                    char characterValue = splitter[0].charAt(0);
                    String font = splitter[1].trim();
                    String color = splitter[2].trim();
                    int size = Integer.parseInt(splitter[3].trim());
                    addCharacter(characterValue, flyweightFactory.getFlyweight(font, color, size));
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred in loadFile()!");
            e.printStackTrace();
        }
    }

    public List<Character> getCharacters() {
        return document;
    }
}

public class FlyweightMain {
    public static void main(String[] args) throws IOException {
        FlyweightFactory flyweightFactory = new FlyweightFactory();
        Document document = new Document();

        document.addCharacter('H', flyweightFactory.getFlyweight("Arial", "Red", 12));
        document.addCharacter('e', flyweightFactory.getFlyweight("Calibri", "Blue", 14));
        document.addCharacter('l', flyweightFactory.getFlyweight("Verdana", "Black", 16));
        document.addCharacter('l', flyweightFactory.getFlyweight("Arial", "Red", 12));
        document.addCharacter('o', flyweightFactory.getFlyweight("Calibri", "Blue", 14));
        document.addCharacter('W', flyweightFactory.getFlyweight("Verdana", "Black", 16));
        document.addCharacter('o', flyweightFactory.getFlyweight("Arial", "Red", 12));
        document.addCharacter('r', flyweightFactory.getFlyweight("Calibri", "Blue", 14));
        document.addCharacter('l', flyweightFactory.getFlyweight("Verdana", "Black", 16));
        document.addCharacter('d', flyweightFactory.getFlyweight("Arial", "Red", 12));
        document.addCharacter('C', flyweightFactory.getFlyweight("Calibri", "Blue", 14));
        document.addCharacter('S', flyweightFactory.getFlyweight("Verdana", "Black", 16));
        document.addCharacter('5', flyweightFactory.getFlyweight("Arial", "Red", 12));
        document.addCharacter('8', flyweightFactory.getFlyweight("Calibri", "Blue", 14));
        document.addCharacter('0', flyweightFactory.getFlyweight("Verdana", "Black", 16));
        document.addCharacter('0', flyweightFactory.getFlyweight("Arial", "Red", 12));

        document.save("document.txt");

        Document newDocument = new Document();
        newDocument.load("document.txt", flyweightFactory);

        System.out.println("Characters in the loaded document:");
        for (Character character : newDocument.getCharacters()) {
            System.out.println("Char: " + character.getCharacter() + ", Font: " + character.getProperties().getFont() + ", Color: " + character.getProperties().getColor() + ", Size: " + character.getProperties().getSize());
        }
    }
}
