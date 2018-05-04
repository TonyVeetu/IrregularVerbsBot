/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonybkru.questbot.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "VW_REG_LAST_QUEST_ANSWER", catalog = "QUEB", schema = "QUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwRegLastQuestAnswer.findAll", query = "SELECT v FROM VwRegLastQuestAnswer v")})
public class VwRegLastQuestAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    private BigInteger id;
    @Column(name = "ID_QUEST")
    private BigInteger idQuest;
    @Column(name = "ID_ANSWER")
    private BigInteger idAnswer;

    public VwRegLastQuestAnswer() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(BigInteger idQuest) {
        this.idQuest = idQuest;
    }

    public BigInteger getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(BigInteger idAnswer) {
        this.idAnswer = idAnswer;
    }
    
}
