package persistence;

import model.Member;
import model.MembershipList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CITATION: JsonSerializationDemo. I used the general structure and message texts.

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MembershipList ml = new MembershipList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileNameYIKES!!!!.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMembershipList() {
        try {
            MembershipList ml = new MembershipList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMembershipList.json");
            writer.open();
            writer.write(ml);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMembershipList.json");
            ml = reader.read();
            assertEquals(0, ml.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMembershipList() {
        try {
            MembershipList ml = new MembershipList();
            ml.addMember(new Member("Spiderman", "spiderman@avengers.com",true));
            ml.addMember(new Member("Ironman", "ironman@avengers.com",false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMembershipList.json");
            writer.open();
            writer.write(ml);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMembershipList.json");
            ml = reader.read();
            List<Member> members = ml.getMembers();
            checkMember("Spiderman", "spiderman@avengers.com",100000,members.get(0));
            checkMember("Ironman", "ironman@avengers.com",100001,members.get(1));
            assertEquals(2, members.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
