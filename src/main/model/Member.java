package model;

import exception.InvalidEmailException;
import org.json.JSONObject;
import org.omg.CORBA.DynAnyPackage.Invalid;
import persistence.Writable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A yoga club member with name, ID, email address, student/non-student
// CITATION: Teller example, as well as https://stackoverflow.com/questions/8204680/java-regex-email

public class Member implements Writable {
    //fields:
    private String name;
    private static final AtomicInteger nextMemberID = new AtomicInteger(100000);
    private int id;
    private String email;
    private boolean isStudent;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //REQUIRES: Member name of non-empty length, email address of non-empty length
    //EFFECTS: creates a new member where the member name is set to memberName, email is set
    //         to memberEmail, ID is the next positive integer that is unique
    public Member(String memberName, String memberEmail, Boolean memberStudent) throws InvalidEmailException {
        name = memberName;
        id = nextMemberID.incrementAndGet();
        isStudent = memberStudent;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(memberEmail);
        if (matcher.find()) {
            email = memberEmail;
        } else {
            throw new InvalidEmailException("Invalid Email.");
        }
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
