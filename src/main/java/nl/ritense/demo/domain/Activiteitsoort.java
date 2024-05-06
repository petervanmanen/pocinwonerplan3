package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activiteitsoort.
 */
@Entity
@Table(name = "activiteitsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activiteitsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vansoortActiviteitsoort")
    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    private Set<Activiteit> vansoortActiviteits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activiteitsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Activiteitsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Activiteitsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Activiteit> getVansoortActiviteits() {
        return this.vansoortActiviteits;
    }

    public void setVansoortActiviteits(Set<Activiteit> activiteits) {
        if (this.vansoortActiviteits != null) {
            this.vansoortActiviteits.forEach(i -> i.setVansoortActiviteitsoort(null));
        }
        if (activiteits != null) {
            activiteits.forEach(i -> i.setVansoortActiviteitsoort(this));
        }
        this.vansoortActiviteits = activiteits;
    }

    public Activiteitsoort vansoortActiviteits(Set<Activiteit> activiteits) {
        this.setVansoortActiviteits(activiteits);
        return this;
    }

    public Activiteitsoort addVansoortActiviteit(Activiteit activiteit) {
        this.vansoortActiviteits.add(activiteit);
        activiteit.setVansoortActiviteitsoort(this);
        return this;
    }

    public Activiteitsoort removeVansoortActiviteit(Activiteit activiteit) {
        this.vansoortActiviteits.remove(activiteit);
        activiteit.setVansoortActiviteitsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activiteitsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Activiteitsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activiteitsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
