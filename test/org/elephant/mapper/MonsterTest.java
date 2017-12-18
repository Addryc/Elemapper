package org.elephant.mapper;

import org.testng.annotations.*;

import java.io.File;

import static org.testng.Assert.*;

public class MonsterTest {

    @BeforeTest
    public void setUp() throws Exception {
        EleConstants.init();
        File folder = new File("test/mon");
        folder.mkdir();
    }

    @AfterTest
    public void tearDown() throws Exception {
        File folder = new File("test/mon");
        folder.delete();

    }

    @Test
    public void testGetType() throws Exception {
        EleConstants.init();
        Monster monster = new Monster();
        monster.setFileName("Foo");
        monster.setName("MyMonster");;
        monster.setLongDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");;
        monster.setLevel(25);
        monster.setGender("male");
        monster.addId("Foo");
        monster.addId("Bar");
        monster.addId("Baz");
        monster.addEmote("%N flits around - %S darts between trees.");
        System.out.println(monster.template());
        monster.export(new File("test/mon"), "mon", null, File.separator);
        File mon = new File("test/mon"+File.separator+monster.getFileName()+".c");
        assertTrue(mon.exists());
        mon.delete();

        System.out.println(monster.writeToXML().getContent());
    }
}
