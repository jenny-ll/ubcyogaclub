package model;

import exception.InvalidEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Source: slightly referred to Library practice problem from A3

class MembershipListTest {

    private Member stuMemberJenny;
    private Member stuMemberKathy;
    private Member nonStuMemberAdam;
    private Member nonStuMemberHarry;
    private MembershipList testMemberList;

    @BeforeEach
    public void setUp() throws InvalidEmailException {
        stuMemberJenny = new Member("Jenny Liu",
                "jennyjn@students.cs.ubc.ca",true);
        stuMemberKathy = new Member("Kathy Penelope",
                "kathy@students.cs.ubc.ca",true);
        nonStuMemberAdam = new Member("Adam Jones", "adamjones@gmail.com",
                false);
        nonStuMemberHarry = new Member("Harry Potter", "hogwarts@gmail.com",
                false);
        testMemberList = new MembershipList();
    }

    @Test
    public void testSize() {
        testMemberList.addMember(stuMemberJenny);
        assertEquals(1,testMemberList.size());
    }

    @Test
    public void testAddOneMember() {
        int previousListSize = testMemberList.size();
        testMemberList.addMember(stuMemberJenny);
        assertEquals(previousListSize + 1, testMemberList.size());
    }

    @Test
    public void testAddMultipleMembers() {
        int previousListSize = testMemberList.size();
        testMemberList.addMember(stuMemberJenny);
        testMemberList.addMember(nonStuMemberAdam);
        assertEquals(previousListSize + 2, testMemberList.size());
    }
    
    @Test
    public void testShowStudentMembers() {
        testMemberList.addMember(stuMemberJenny);
        testMemberList.addMember(stuMemberKathy);
        testMemberList.addMember(nonStuMemberAdam);

        List<Member> studentList = new ArrayList<>();
        studentList.add(stuMemberJenny);
        studentList.add(stuMemberKathy);
        assertEquals(studentList, testMemberList.showStudentMembers());
    }

    @Test
    public void testShowNonStudentMembers() {
        testMemberList.addMember(stuMemberJenny);
        testMemberList.addMember(stuMemberKathy);
        testMemberList.addMember(nonStuMemberAdam);

        List<Member> nonStudentList = new ArrayList<>();
        nonStudentList.add(nonStuMemberAdam);
        assertEquals(nonStudentList, testMemberList.showNonStudentMembers());
    }

    @Test
    public void testDeleteIdSuccess() {
        testMemberList.addMember(stuMemberJenny);
        assertEquals(1, testMemberList.size());
        testMemberList.deleteMember(100033);
        assertEquals(0, testMemberList.size());
    }

    @Test
    public void testDeleteIdFail() {
        testMemberList.addMember(stuMemberJenny);
        assertEquals(1, testMemberList.size());
        testMemberList.deleteMember(100000);
        assertEquals(1, testMemberList.size());
    }

    @Test
    public void testFindMemberSuccess() {
        testMemberList.addMember(stuMemberJenny);
        assertEquals("Jenny Liu", testMemberList.findMember(100029));
    }

    @Test
    public void testFindMemberFail() {
        testMemberList.addMember(stuMemberJenny);
        assertEquals("Member not found, sorry.", testMemberList.findMember(100007));
    }

}