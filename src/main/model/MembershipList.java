package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// Source: referred to general structure of LibrarySystemMaster practice problem

public class MembershipList {

    // Fields
    private List<Member> members;

    // Constructor: creates a new empty list of memberships
    public MembershipList() {
        members = new ArrayList<>();
    }

    // EFFECTS: returns size of list
    public int size() {
        return members.size();
    }

    // REQUIRES: m != null
    // MODIFIES: this
    // EFFECTS: adds the Member m into the membership list
    public void addMember(Member m) {
        members.add(m);
    }

    // EFFECTS: show list of all student members
    public ArrayList<Member> showStudentMembers() {
        List<Member> students = new ArrayList<Member>();
        for (Member member : this.members) {
            if (member.isStudent()) {
                students.add(member);
            }
        }
        return (ArrayList<Member>) students;
    }

    // EFFECTS: show list of all non-student members
    public ArrayList<Member> showNonStudentMembers() {
        List<Member> nonStudents = new ArrayList<Member>();
        for (Member member : this.members) {
            if (!(member.isStudent())) {
                nonStudents.add(member);
            }
        }
        return (ArrayList<Member>) nonStudents;
    }

    // REQUIRES: a member ID
    // MODIFIES: this
    // EFFECTS: deletes the member corresponding to the ID from the membership list
    public void deleteMember(int id) {
        members.removeIf(member -> (member.getId() == id));
    }

    // REQUIRES: a member ID
    // MODIFIES: this
    // EFFECTS: gets the member name based on ID
    public String findMember(int id) {
        String correctName = "";
        for (Member member : this.members) {
            if (member.getId() == id) {
                correctName = member.getName();
            }
        }
        return correctName;
    }
}
