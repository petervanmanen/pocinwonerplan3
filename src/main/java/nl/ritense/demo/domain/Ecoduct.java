package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Ecoduct.
 */
@Entity
@Table(name = "ecoduct")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ecoduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantaloverspanningen")
    private String aantaloverspanningen;

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

    public Ecoduct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantaloverspanningen() {
        return this.aantaloverspanningen;
    }

    public Ecoduct aantaloverspanningen(String aantaloverspanningen) {
        this.setAantaloverspanningen(aantaloverspanningen);
        return this;
    }

    public void setAantaloverspanningen(String aantaloverspanningen) {
        this.aantaloverspanningen = aantaloverspanningen;
    }

    public String getDraagvermogen() {
        return this.draagvermogen;
    }

    public Ecoduct draagvermogen(String draagvermogen) {
        this.setDraagvermogen(draagvermogen);
        return this;
    }

    public void setDraagvermogen(String draagvermogen) {
        this.draagvermogen = draagvermogen;
    }

    public String getMaximaaltoelaatbaarvoertuiggewicht() {
        return this.maximaaltoelaatbaarvoertuiggewicht;
    }

    public Ecoduct maximaaltoelaatbaarvoertuiggewicht(String maximaaltoelaatbaarvoertuiggewicht) {
        this.setMaximaaltoelaatbaarvoertuiggewicht(maximaaltoelaatbaarvoertuiggewicht);
        return this;
    }

    public void setMaximaaltoelaatbaarvoertuiggewicht(String maximaaltoelaatbaarvoertuiggewicht) {
        this.maximaaltoelaatbaarvoertuiggewicht = maximaaltoelaatbaarvoertuiggewicht;
    }

    public String getMaximaleasbelasting() {
        return this.maximaleasbelasting;
    }

    public Ecoduct maximaleasbelasting(String maximaleasbelasting) {
        this.setMaximaleasbelasting(maximaleasbelasting);
        return this;
    }

    public void setMaximaleasbelasting(String maximaleasbelasting) {
        this.maximaleasbelasting = maximaleasbelasting;
    }

    public String getMaximaleoverspanning() {
        return this.maximaleoverspanning;
    }

    public Ecoduct maximaleoverspanning(String maximaleoverspanning) {
        this.setMaximaleoverspanning(maximaleoverspanning);
        return this;
    }

    public void setMaximaleoverspanning(String maximaleoverspanning) {
        this.maximaleoverspanning = maximaleoverspanning;
    }

    public String getOverbruggingsobjectdoorrijopening() {
        return this.overbruggingsobjectdoorrijopening;
    }

    public Ecoduct overbruggingsobjectdoorrijopening(String overbruggingsobjectdoorrijopening) {
        this.setOverbruggingsobjectdoorrijopening(overbruggingsobjectdoorrijopening);
        return this;
    }

    public void setOverbruggingsobjectdoorrijopening(String overbruggingsobjectdoorrijopening) {
        this.overbruggingsobjectdoorrijopening = overbruggingsobjectdoorrijopening;
    }

    public String getType() {
        return this.type;
    }

    public Ecoduct type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZwaarstevoertuig() {
        return this.zwaarstevoertuig;
    }

    public Ecoduct zwaarstevoertuig(String zwaarstevoertuig) {
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
        if (!(o instanceof Ecoduct)) {
            return false;
        }
        return getId() != null && getId().equals(((Ecoduct) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecoduct{" +
            "id=" + getId() +
            ", aantaloverspanningen='" + getAantaloverspanningen() + "'" +
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
