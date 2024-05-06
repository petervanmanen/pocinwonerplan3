package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sollicitatiegesprek.
 */
@Entity
@Table(name = "sollicitatiegesprek")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sollicitatiegesprek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangenomen")
    private Boolean aangenomen;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "volgendgesprek")
    private Boolean volgendgesprek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "opvacatureVacature", "solliciteertopfunctieSollicitant", "solliciteertWerknemer", "inkadervanSollicitatiegespreks" },
        allowSetters = true
    )
    private Sollicitatie inkadervanSollicitatie;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_sollicitatiegesprek__kandidaat_sollicitant",
        joinColumns = @JoinColumn(name = "sollicitatiegesprek_id"),
        inverseJoinColumns = @JoinColumn(name = "kandidaat_sollicitant_id")
    )
    @JsonIgnoreProperties(value = { "solliciteertopfunctieSollicitaties", "kandidaatSollicitatiegespreks" }, allowSetters = true)
    private Set<Sollicitant> kandidaatSollicitants = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_sollicitatiegesprek__doetsollicitatiegesprek_werknemer",
        joinColumns = @JoinColumn(name = "sollicitatiegesprek_id"),
        inverseJoinColumns = @JoinColumn(name = "doetsollicitatiegesprek_werknemer_id")
    )
    @JsonIgnoreProperties(
        value = {
            "beoordeeltdoorBeoordelings",
            "beoordelingvanBeoordelings",
            "dientinDeclaraties",
            "medewerkerheeftdienstverbandDienstverbands",
            "solliciteertSollicitaties",
            "heeftverlofVerlofs",
            "heeftverzuimVerzuims",
            "heeftondergaanGeweldsincident",
            "heeftRols",
            "doetsollicitatiegesprekSollicitatiegespreks",
        },
        allowSetters = true
    )
    private Set<Werknemer> doetsollicitatiegesprekWerknemers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sollicitatiegesprek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAangenomen() {
        return this.aangenomen;
    }

    public Sollicitatiegesprek aangenomen(Boolean aangenomen) {
        this.setAangenomen(aangenomen);
        return this;
    }

    public void setAangenomen(Boolean aangenomen) {
        this.aangenomen = aangenomen;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Sollicitatiegesprek datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Sollicitatiegesprek opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Boolean getVolgendgesprek() {
        return this.volgendgesprek;
    }

    public Sollicitatiegesprek volgendgesprek(Boolean volgendgesprek) {
        this.setVolgendgesprek(volgendgesprek);
        return this;
    }

    public void setVolgendgesprek(Boolean volgendgesprek) {
        this.volgendgesprek = volgendgesprek;
    }

    public Sollicitatie getInkadervanSollicitatie() {
        return this.inkadervanSollicitatie;
    }

    public void setInkadervanSollicitatie(Sollicitatie sollicitatie) {
        this.inkadervanSollicitatie = sollicitatie;
    }

    public Sollicitatiegesprek inkadervanSollicitatie(Sollicitatie sollicitatie) {
        this.setInkadervanSollicitatie(sollicitatie);
        return this;
    }

    public Set<Sollicitant> getKandidaatSollicitants() {
        return this.kandidaatSollicitants;
    }

    public void setKandidaatSollicitants(Set<Sollicitant> sollicitants) {
        this.kandidaatSollicitants = sollicitants;
    }

    public Sollicitatiegesprek kandidaatSollicitants(Set<Sollicitant> sollicitants) {
        this.setKandidaatSollicitants(sollicitants);
        return this;
    }

    public Sollicitatiegesprek addKandidaatSollicitant(Sollicitant sollicitant) {
        this.kandidaatSollicitants.add(sollicitant);
        return this;
    }

    public Sollicitatiegesprek removeKandidaatSollicitant(Sollicitant sollicitant) {
        this.kandidaatSollicitants.remove(sollicitant);
        return this;
    }

    public Set<Werknemer> getDoetsollicitatiegesprekWerknemers() {
        return this.doetsollicitatiegesprekWerknemers;
    }

    public void setDoetsollicitatiegesprekWerknemers(Set<Werknemer> werknemers) {
        this.doetsollicitatiegesprekWerknemers = werknemers;
    }

    public Sollicitatiegesprek doetsollicitatiegesprekWerknemers(Set<Werknemer> werknemers) {
        this.setDoetsollicitatiegesprekWerknemers(werknemers);
        return this;
    }

    public Sollicitatiegesprek addDoetsollicitatiegesprekWerknemer(Werknemer werknemer) {
        this.doetsollicitatiegesprekWerknemers.add(werknemer);
        return this;
    }

    public Sollicitatiegesprek removeDoetsollicitatiegesprekWerknemer(Werknemer werknemer) {
        this.doetsollicitatiegesprekWerknemers.remove(werknemer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sollicitatiegesprek)) {
            return false;
        }
        return getId() != null && getId().equals(((Sollicitatiegesprek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sollicitatiegesprek{" +
            "id=" + getId() +
            ", aangenomen='" + getAangenomen() + "'" +
            ", datum='" + getDatum() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", volgendgesprek='" + getVolgendgesprek() + "'" +
            "}";
    }
}
