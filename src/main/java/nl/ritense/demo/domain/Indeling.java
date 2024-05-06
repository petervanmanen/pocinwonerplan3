package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Indeling.
 */
@Entity
@Table(name = "indeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Indeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "indelingsoort", length = 20)
    private String indelingsoort;

    @Column(name = "naam")
    private String naam;

    @Size(max = 20)
    @Column(name = "nummer", length = 20)
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenIndeling")
    @JsonIgnoreProperties(
        value = {
            "isonderdeelvanArchief",
            "heeftUitgever",
            "heeftVindplaats",
            "heeftOrdeningsschemas",
            "stamtuitPeriodes",
            "valtbinnenIndeling",
            "voorAanvraags",
        },
        allowSetters = true
    )
    private Set<Archiefstuk> valtbinnenArchiefstuks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenIndeling2")
    @JsonIgnoreProperties(
        value = { "valtbinnenArchiefstuks", "valtbinnenIndelings", "hoortbijArchief", "valtbinnenIndeling2" },
        allowSetters = true
    )
    private Set<Indeling> valtbinnenIndelings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftRechthebbende", "valtbinnenArchiefcategories", "stamtuitPeriodes", "isonderdeelvanArchiefstuks", "hoortbijIndelings",
        },
        allowSetters = true
    )
    private Archief hoortbijArchief;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "valtbinnenArchiefstuks", "valtbinnenIndelings", "hoortbijArchief", "valtbinnenIndeling2" },
        allowSetters = true
    )
    private Indeling valtbinnenIndeling2;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Indeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndelingsoort() {
        return this.indelingsoort;
    }

    public Indeling indelingsoort(String indelingsoort) {
        this.setIndelingsoort(indelingsoort);
        return this;
    }

    public void setIndelingsoort(String indelingsoort) {
        this.indelingsoort = indelingsoort;
    }

    public String getNaam() {
        return this.naam;
    }

    public Indeling naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Indeling nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Indeling omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Archiefstuk> getValtbinnenArchiefstuks() {
        return this.valtbinnenArchiefstuks;
    }

    public void setValtbinnenArchiefstuks(Set<Archiefstuk> archiefstuks) {
        if (this.valtbinnenArchiefstuks != null) {
            this.valtbinnenArchiefstuks.forEach(i -> i.setValtbinnenIndeling(null));
        }
        if (archiefstuks != null) {
            archiefstuks.forEach(i -> i.setValtbinnenIndeling(this));
        }
        this.valtbinnenArchiefstuks = archiefstuks;
    }

    public Indeling valtbinnenArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setValtbinnenArchiefstuks(archiefstuks);
        return this;
    }

    public Indeling addValtbinnenArchiefstuk(Archiefstuk archiefstuk) {
        this.valtbinnenArchiefstuks.add(archiefstuk);
        archiefstuk.setValtbinnenIndeling(this);
        return this;
    }

    public Indeling removeValtbinnenArchiefstuk(Archiefstuk archiefstuk) {
        this.valtbinnenArchiefstuks.remove(archiefstuk);
        archiefstuk.setValtbinnenIndeling(null);
        return this;
    }

    public Set<Indeling> getValtbinnenIndelings() {
        return this.valtbinnenIndelings;
    }

    public void setValtbinnenIndelings(Set<Indeling> indelings) {
        if (this.valtbinnenIndelings != null) {
            this.valtbinnenIndelings.forEach(i -> i.setValtbinnenIndeling2(null));
        }
        if (indelings != null) {
            indelings.forEach(i -> i.setValtbinnenIndeling2(this));
        }
        this.valtbinnenIndelings = indelings;
    }

    public Indeling valtbinnenIndelings(Set<Indeling> indelings) {
        this.setValtbinnenIndelings(indelings);
        return this;
    }

    public Indeling addValtbinnenIndeling(Indeling indeling) {
        this.valtbinnenIndelings.add(indeling);
        indeling.setValtbinnenIndeling2(this);
        return this;
    }

    public Indeling removeValtbinnenIndeling(Indeling indeling) {
        this.valtbinnenIndelings.remove(indeling);
        indeling.setValtbinnenIndeling2(null);
        return this;
    }

    public Archief getHoortbijArchief() {
        return this.hoortbijArchief;
    }

    public void setHoortbijArchief(Archief archief) {
        this.hoortbijArchief = archief;
    }

    public Indeling hoortbijArchief(Archief archief) {
        this.setHoortbijArchief(archief);
        return this;
    }

    public Indeling getValtbinnenIndeling2() {
        return this.valtbinnenIndeling2;
    }

    public void setValtbinnenIndeling2(Indeling indeling) {
        this.valtbinnenIndeling2 = indeling;
    }

    public Indeling valtbinnenIndeling2(Indeling indeling) {
        this.setValtbinnenIndeling2(indeling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Indeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Indeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Indeling{" +
            "id=" + getId() +
            ", indelingsoort='" + getIndelingsoort() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
