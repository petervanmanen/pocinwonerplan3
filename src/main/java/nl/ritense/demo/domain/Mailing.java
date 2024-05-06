package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Mailing.
 */
@Entity
@Table(name = "mailing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mailing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_mailing__versturenaan_museumrelatie",
        joinColumns = @JoinColumn(name = "mailing_id"),
        inverseJoinColumns = @JoinColumn(name = "versturenaan_museumrelatie_id")
    )
    @JsonIgnoreProperties(value = { "voorProgrammas", "valtbinnenDoelgroeps", "versturenaanMailings" }, allowSetters = true)
    private Set<Museumrelatie> versturenaanMuseumrelaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mailing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Mailing datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getNaam() {
        return this.naam;
    }

    public Mailing naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Mailing omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Museumrelatie> getVersturenaanMuseumrelaties() {
        return this.versturenaanMuseumrelaties;
    }

    public void setVersturenaanMuseumrelaties(Set<Museumrelatie> museumrelaties) {
        this.versturenaanMuseumrelaties = museumrelaties;
    }

    public Mailing versturenaanMuseumrelaties(Set<Museumrelatie> museumrelaties) {
        this.setVersturenaanMuseumrelaties(museumrelaties);
        return this;
    }

    public Mailing addVersturenaanMuseumrelatie(Museumrelatie museumrelatie) {
        this.versturenaanMuseumrelaties.add(museumrelatie);
        return this;
    }

    public Mailing removeVersturenaanMuseumrelatie(Museumrelatie museumrelatie) {
        this.versturenaanMuseumrelaties.remove(museumrelatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mailing)) {
            return false;
        }
        return getId() != null && getId().equals(((Mailing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mailing{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
