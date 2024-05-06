package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Leefgebied.
 */
@Entity
@Table(name = "leefgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leefgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scorebijleeggebiedLeefgebied")
    @JsonIgnoreProperties(value = { "scorebijleeggebiedLeefgebied", "hoogtescoreScoresoort", "heeftClient" }, allowSetters = true)
    private Set<Score> scorebijleeggebiedScores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leefgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Leefgebied naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Score> getScorebijleeggebiedScores() {
        return this.scorebijleeggebiedScores;
    }

    public void setScorebijleeggebiedScores(Set<Score> scores) {
        if (this.scorebijleeggebiedScores != null) {
            this.scorebijleeggebiedScores.forEach(i -> i.setScorebijleeggebiedLeefgebied(null));
        }
        if (scores != null) {
            scores.forEach(i -> i.setScorebijleeggebiedLeefgebied(this));
        }
        this.scorebijleeggebiedScores = scores;
    }

    public Leefgebied scorebijleeggebiedScores(Set<Score> scores) {
        this.setScorebijleeggebiedScores(scores);
        return this;
    }

    public Leefgebied addScorebijleeggebiedScore(Score score) {
        this.scorebijleeggebiedScores.add(score);
        score.setScorebijleeggebiedLeefgebied(this);
        return this;
    }

    public Leefgebied removeScorebijleeggebiedScore(Score score) {
        this.scorebijleeggebiedScores.remove(score);
        score.setScorebijleeggebiedLeefgebied(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leefgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Leefgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leefgebied{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
