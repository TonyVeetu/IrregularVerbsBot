package tonybkru.questbot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupsOfWord {
    private Map<String, Integer> storeVerb = new HashMap();

    private ArrayList<IrregularLine> one = new ArrayList();
    private ArrayList<IrregularLine> two = new ArrayList<>();
    private ArrayList<IrregularLine> three = new ArrayList<>();
    private ArrayList<IrregularLine> four = new ArrayList<>();
    private ArrayList<IrregularLine> five = new ArrayList<>();
    private ArrayList<IrregularLine> six = new ArrayList<>();
    private ArrayList<IrregularLine> seven = new ArrayList<>();
    private ArrayList<IrregularLine> eight = new ArrayList<>();
    private ArrayList<IrregularLine> nine = new ArrayList<>();
    private ArrayList<IrregularLine> ten = new ArrayList<>();

    public GroupsOfWord() {
        initGroups();
        initStoreVerb();
    }

    private void initStoreVerb() {
        storeVerb.put("drink",1);
        storeVerb.put("swim",2);
        storeVerb.put("sit",3);
        storeVerb.put("begin",4);
        storeVerb.put("ring",5);
        storeVerb.put("buy",6);
        storeVerb.put("fight",7);
        storeVerb.put("bring",8);

        //TODO put all verb in this!

    }
    private void initGroups() {
        one.add(new IrregularLine("Drink - drank - ?\n"));
        one.add(new IrregularLine("Swim - swam - ?\n"));
        one.add(new IrregularLine("Sit - sat - ?\n"));
        one.add(new IrregularLine("Begin - began - ?\n"));
        one.add(new IrregularLine("Ring - rang - ?\n"));

        two.add(new IrregularLine("Buy - bought - ?\n"));
        two.add(new IrregularLine("Fight - fought - ?\n"));
        two.add(new IrregularLine("Think - thought - ?\n"));
        two.add(new IrregularLine("Bring - brought - ?\n"));

        three.add(new IrregularLine("Send - sent - ?\n"));
        three.add(new IrregularLine("Build - built - ?\n"));
        three.add(new IrregularLine("Spend - spent - ?\n"));

        four.add(new IrregularLine("Know - knew - ?\n"));
        four.add(new IrregularLine("Grow - grew - ?\n"));
        four.add(new IrregularLine("Throw - threw - ?\n"));

        five.add(new IrregularLine("Meet - met - ?\n"));
        five.add(new IrregularLine("Lead - led - ?\n"));
        five.add(new IrregularLine("Feed - fed - ?\n"));
        five.add(new IrregularLine("Bleed - bled - ?\n"));
        five.add(new IrregularLine("but  READ - READ - v\n"));

        six.add(new IrregularLine("Sleep - slept - ?\n"));
        six.add(new IrregularLine("Keep - kept - ?\n"));
        six.add(new IrregularLine("Weep - wept - ?\n"));
        six.add(new IrregularLine("Sweep - swept - ?\n"));

        seven.add(new IrregularLine("Cut - cut - ?\n"));
        seven.add(new IrregularLine("Put - put - ?\n"));
        seven.add(new IrregularLine("Let - let - ?\n"));
        seven.add(new IrregularLine("Set - set - ?\n"));
        seven.add(new IrregularLine("Hit - hit - ?\n"));
        seven.add(new IrregularLine("Rid - rid - ?\n"));
        seven.add(new IrregularLine("Cost - cost - ?\n"));

        eight.add(new IrregularLine("Wear - wore - ?\n"));
        eight.add(new IrregularLine("Tear - tore - ?\n"));
        eight.add(new IrregularLine("Swear - swore - ?\n"));

        nine.add(new IrregularLine("Speak - spoke - ?\n"));
        nine.add(new IrregularLine("Break - broke - ?\n"));
        nine.add(new IrregularLine("Steal - stole - ?\n"));

        ten.add(new IrregularLine("go - went - gone\n"));
        ten.add(new IrregularLine("do - did - done\n"));
        ten.add(new IrregularLine("see - saw - seen\n"));
        ten.add(new IrregularLine("eat - ate - ?\n"));
        //TODO make three worm!!
    }

    public ArrayList<IrregularLine> getGroup(String name) {
        if (name.equals("1")) {
            return one;
        } else if (name.equals("2")) {
            return two;
        } else if (name.equals("3")) {
            return three;
        } else if (name.equals("4")) {
            return four;
        } else if (name.equals("5")) {
            return five;
        } else if (name.equals("6")) {
            return six;
        } else if (name.equals("7")) {
            return seven;
        } else if (name.equals("8")) {
            return eight;
        } else if (name.equals("9")) {
            return nine;
        } else if (name.equals("10")) {
            return ten;
        } else {
            return null;
        }
    }

    public Integer getIdVerbFromStore(String verb) {
        return storeVerb.get(verb);
    }

    public static void main(String[] args) {
        GroupsOfWord groupsOfWord = new GroupsOfWord();
        System.out.println(groupsOfWord.getIdVerbFromStore("buy"));
    }

    public static void findVerb() {
        //TODO
    }
}
