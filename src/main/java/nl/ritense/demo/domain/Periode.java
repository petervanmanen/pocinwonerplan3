package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Periode.
 */
@Entity
@Table(name = "periode")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Periode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "stamtuitPeriodes")
    @JsonIgnoreProperties(
        value = {
            "heeftRechthebbende", "valtbinnenArchiefcategories", "stamtuitPeriodes", "isonderdeelvanArchiefstuks", "hoortbijIndelings",
        },
        allowSetters = true
    )
    private Set<Archief> stamtuitArchiefs = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "stamtuitPeriodes")
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
    private Set<Archiefstuk> stamtuitArchiefstuks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "binnenPeriodes")
    @JsonIgnoreProperties(value = { "heeftDoelstellings", "binnenPeriodes", "betreftBegrotingregels" }, allowSetters = true)
    private Set<Hoofdstuk> binnenHoofdstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Periode id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Periode datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Periode datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Periode omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Archief> getStamtuitArchiefs() {
        return this.stamtuitArchiefs;
    }

    public void setStamtuitArchiefs(Set<Archief> archiefs) {
        if (this.stamtuitArchiefs != null) {
            this.stamtuitArchiefs.forEach(i -> i.removeStamtuitPeriode(this));
        }
        if (archiefs != null) {
            archiefs.forEach(i -> i.addStamtuitPeriode(this));
        }
        this.stamtuitArchiefs = archiefs;
    }

    public Periode stamtuitArchiefs(Set<Archief> archiefs) {
        this.setStamtuitArchiefs(archiefs);
        return this;
    }

    public Periode addStamtuitArchief(Archief archief) {
        this.stamtuitArchiefs.add(archief);
        archief.getStamtuitPeriodes().add(this);
        return this;
    }

    public Periode removeStamtuitArchief(Archief archief) {
        this.stamtuitArchiefs.remove(archief);
        archief.getStamtuitPeriodes().remove(this);
        return this;
    }

    public Set<Archiefstuk> getStamtuitArchiefstuks() {
        return this.stamtuitArchiefstuks;
    }

    public void setStamtuitArchiefstuks(Set<Archiefstuk> archiefstuks) {
        if (this.stamtuitArchiefstuks != null) {
            this.stamtuitArchiefstuks.forEach(i -> i.removeStamtuitPeriode(this));
        }
        if (archiefstuks != null) {
            archiefstuks.forEach(i -> i.addStamtuitPeriode(this));
        }
        this.stamtuitArchiefstuks = archiefstuks;
    }

    public Periode stamtuitArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setStamtuitArchiefstuks(archiefstuks);
        return this;
    }

    public Periode addStamtuitArchiefstuk(Archiefstuk archiefstuk) {
        this.stamtuitArchiefstuks.add(archiefstuk);
        archiefstuk.getStamtuitPeriodes().add(this);
        return this;
    }

    public Periode removeStamtuitArchiefstuk(Archiefstuk archiefstuk) {
        this.stamtuitArchiefstuks.remove(archiefstuk);
        archiefstuk.getStamtuitPeriodes().remove(this);
        return this;
    }

    public Set<Hoofdstuk> getBinnenHoofdstuks() {
        return this.binnenHoofdstuks;
    }

    public void setBinnenHoofdstuks(Set<Hoofdstuk> hoofdstuks) {
        if (this.binnenHoofdstuks != null) {
            this.binnenHoofdstuks.forEach(i -> i.removeBinnenPeriode(this));
        }
        if (hoofdstuks != null) {
            hoofdstuks.forEach(i -> i.addBinnenPeriode(this));
        }
        this.binnenHoofdstuks = hoofdstuks;
    }

    public Periode binnenHoofdstuks(Set<Hoofdstuk> hoofdstuks) {
        this.setBinnenHoofdstuks(hoofdstuks);
        return this;
    }

    public Periode addBinnenHoofdstuk(Hoofdstuk hoofdstuk) {
        this.binnenHoofdstuks.add(hoofdstuk);
        hoofdstuk.getBinnenPeriodes().add(this);
        return this;
    }

    public Periode removeBinnenHoofdstuk(Hoofdstuk hoofdstuk) {
        this.binnenHoofdstuks.remove(hoofdstuk);
        hoofdstuk.getBinnenPeriodes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Periode)) {
            return false;
        }
        return getId() != null && getId().equals(((Periode) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Periode{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
