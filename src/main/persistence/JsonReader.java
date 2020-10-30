package persistence;

import model.Member;
import model.MembershipList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read data from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MembershipList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMembershipList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses membership list from JSON object and returns it
    private MembershipList parseMembershipList(JSONObject jsonObject) {
        MembershipList ml = new MembershipList();
        addMembers(ml, jsonObject);
        return ml;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to membership list
    private void addMembers(MembershipList wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("members");
        for (Object json : jsonArray) {
            JSONObject nextMember = (JSONObject) json;
            addMember(wr, nextMember);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses member from JSON object and adds it to membership list
    private void addMember(MembershipList ml, JSONObject jsonObject) {
        String memberName = jsonObject.getString("memberName");
        String memberEmail = jsonObject.getString("memberEmail");
        Boolean memberStudent = jsonObject.getBoolean("memberStudent");
        Integer memberId = jsonObject.getInt("memberId");
        Member member = new Member(memberName, memberEmail, memberStudent);
        ml.addMember(member);
    }

}
