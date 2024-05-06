package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Scoresoort.
 */
@Entity
@Table(name = "scoresoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Scoresoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "niveau")
    private String niveau;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoogtescoreScoresoort")
    @JsonIgnoreProperties(value = { "scorebijleeggebiedLeefgebied", "hoogtescoreScoresoort", "heeftClient" }, allowSetters = true)
    private Set<Score> hoogtescoreScores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Scoresoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public Scoresoort niveau(String niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Set<Score> getHoogtescoreScores() {
        return this.hoogtescoreScores;
    }

    public void setHoogtescoreScores(Set<Score> scores) {
        if (this.hoogtescoreScores != null) {
            this.hoogtescoreScores.forEach(i -> i.setHoogtescoreScoresoort(null));
        }
        if (scores != null) {
            scores.forEach(i -> i.setHoogtescoreScoresoort(this));
        }
        this.hoogtescoreScores = scores;
    }

    public Scoresoort hoogtescoreScores(Set<Score> scores) {
        this.setHoogtescoreScores(scores);
        return this;
    }

    public Scoresoort addHoogtescoreScore(Score score) {
        this.hoogtescoreScores.add(score);
        score.setHoogtescoreScoresoort(this);
        return this;
    }

    public Scoresoort removeHoogtescoreScore(Score score) {
        this.hoogtescoreScores.remove(score);
        score.setHoogtescoreScoresoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scoresoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Scoresoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scoresoort{" +
            "id=" + getId() +
            ", niveau='" + getNiveau() + "'" +
            "}";
    }
}
