package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Beperkingscore.
 */
@Entity
@Table(name = "beperkingscore")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beperkingscore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "commentaar")
    private String commentaar;

    @Column(name = "score")
    private String score;

    @Column(name = "wet")
    private String wet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "iseenBeperkingscores" }, allowSetters = true)
    private Beperkingscoresoort iseenBeperkingscoresoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "emptyBeperkingscores", "iseenBeperkingscategorie", "isgebaseerdopBeschikking" }, allowSetters = true)
    private Beperking emptyBeperking;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beperkingscore id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaar() {
        return this.commentaar;
    }

    public Beperkingscore commentaar(String commentaar) {
        this.setCommentaar(commentaar);
        return this;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public String getScore() {
        return this.score;
    }

    public Beperkingscore score(String score) {
        this.setScore(score);
        return this;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWet() {
        return this.wet;
    }

    public Beperkingscore wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Beperkingscoresoort getIseenBeperkingscoresoort() {
        return this.iseenBeperkingscoresoort;
    }

    public void setIseenBeperkingscoresoort(Beperkingscoresoort beperkingscoresoort) {
        this.iseenBeperkingscoresoort = beperkingscoresoort;
    }

    public Beperkingscore iseenBeperkingscoresoort(Beperkingscoresoort beperkingscoresoort) {
        this.setIseenBeperkingscoresoort(beperkingscoresoort);
        return this;
    }

    public Beperking getEmptyBeperking() {
        return this.emptyBeperking;
    }

    public void setEmptyBeperking(Beperking beperking) {
        this.emptyBeperking = beperking;
    }

    public Beperkingscore emptyBeperking(Beperking beperking) {
        this.setEmptyBeperking(beperking);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beperkingscore)) {
            return false;
        }
        return getId() != null && getId().equals(((Beperkingscore) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beperkingscore{" +
            "id=" + getId() +
            ", commentaar='" + getCommentaar() + "'" +
            ", score='" + getScore() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
