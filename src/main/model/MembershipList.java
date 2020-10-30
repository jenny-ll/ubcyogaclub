package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// CITATION: referred to general structure of LibrarySystemMaster practice problem,
//           as well as the model of JSON methods in the JsonSerializationDemo.

public class MembershipList implements Writable {

    // Fields
    private String memberName;
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

    // EFFECTS: returns an unmodifiable list of all members in list
    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("memberName", memberName);
        json.put("members", membersToJson());
        return json;
    }

    // EFFECTS: returns things in this membership list as a JSON array
    private JSONArray membersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Member m : members) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
