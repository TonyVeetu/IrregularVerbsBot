/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonybkru.questbot.bot.state;

import tonybkru.questbot.model.ClsQuest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author altmf
 */
public class QuestEnumeration implements Enumeration<ClsQuest>{
    private List<ClsQuest> quests = new ArrayList<>();
    private Integer currentQuest = 0;
    
    public QuestEnumeration(List<ClsQuest> quests){
        this.quests.addAll(quests);
    }

    @Override
    public boolean hasMoreElements() {
        return currentQuest < quests.size();
    }

    @Override
    public ClsQuest nextElement() {
        ClsQuest q = null;
        if (hasMoreElements()){
            q = quests.get(currentQuest);
            currentQuest++;
        }
        return q;
    }
    
    public Integer getCurrentQuest(){
        return currentQuest;
    }
}
