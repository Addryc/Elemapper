package org.elephant.mapper;

import org.testng.annotations.Test;

public class RoomTest {

    @Test
    public void testGetType() throws Exception {
        String roomDesc = "You have arrived at something of a junction on Arcanist's Way. Ahead of you, the road continues towards the looming Tower of Ancients, while to your east a short walk takes you to what appears to be the entrance to the Sente House. Broad steps lead up to its entrance which appears to be open DAYCHANGE(despite it being night and there is nobody to be seen around|despite dawn only just having arrived|for the daily traffic of government|even though night is swiftly descending). Looking to your west there appears to be a park of some variety, with the road dwindling into a well trodden path through the grass, flanked on either side by well-tended trees.";
        System.out.println(roomDesc);
        System.out.println(Room.replaceMacros("TEST1", roomDesc));

        roomDesc = "You have entered the Portal Room for House Aggramar. It goes without saying that to be found in here would invite certain death, as while the Senate House is barely used, this is, nonetheless, clearly a private office for one of the rulers of Erindar. Against the north wall is a large frame made of blackened wood, the wall behind which seems seared and burnt as if a fire had raged against it. CLASSCHANGE(warmage:Clearly it is a portal of some variety, but not of a type familiar to you.|necromancer:Clearly this is a portal made by one of your necromantic bretheren, although it is currently inactive.) The Wolf's Head insignia is adorned here, gazing down at your from a large tapestry on the wall. Beyond the wooden frame, the room is spartan, without only a few tables and chairs set about the place. All seem to be in the process of decay, with the wood rotting in places and dissolving into a putrid smelling slurry in others.";
        System.out.println(roomDesc);
        System.out.println(Room.replaceMacros("TEST2", roomDesc));


        roomDesc = "Testing Alignment - ALIGNCHANGE(This is a good description|This is a neutral description|This is an evil description)";
        System.out.println(roomDesc);
        System.out.println(Room.replaceMacros("TEST3", roomDesc));

        roomDesc = "Testing ALL - Time of Day DAYCHANGE(A|B|C|D) - Alignment - ALIGNCHANGE(This is a good description|This is a neutral description|This is an evil description) - Random - RANDOMCHANGE(A|B|C) - Race - RACECHANGE(human:Human stuff|elf:Elf stuff)";
        System.out.println(roomDesc);
        System.out.println(Room.replaceMacros("TEST4", roomDesc));


    }
}

