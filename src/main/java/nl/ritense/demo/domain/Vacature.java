package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vacature.
 */
@Entity
@Table(name = "vacature")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vacature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumgesloten")
    private LocalDate datumgesloten;

    @Column(name = "datumopengesteld")
    private String datumopengesteld;

    @Column(name = "deeltijd")
    private Boolean deeltijd;

    @Column(name = "extern")
    private Boolean extern;

    @Column(name = "intern")
    private Boolean intern;

    @Column(name = "vastedienst")
    private Boolean vastedienst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "uitgevoerddoorOpdrachtgevers",
            "uitgevoerddoorOpdrachtnemers",
            "dienstverbandconformfunctieDienstverbands",
            "vacaturebijfunctieVacatures",
        },
        allowSetters = true
    )
    private Functie vacaturebijfunctieFunctie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "opvacatureVacature")
    @JsonIgnoreProperties(
        value = { "opvacatureVacature", "solliciteertopfunctieSollicitant", "solliciteertWerknemer", "inkadervanSollicitatiegespreks" },
        allowSetters = true
    )
    private Set<Sollicitatie> opvacatureSollicitaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vacature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumgesloten() {
        return this.datumgesloten;
    }

    public Vacature datumgesloten(LocalDate datumgesloten) {
        this.setDatumgesloten(datumgesloten);
        return this;
    }

    public void setDatumgesloten(LocalDate datumgesloten) {
        this.datumgesloten = datumgesloten;
    }

    public String getDatumopengesteld() {
        return this.datumopengesteld;
    }

    public Vacature datumopengesteld(String datumopengesteld) {
        this.setDatumopengesteld(datumopengesteld);
        return this;
    }

    public void setDatumopengesteld(String datumopengesteld) {
        this.datumopengesteld = datumopengesteld;
    }

    public Boolean getDeeltijd() {
        return this.deeltijd;
    }

    public Vacature deeltijd(Boolean deeltijd) {
        this.setDeeltijd(deeltijd);
        return this;
    }

    public void setDeeltijd(Boolean deeltijd) {
        this.deeltijd = deeltijd;
    }

    public Boolean getExtern() {
        return this.extern;
    }

    public Vacature extern(Boolean extern) {
        this.setExtern(extern);
        return this;
    }

    public void setExtern(Boolean extern) {
        this.extern = extern;
    }

    public Boolean getIntern() {
        return this.intern;
    }

    public Vacature intern(Boolean intern) {
        this.setIntern(intern);
        return this;
    }

    public void setIntern(Boolean intern) {
        this.intern = intern;
    }

    public Boolean getVastedienst() {
        return this.vastedienst;
    }

    public Vacature vastedienst(Boolean vastedienst) {
        this.setVastedienst(vastedienst);
        return this;
    }

    public void setVastedienst(Boolean vastedienst) {
        this.vastedienst = vastedienst;
    }

    public Functie getVacaturebijfunctieFunctie() {
        return this.vacaturebijfunctieFunctie;
    }

    public void setVacaturebijfunctieFunctie(Functie functie) {
        this.vacaturebijfunctieFunctie = functie;
    }

    public Vacature vacaturebijfunctieFunctie(Functie functie) {
        this.setVacaturebijfunctieFunctie(functie);
        return this;
    }

    public Set<Sollicitatie> getOpvacatureSollicitaties() {
        return this.opvacatureSollicitaties;
    }

    public void setOpvacatureSollicitaties(Set<Sollicitatie> sollicitaties) {
        if (this.opvacatureSollicitaties != null) {
            this.opvacatureSollicitaties.forEach(i -> i.setOpvacatureVacature(null));
        }
        if (sollicitaties != null) {
            sollicitaties.forEach(i -> i.setOpvacatureVacature(this));
        }
        this.opvacatureSollicitaties = sollicitaties;
    }

    public Vacature opvacatureSollicitaties(Set<Sollicitatie> sollicitaties) {
        this.setOpvacatureSollicitaties(sollicitaties);
        return this;
    }

    public Vacature addOpvacatureSollicitatie(Sollicitatie sollicitatie) {
        this.opvacatureSollicitaties.add(sollicitatie);
        sollicitatie.setOpvacatureVacature(this);
        return this;
    }

    public Vacature removeOpvacatureSollicitatie(Sollicitatie sollicitatie) {
        this.opvacatureSollicitaties.remove(sollicitatie);
        sollicitatie.setOpvacatureVacature(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vacature)) {
            return false;
        }
        return getId() != null && getId().equals(((Vacature) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vacature{" +
            "id=" + getId() +
            ", datumgesloten='" + getDatumgesloten() + "'" +
            ", datumopengesteld='" + getDatumopengesteld() + "'" +
            ", deeltijd='" + getDeeltijd() + "'" +
            ", extern='" + getExtern() + "'" +
            ", intern='" + getIntern() + "'" +
            ", vastedienst='" + getVastedienst() + "'" +
            "}";
    }
}
