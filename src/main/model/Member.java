package model;

// A yoga club member with name, ID, email address, student/non-student
// Source referred: Teller example
public class Member {
    //fields:
    private String name;
    private static int nextMemberID = 100000;
    private int id;
    private String email;
    private boolean isStudent;

    //REQUIRES: Member name of non-empty length, email address of non-empty length
    //EFFECTS: creates a new member where the member name is set to memberName, email is set
    //         to memberEmail, ID is the next positive integer that is unique
    public Member(String memberName, String memberEmail, Boolean memberStudent) {
        name = memberName;
        id = nextMemberID++;
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
}
