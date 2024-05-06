package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Beperkingscoresoort.
 */
@Entity
@Table(name = "beperkingscoresoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beperkingscoresoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "vraag")
    private String vraag;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "iseenBeperkingscoresoort")
    @JsonIgnoreProperties(value = { "iseenBeperkingscoresoort", "emptyBeperking" }, allowSetters = true)
    private Set<Beperkingscore> iseenBeperkingscores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beperkingscoresoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVraag() {
        return this.vraag;
    }

    public Beperkingscoresoort vraag(String vraag) {
        this.setVraag(vraag);
        return this;
    }

    public void setVraag(String vraag) {
        this.vraag = vraag;
    }

    public String getWet() {
        return this.wet;
    }

    public Beperkingscoresoort wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Beperkingscore> getIseenBeperkingscores() {
        return this.iseenBeperkingscores;
    }

    public void setIseenBeperkingscores(Set<Beperkingscore> beperkingscores) {
        if (this.iseenBeperkingscores != null) {
            this.iseenBeperkingscores.forEach(i -> i.setIseenBeperkingscoresoort(null));
        }
        if (beperkingscores != null) {
            beperkingscores.forEach(i -> i.setIseenBeperkingscoresoort(this));
        }
        this.iseenBeperkingscores = beperkingscores;
    }

    public Beperkingscoresoort iseenBeperkingscores(Set<Beperkingscore> beperkingscores) {
        this.setIseenBeperkingscores(beperkingscores);
        return this;
    }

    public Beperkingscoresoort addIseenBeperkingscore(Beperkingscore beperkingscore) {
        this.iseenBeperkingscores.add(beperkingscore);
        beperkingscore.setIseenBeperkingscoresoort(this);
        return this;
    }

    public Beperkingscoresoort removeIseenBeperkingscore(Beperkingscore beperkingscore) {
        this.iseenBeperkingscores.remove(beperkingscore);
        beperkingscore.setIseenBeperkingscoresoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beperkingscoresoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Beperkingscoresoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beperkingscoresoort{" +
            "id=" + getId() +
            ", vraag='" + getVraag() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
