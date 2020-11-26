package model;

import java.util.List;

import exception.InvalidEmailException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

import static org.junit.jupiter.api.Assertions.*;

// Source: slightly referred to Library practice problem from A3

public class MemberTest {

    private Member stuMember;
    private Member nonStuMember;
    private MembershipList testMemberList;

    @BeforeEach
    public void setUp() {
        testMemberList = new MembershipList();
    }

    @Test
    public void testConstructWithValidEmail() {
        try {
            stuMember = new Member("Jenny Liu",
                    "jennyjn@students.ca",true);
        } catch (InvalidEmailException e) {
            fail();
        }
    }

    @Test
    public void testConstructWithInvalidEmail() {
        try {
            stuMember = new Member("Jenny Liu",
                    "whats up!",true);
            fail();
        } catch (InvalidEmailException e) {
            // expected
        }
    }

    @Test
    public void testGetters() {
        try {
            stuMember = new Member("Jenny Liu",
                    "jennyjn@students.ca",true);
            nonStuMember = new Member("Adam Jones", "adamjones@gmail.com",
                    false);
            assertEquals("Jenny Liu", stuMember.getName());
            assertEquals("adamjones@gmail.com", nonStuMember.getEmail());
            assertEquals(true, stuMember.isStudent());
            assertEquals(100003, stuMember.getId());
            assertEquals(100004, nonStuMember.getId());
        } catch (InvalidEmailException e) {
            fail();
        }
    }

    @Test
    public void testSetId() {
        try {
            stuMember = new Member("Jenny Liu",
                    "jennyjn@students.ca", true);
            stuMember.setId(200000);
            assertEquals(200000, stuMember.getId());
        } catch (InvalidEmailException e) {
            fail();
        }
    }
}
