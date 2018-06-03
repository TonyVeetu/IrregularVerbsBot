package tonybkru.questbot.model;

import java.util.ArrayList;

public class GroupsOfWord {
    private ArrayList<IrregularLine> one = new ArrayList();
    private ArrayList<IrregularLine> two = new ArrayList<>();

    public GroupsOfWord() {
        one.add(new IrregularLine("drink - drank - drunk\n"));
        one.add(new IrregularLine("swim - swam - swam\n"));
        one.add(new IrregularLine("sit - sat - sat\n"));
        one.add(new IrregularLine("begin - began - began\n"));
        one.add(new IrregularLine("ring - rang - rang\n"));

        two.add(new IrregularLine("buy - bought - bought\n"));
        two.add(new IrregularLine("fight - fought - fought\n"));
        two.add(new IrregularLine("think - thought - thought\n"));
        two.add(new IrregularLine("bring - brought - brought\n"));
    }

    public Object[] getOneGroup () {
        return one.toArray();
    }

    public ArrayList<IrregularLine> getGroup(String name) {
        if (name.equals("1")) {
            return one;
        } else if (name.equals("2")) {
            return two;
        } else {
            return null;
        }
    }
}
