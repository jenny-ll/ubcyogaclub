package model;

import exception.NegativeIDException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.concurrent.atomic.AtomicInteger;

// A yoga club member with name, ID, email address, student/non-student
// CITATION: Teller example

public class Member implements Writable {
    //fields:
    private String name;
    private static final AtomicInteger nextMemberID = new AtomicInteger(100000);
    private int id;
    private String email;
    private boolean isStudent;

    //REQUIRES: Member name of non-empty length, email address of non-empty length
    //EFFECTS: creates a new member where the member name is set to memberName, email is set
    //         to memberEmail, ID is the next positive integer that is unique
    public Member(String memberName, String memberEmail, Boolean memberStudent) {
        name = memberName;
        id = nextMemberID.incrementAndGet();
        email = memberEmail;
        isStudent = memberStudent;
    }

    //getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isStudent() {
        return isStudent;
    }

    // MODIFIES: this
    // EFFECTS: set ID to integer i
    public void setId(Integer i) {
        this.id = i;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("memberName", name);
        json.put("memberEmail", email);
        json.put("memberStudent", isStudent);
        json.put("memberId", id);
        return json;
    }
}
