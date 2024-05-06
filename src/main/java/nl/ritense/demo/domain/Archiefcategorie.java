package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Archiefcategorie.
 */
@Entity
@Table(name = "archiefcategorie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Archiefcategorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Size(max = 20)
    @Column(name = "nummer", length = 20)
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenArchiefcategories")
    @JsonIgnoreProperties(
        value = {
            "heeftRechthebbende", "valtbinnenArchiefcategories", "stamtuitPeriodes", "isonderdeelvanArchiefstuks", "hoortbijIndelings",
        },
        allowSetters = true
    )
    private Set<Archief> valtbinnenArchiefs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Archiefcategorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Archiefcategorie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Archiefcategorie nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Archiefcategorie omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Archief> getValtbinnenArchiefs() {
        return this.valtbinnenArchiefs;
    }

    public void setValtbinnenArchiefs(Set<Archief> archiefs) {
        if (this.valtbinnenArchiefs != null) {
            this.valtbinnenArchiefs.forEach(i -> i.removeValtbinnenArchiefcategorie(this));
        }
        if (archiefs != null) {
            archiefs.forEach(i -> i.addValtbinnenArchiefcategorie(this));
        }
        this.valtbinnenArchiefs = archiefs;
    }

    public Archiefcategorie valtbinnenArchiefs(Set<Archief> archiefs) {
        this.setValtbinnenArchiefs(archiefs);
        return this;
    }

    public Archiefcategorie addValtbinnenArchief(Archief archief) {
        this.valtbinnenArchiefs.add(archief);
        archief.getValtbinnenArchiefcategories().add(this);
        return this;
    }

    public Archiefcategorie removeValtbinnenArchief(Archief archief) {
        this.valtbinnenArchiefs.remove(archief);
        archief.getValtbinnenArchiefcategories().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archiefcategorie)) {
            return false;
        }
        return getId() != null && getId().equals(((Archiefcategorie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Archiefcategorie{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
