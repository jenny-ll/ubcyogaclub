package model;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Source: slightly referred to Library practice problem from A3

public class MemberTest {

    private Member stuMember;
    private Member nonStuMember;
    private MembershipList testMemberList;

    @BeforeEach
    public void setUp() {
        stuMember = new Member("Jenny Liu",
                "jennyjn@students.cs.ubc.ca",true);
        nonStuMember = new Member("Adam Jones", "adamjones@gmail.com",
                false);
        testMemberList = new MembershipList();
    }

    @Test
    public void testGetters() {
        assertEquals("Jenny Liu", stuMember.getName());
        assertEquals("adamjones@gmail.com", nonStuMember.getEmail());
        assertEquals(true, stuMember.isStudent());
        assertEquals(100000, stuMember.getId());
        assertEquals(100001, nonStuMember.getId());
    }

    @Test
    public void testSetEmail() {
        stuMember.setEmail("jennyliu@gmail.com");
        assertEquals("jennyliu@gmail.com", stuMember.getEmail());
    }
}
