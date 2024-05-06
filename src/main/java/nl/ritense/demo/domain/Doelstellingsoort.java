package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doelstellingsoort.
 */
@Entity
@Table(name = "doelstellingsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doelstellingsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvansoortDoelstellingsoort")
    @JsonIgnoreProperties(
        value = { "heeftProducts", "isvansoortDoelstellingsoort", "heeftHoofdstuk", "betreftBegrotingregels" },
        allowSetters = true
    )
    private Set<Doelstelling> isvansoortDoelstellings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doelstellingsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Doelstellingsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Doelstellingsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Doelstelling> getIsvansoortDoelstellings() {
        return this.isvansoortDoelstellings;
    }

    public void setIsvansoortDoelstellings(Set<Doelstelling> doelstellings) {
        if (this.isvansoortDoelstellings != null) {
            this.isvansoortDoelstellings.forEach(i -> i.setIsvansoortDoelstellingsoort(null));
        }
        if (doelstellings != null) {
            doelstellings.forEach(i -> i.setIsvansoortDoelstellingsoort(this));
        }
        this.isvansoortDoelstellings = doelstellings;
    }

    public Doelstellingsoort isvansoortDoelstellings(Set<Doelstelling> doelstellings) {
        this.setIsvansoortDoelstellings(doelstellings);
        return this;
    }

    public Doelstellingsoort addIsvansoortDoelstelling(Doelstelling doelstelling) {
        this.isvansoortDoelstellings.add(doelstelling);
        doelstelling.setIsvansoortDoelstellingsoort(this);
        return this;
    }

    public Doelstellingsoort removeIsvansoortDoelstelling(Doelstelling doelstelling) {
        this.isvansoortDoelstellings.remove(doelstelling);
        doelstelling.setIsvansoortDoelstellingsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doelstellingsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Doelstellingsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doelstellingsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
