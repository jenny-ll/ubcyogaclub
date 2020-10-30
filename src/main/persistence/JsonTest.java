package persistence;

import model.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMember(String memberName, String memberEmail, Integer memberId, Member member) {
        assertEquals(memberName, member.getName());
        assertEquals(memberEmail, member.getEmail());
        assertEquals(memberId, member.getId());
    }
}
