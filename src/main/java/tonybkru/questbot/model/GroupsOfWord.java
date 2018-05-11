package tonybkru.questbot.model;

import java.util.ArrayList;

public class GroupsOfWord {
    private ArrayList<String> one = new ArrayList();
    private ArrayList<String> two = new ArrayList<>();

    public GroupsOfWord() {
        one.add("cat");
        one.add("cat");
        one.add("cat");

        two.add("buy");
        two.add("bought");
        two.add("bought");
    }

    public Object[] getOneGroup () {
        return one.toArray();
    }

    public Object[] getGroup(String name) {
        if (name.equals("1")) {
            return one.toArray();
        } else if (name.equals("2")) {
            return two.toArray();
        } else {
            return null;
        }
    }
}
