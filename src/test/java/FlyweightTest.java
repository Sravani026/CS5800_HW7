import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class CharacterPropertiesTest {

    @Test
    void testCharacterProperties() {
        CharacterProperties properties = new CharacterProperties("Arial", "Red", 12);
        assertEquals("Arial", properties.getFont());
        assertEquals("Red", properties.getColor());
        assertEquals(12, properties.getSize());
    }
}

class CharacterTest {

    @Test
    void testCharacter() {
        CharacterProperties properties = new CharacterProperties("Arial", "Red", 12);
        Character character = new Character('A', properties);
        assertEquals('A', character.getCharacter());
        assertEquals(properties, character.getProperties());
    }
}

class FlyweightFactoryTest {

    @Test
    void testFlyweightFactory() {
        FlyweightFactory flyweightFactory = new FlyweightFactory();
        CharacterProperties properties1 = flyweightFactory.getFlyweight("Arial", "Red", 12);
        CharacterProperties properties2 = flyweightFactory.getFlyweight("Arial", "Red", 12);
        assertSame(properties1, properties2); // Check if the same object is returned for same properties
    }
}

class DocumentTest {

    @Test
    void testDocument() {
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

        assertEquals("HelloWorldCS5800", document.getDocumentString());

        Document newDocument = new Document();
        newDocument.load("document.txt", flyweightFactory);

        assertEquals(document.getCharacters().size(), newDocument.getCharacters().size());
        for (int i = 0; i < document.getCharacters().size(); i++) {
            Character originalChar = document.getCharacters().get(i);
            Character loadedChar = newDocument.getCharacters().get(i);
            assertEquals(originalChar.getCharacter(), loadedChar.getCharacter());
            assertEquals(originalChar.getProperties().getFont(), loadedChar.getProperties().getFont());
            assertEquals(originalChar.getProperties().getColor(), loadedChar.getProperties().getColor());
            assertEquals(originalChar.getProperties().getSize(), loadedChar.getProperties().getSize());
        }
    }
}
