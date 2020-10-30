package persistence;

import model.MembershipList;
import model.Member;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: JsonSerializationDemo. I used the general structure.

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MembershipList ml = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMembershipList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMembershipList");
        try {
            MembershipList ml = reader.read();
            assertEquals(0, ml.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMembershipList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMembershipList");
        try {
            MembershipList ml = reader.read();
            List<Member> members = ml.getMembers();
            assertEquals(4, members.size());
            checkMember("Jenny Liu", "jennyjn@students.cs.ubc.ca",
                    100000, members.get(0));
            checkMember("Harry", "harry@hogwarts.com",
                    100001, members.get(1));
            checkMember("Hermione", "hermione@hogwarts.com",
                    100002, members.get(2));
            checkMember("Hagrid", "hagrid@hogwarts.com",
                    100003, members.get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
