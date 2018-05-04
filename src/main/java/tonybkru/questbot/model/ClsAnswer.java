/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonybkru.questbot.model;

import tonybkru.classifier.model.Classifier;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "CLS_ANSWER", catalog = "QUEB", schema = "QUE")
@XmlRootElement
public class ClsAnswer extends Classifier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Lob
    @Column(name = "ANSWER_TEXT")
    private String answerText;
    @Lob
    @Column(name = "ANSWER_COMMENT")
    private String answerComment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnswer")
    private Collection<RegQuestAnswer> regQuestAnswerCollection;
    @JoinColumn(name = "ID_QUEST", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsQuest idQuest;

    public ClsAnswer() {
    }

    public ClsAnswer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerComment() {
        return answerComment;
    }

    public void setAnswerComment(String answerComment) {
        this.answerComment = answerComment;
    }

    @XmlTransient
    public Collection<RegQuestAnswer> getRegQuestAnswerCollection() {
        return regQuestAnswerCollection;
    }

    public void setRegQuestAnswerCollection(Collection<RegQuestAnswer> regQuestAnswerCollection) {
        this.regQuestAnswerCollection = regQuestAnswerCollection;
    }

    public ClsQuest getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(ClsQuest idQuest) {
        this.idQuest = idQuest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsAnswer)) {
            return false;
        }
        ClsAnswer other = (ClsAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.tonybkru.questbot.model.ClsAnswer[ id=" + id + " ]";
    }
    
}
