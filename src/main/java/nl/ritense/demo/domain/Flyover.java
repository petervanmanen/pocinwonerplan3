package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Flyover.
 */
@Entity
@Table(name = "flyover")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Flyover implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantaloverspanningen")
    private String aantaloverspanningen;

    @Column(name = "belastingklassenieuw")
    private String belastingklassenieuw;

    @Column(name = "belastingklasseoud")
    private String belastingklasseoud;

    @Column(name = "draagvermogen")
    private String draagvermogen;

    @Column(name = "maximaaltoelaatbaarvoertuiggewicht")
    private String maximaaltoelaatbaarvoertuiggewicht;

    @Column(name = "maximaleasbelasting")
    private String maximaleasbelasting;

    @Column(name = "maximaleoverspanning")
    private String maximaleoverspanning;

    @Size(max = 10)
    @Column(name = "overbruggingsobjectdoorrijopening", length = 10)
    private String overbruggingsobjectdoorrijopening;

    @Column(name = "type")
    private String type;

    @Column(name = "zwaarstevoertuig")
    private String zwaarstevoertuig;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Flyover id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantaloverspanningen() {
        return this.aantaloverspanningen;
    }

    public Flyover aantaloverspanningen(String aantaloverspanningen) {
        this.setAantaloverspanningen(aantaloverspanningen);
        return this;
    }

    public void setAantaloverspanningen(String aantaloverspanningen) {
        this.aantaloverspanningen = aantaloverspanningen;
    }

    public String getBelastingklassenieuw() {
        return this.belastingklassenieuw;
    }

    public Flyover belastingklassenieuw(String belastingklassenieuw) {
        this.setBelastingklassenieuw(belastingklassenieuw);
        return this;
    }

    public void setBelastingklassenieuw(String belastingklassenieuw) {
        this.belastingklassenieuw = belastingklassenieuw;
    }

    public String getBelastingklasseoud() {
        return this.belastingklasseoud;
    }

    public Flyover belastingklasseoud(String belastingklasseoud) {
        this.setBelastingklasseoud(belastingklasseoud);
        return this;
    }

    public void setBelastingklasseoud(String belastingklasseoud) {
        this.belastingklasseoud = belastingklasseoud;
    }

    public String getDraagvermogen() {
        return this.draagvermogen;
    }

    public Flyover draagvermogen(String draagvermogen) {
        this.setDraagvermogen(draagvermogen);
        return this;
    }

    public void setDraagvermogen(String draagvermogen) {
        this.draagvermogen = draagvermogen;
    }

    public String getMaximaaltoelaatbaarvoertuiggewicht() {
        return this.maximaaltoelaatbaarvoertuiggewicht;
    }

    public Flyover maximaaltoelaatbaarvoertuiggewicht(String maximaaltoelaatbaarvoertuiggewicht) {
        this.setMaximaaltoelaatbaarvoertuiggewicht(maximaaltoelaatbaarvoertuiggewicht);
        return this;
    }

    public void setMaximaaltoelaatbaarvoertuiggewicht(String maximaaltoelaatbaarvoertuiggewicht) {
        this.maximaaltoelaatbaarvoertuiggewicht = maximaaltoelaatbaarvoertuiggewicht;
    }

    public String getMaximaleasbelasting() {
        return this.maximaleasbelasting;
    }

    public Flyover maximaleasbelasting(String maximaleasbelasting) {
        this.setMaximaleasbelasting(maximaleasbelasting);
        return this;
    }

    public void setMaximaleasbelasting(String maximaleasbelasting) {
        this.maximaleasbelasting = maximaleasbelasting;
    }

    public String getMaximaleoverspanning() {
        return this.maximaleoverspanning;
    }

    public Flyover maximaleoverspanning(String maximaleoverspanning) {
        this.setMaximaleoverspanning(maximaleoverspanning);
        return this;
    }

    public void setMaximaleoverspanning(String maximaleoverspanning) {
        this.maximaleoverspanning = maximaleoverspanning;
    }

    public String getOverbruggingsobjectdoorrijopening() {
        return this.overbruggingsobjectdoorrijopening;
    }

    public Flyover overbruggingsobjectdoorrijopening(String overbruggingsobjectdoorrijopening) {
        this.setOverbruggingsobjectdoorrijopening(overbruggingsobjectdoorrijopening);
        return this;
    }

    public void setOverbruggingsobjectdoorrijopening(String overbruggingsobjectdoorrijopening) {
        this.overbruggingsobjectdoorrijopening = overbruggingsobjectdoorrijopening;
    }

    public String getType() {
        return this.type;
    }

    public Flyover type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZwaarstevoertuig() {
        return this.zwaarstevoertuig;
    }

    public Flyover zwaarstevoertuig(String zwaarstevoertuig) {
        this.setZwaarstevoertuig(zwaarstevoertuig);
        return this;
    }

    public void setZwaarstevoertuig(String zwaarstevoertuig) {
        this.zwaarstevoertuig = zwaarstevoertuig;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flyover)) {
            return false;
        }
        return getId() != null && getId().equals(((Flyover) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Flyover{" +
            "id=" + getId() +
            ", aantaloverspanningen='" + getAantaloverspanningen() + "'" +
            ", belastingklassenieuw='" + getBelastingklassenieuw() + "'" +
            ", belastingklasseoud='" + getBelastingklasseoud() + "'" +
            ", draagvermogen='" + getDraagvermogen() + "'" +
            ", maximaaltoelaatbaarvoertuiggewicht='" + getMaximaaltoelaatbaarvoertuiggewicht() + "'" +
            ", maximaleasbelasting='" + getMaximaleasbelasting() + "'" +
            ", maximaleoverspanning='" + getMaximaleoverspanning() + "'" +
            ", overbruggingsobjectdoorrijopening='" + getOverbruggingsobjectdoorrijopening() + "'" +
            ", type='" + getType() + "'" +
            ", zwaarstevoertuig='" + getZwaarstevoertuig() + "'" +
            "}";
    }
}
