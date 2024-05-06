package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Inspectie.
 */
@Entity
@Table(name = "inspectie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inspectie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Column(name = "datumaanmaak")
    private LocalDate datumaanmaak;

    @Column(name = "datumgepland")
    private LocalDate datumgepland;

    @Column(name = "datuminspectie")
    private LocalDate datuminspectie;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Column(name = "gemuteerddoor")
    private String gemuteerddoor;

    @Column(name = "inspectietype")
    private String inspectietype;

    @Size(max = 20)
    @Column(name = "kenmerk", length = 20)
    private String kenmerk;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "betreftPand",
            "bestaatuitBouwdeels",
            "betreftInspecties",
            "heeftVerblijfsobject",
            "heeftPand",
            "heeftKostenplaats",
            "betreftWerkbons",
        },
        allowSetters = true
    )
    private Vastgoedobject betreftVastgoedobject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inspectie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Inspectie aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public LocalDate getDatumaanmaak() {
        return this.datumaanmaak;
    }

    public Inspectie datumaanmaak(LocalDate datumaanmaak) {
        this.setDatumaanmaak(datumaanmaak);
        return this;
    }

    public void setDatumaanmaak(LocalDate datumaanmaak) {
        this.datumaanmaak = datumaanmaak;
    }

    public LocalDate getDatumgepland() {
        return this.datumgepland;
    }

    public Inspectie datumgepland(LocalDate datumgepland) {
        this.setDatumgepland(datumgepland);
        return this;
    }

    public void setDatumgepland(LocalDate datumgepland) {
        this.datumgepland = datumgepland;
    }

    public LocalDate getDatuminspectie() {
        return this.datuminspectie;
    }

    public Inspectie datuminspectie(LocalDate datuminspectie) {
        this.setDatuminspectie(datuminspectie);
        return this;
    }

    public void setDatuminspectie(LocalDate datuminspectie) {
        this.datuminspectie = datuminspectie;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Inspectie datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public String getGemuteerddoor() {
        return this.gemuteerddoor;
    }

    public Inspectie gemuteerddoor(String gemuteerddoor) {
        this.setGemuteerddoor(gemuteerddoor);
        return this;
    }

    public void setGemuteerddoor(String gemuteerddoor) {
        this.gemuteerddoor = gemuteerddoor;
    }

    public String getInspectietype() {
        return this.inspectietype;
    }

    public Inspectie inspectietype(String inspectietype) {
        this.setInspectietype(inspectietype);
        return this;
    }

    public void setInspectietype(String inspectietype) {
        this.inspectietype = inspectietype;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Inspectie kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Inspectie omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Inspectie opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getStatus() {
        return this.status;
    }

    public Inspectie status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vastgoedobject getBetreftVastgoedobject() {
        return this.betreftVastgoedobject;
    }

    public void setBetreftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.betreftVastgoedobject = vastgoedobject;
    }

    public Inspectie betreftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.setBetreftVastgoedobject(vastgoedobject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspectie)) {
            return false;
        }
        return getId() != null && getId().equals(((Inspectie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inspectie{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", datumaanmaak='" + getDatumaanmaak() + "'" +
            ", datumgepland='" + getDatumgepland() + "'" +
            ", datuminspectie='" + getDatuminspectie() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", gemuteerddoor='" + getGemuteerddoor() + "'" +
            ", inspectietype='" + getInspectietype() + "'" +
            ", kenmerk='" + getKenmerk() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
