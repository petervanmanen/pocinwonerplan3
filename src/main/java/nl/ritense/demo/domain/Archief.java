package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Archief.
 */
@Entity
@Table(name = "archief")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Archief implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "archiefnummer", length = 20)
    private String archiefnummer;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "openbaarheidsbeperking")
    private String openbaarheidsbeperking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftArchiefs" }, allowSetters = true)
    private Rechthebbende heeftRechthebbende;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_archief__valtbinnen_archiefcategorie",
        joinColumns = @JoinColumn(name = "archief_id"),
        inverseJoinColumns = @JoinColumn(name = "valtbinnen_archiefcategorie_id")
    )
    @JsonIgnoreProperties(value = { "valtbinnenArchiefs" }, allowSetters = true)
    private Set<Archiefcategorie> valtbinnenArchiefcategories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_archief__stamtuit_periode",
        joinColumns = @JoinColumn(name = "archief_id"),
        inverseJoinColumns = @JoinColumn(name = "stamtuit_periode_id")
    )
    @JsonIgnoreProperties(value = { "stamtuitArchiefs", "stamtuitArchiefstuks", "binnenHoofdstuks" }, allowSetters = true)
    private Set<Periode> stamtuitPeriodes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isonderdeelvanArchief")
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
    private Set<Archiefstuk> isonderdeelvanArchiefstuks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoortbijArchief")
    @JsonIgnoreProperties(
        value = { "valtbinnenArchiefstuks", "valtbinnenIndelings", "hoortbijArchief", "valtbinnenIndeling2" },
        allowSetters = true
    )
    private Set<Indeling> hoortbijIndelings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Archief id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchiefnummer() {
        return this.archiefnummer;
    }

    public Archief archiefnummer(String archiefnummer) {
        this.setArchiefnummer(archiefnummer);
        return this;
    }

    public void setArchiefnummer(String archiefnummer) {
        this.archiefnummer = archiefnummer;
    }

    public String getNaam() {
        return this.naam;
    }

    public Archief naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Archief omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOpenbaarheidsbeperking() {
        return this.openbaarheidsbeperking;
    }

    public Archief openbaarheidsbeperking(String openbaarheidsbeperking) {
        this.setOpenbaarheidsbeperking(openbaarheidsbeperking);
        return this;
    }

    public void setOpenbaarheidsbeperking(String openbaarheidsbeperking) {
        this.openbaarheidsbeperking = openbaarheidsbeperking;
    }

    public Rechthebbende getHeeftRechthebbende() {
        return this.heeftRechthebbende;
    }

    public void setHeeftRechthebbende(Rechthebbende rechthebbende) {
        this.heeftRechthebbende = rechthebbende;
    }

    public Archief heeftRechthebbende(Rechthebbende rechthebbende) {
        this.setHeeftRechthebbende(rechthebbende);
        return this;
    }

    public Set<Archiefcategorie> getValtbinnenArchiefcategories() {
        return this.valtbinnenArchiefcategories;
    }

    public void setValtbinnenArchiefcategories(Set<Archiefcategorie> archiefcategories) {
        this.valtbinnenArchiefcategories = archiefcategories;
    }

    public Archief valtbinnenArchiefcategories(Set<Archiefcategorie> archiefcategories) {
        this.setValtbinnenArchiefcategories(archiefcategories);
        return this;
    }

    public Archief addValtbinnenArchiefcategorie(Archiefcategorie archiefcategorie) {
        this.valtbinnenArchiefcategories.add(archiefcategorie);
        return this;
    }

    public Archief removeValtbinnenArchiefcategorie(Archiefcategorie archiefcategorie) {
        this.valtbinnenArchiefcategories.remove(archiefcategorie);
        return this;
    }

    public Set<Periode> getStamtuitPeriodes() {
        return this.stamtuitPeriodes;
    }

    public void setStamtuitPeriodes(Set<Periode> periodes) {
        this.stamtuitPeriodes = periodes;
    }

    public Archief stamtuitPeriodes(Set<Periode> periodes) {
        this.setStamtuitPeriodes(periodes);
        return this;
    }

    public Archief addStamtuitPeriode(Periode periode) {
        this.stamtuitPeriodes.add(periode);
        return this;
    }

    public Archief removeStamtuitPeriode(Periode periode) {
        this.stamtuitPeriodes.remove(periode);
        return this;
    }

    public Set<Archiefstuk> getIsonderdeelvanArchiefstuks() {
        return this.isonderdeelvanArchiefstuks;
    }

    public void setIsonderdeelvanArchiefstuks(Set<Archiefstuk> archiefstuks) {
        if (this.isonderdeelvanArchiefstuks != null) {
            this.isonderdeelvanArchiefstuks.forEach(i -> i.setIsonderdeelvanArchief(null));
        }
        if (archiefstuks != null) {
            archiefstuks.forEach(i -> i.setIsonderdeelvanArchief(this));
        }
        this.isonderdeelvanArchiefstuks = archiefstuks;
    }

    public Archief isonderdeelvanArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setIsonderdeelvanArchiefstuks(archiefstuks);
        return this;
    }

    public Archief addIsonderdeelvanArchiefstuk(Archiefstuk archiefstuk) {
        this.isonderdeelvanArchiefstuks.add(archiefstuk);
        archiefstuk.setIsonderdeelvanArchief(this);
        return this;
    }

    public Archief removeIsonderdeelvanArchiefstuk(Archiefstuk archiefstuk) {
        this.isonderdeelvanArchiefstuks.remove(archiefstuk);
        archiefstuk.setIsonderdeelvanArchief(null);
        return this;
    }

    public Set<Indeling> getHoortbijIndelings() {
        return this.hoortbijIndelings;
    }

    public void setHoortbijIndelings(Set<Indeling> indelings) {
        if (this.hoortbijIndelings != null) {
            this.hoortbijIndelings.forEach(i -> i.setHoortbijArchief(null));
        }
        if (indelings != null) {
            indelings.forEach(i -> i.setHoortbijArchief(this));
        }
        this.hoortbijIndelings = indelings;
    }

    public Archief hoortbijIndelings(Set<Indeling> indelings) {
        this.setHoortbijIndelings(indelings);
        return this;
    }

    public Archief addHoortbijIndeling(Indeling indeling) {
        this.hoortbijIndelings.add(indeling);
        indeling.setHoortbijArchief(this);
        return this;
    }

    public Archief removeHoortbijIndeling(Indeling indeling) {
        this.hoortbijIndelings.remove(indeling);
        indeling.setHoortbijArchief(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archief)) {
            return false;
        }
        return getId() != null && getId().equals(((Archief) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Archief{" +
            "id=" + getId() +
            ", archiefnummer='" + getArchiefnummer() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", openbaarheidsbeperking='" + getOpenbaarheidsbeperking() + "'" +
            "}";
    }
}
