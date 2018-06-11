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
        one.add(new IrregularLine("Drink - drank - drunk\n"));
        one.add(new IrregularLine("Swim - swam - swum\n"));
        one.add(new IrregularLine("Sit - sat - sat\n"));
        one.add(new IrregularLine("Begin - began - begun\n"));
        one.add(new IrregularLine("Ring - rang - rung\n"));

        two.add(new IrregularLine("Buy - bought - bought\n"));
        two.add(new IrregularLine("Fight - fought - fought\n"));
        two.add(new IrregularLine("Think - thought - thought\n"));
        two.add(new IrregularLine("Bring - brought - brought\n"));
        two.add(new IrregularLine("Catch - caught - caught\n"));
        two.add(new IrregularLine("Teach - taught - taught\n"));

        three.add(new IrregularLine("Send - sent - sent\n"));
        three.add(new IrregularLine("Build - built - built\n"));
        three.add(new IrregularLine("Spend - spent - spent\n"));
        three.add(new IrregularLine("Bend - bent - bent\n"));
        three.add(new IrregularLine("Lend - lent - lent\n"));

        four.add(new IrregularLine("Know - knew - known\n"));
        four.add(new IrregularLine("Grow - grew - grown\n"));
        four.add(new IrregularLine("Throw - threw - thrown\n"));
        four.add(new IrregularLine("Throw - threw - thrown\n"));
        four.add(new IrregularLine("Show - showed - shown\n"));
        four.add(new IrregularLine("Draw - drew - drawn\n"));
        four.add(new IrregularLine("Blow - blew - blown\n"));
        four.add(new IrregularLine("Fly - flew - flown\n"));

        five.add(new IrregularLine("Meet - met - met\n"));
        five.add(new IrregularLine("Lead - led - led\n"));
        five.add(new IrregularLine("Feed - fed - fed\n"));
        five.add(new IrregularLine("Bleed - bled - bled\n"));
        five.add(new IrregularLine("but:  READ - READ - READ\n"));

        six.add(new IrregularLine("Sleep - slept - slept\n"));
        six.add(new IrregularLine("Keep - kept - kept\n"));
        six.add(new IrregularLine("Weep - wept - wept\n"));
        six.add(new IrregularLine("Sweep - swept - swept\n"));
        six.add(new IrregularLine("Kneel - knelt - knelt\n"));

        seven.add(new IrregularLine("Cut - cut - cut\n"));
        seven.add(new IrregularLine("Put - put - put\n"));
        seven.add(new IrregularLine("Let - let - let\n"));
        seven.add(new IrregularLine("Set - set - set\n"));
        seven.add(new IrregularLine("Hit - hit - hit\n"));
        seven.add(new IrregularLine("Rid - rid - rid\n"));
        seven.add(new IrregularLine("Cost - cost - cost\n"));
        seven.add(new IrregularLine("Shut - shut - shut\n"));
        seven.add(new IrregularLine("Burst - burst - burst\n"));

        eight.add(new IrregularLine("Wear - wore - worn\n"));
        eight.add(new IrregularLine("Tear - tore - torn\n"));
        eight.add(new IrregularLine("Swear - swore - sworn\n"));
        
        nine.add(new IrregularLine("Speak - spoke - spoken\n"));
        nine.add(new IrregularLine("Break - broke - broken\n"));
        nine.add(new IrregularLine("Steal - stole - stolen\n"));
        nine.add(new IrregularLine("Freeze - froze - frozen\n"));
        nine.add(new IrregularLine("Wake - woke - woken\n"));

        //nine.add(new IrregularLine("Beat - beat - beaten\n"));
        ten.add(new IrregularLine("_**__Crazy_Verbs__**_\n"));
        ten.add(new IrregularLine("go - went - gone\n"));
        ten.add(new IrregularLine("do - did - done\n"));
        ten.add(new IrregularLine("see - saw - seen\n"));
        ten.add(new IrregularLine("eat - ate - eaten\n"));
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
