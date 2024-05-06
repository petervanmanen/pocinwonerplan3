package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Kadastraalperceel.
 */
@Entity
@Table(name = "kadastraalperceel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kadastraalperceel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidingsoortgrootte")
    private String aanduidingsoortgrootte;

    @Column(name = "begrenzingperceel")
    private String begrenzingperceel;

    @Column(name = "grootteperceel")
    private String grootteperceel;

    @Column(name = "indicatiedeelperceel")
    private String indicatiedeelperceel;

    @Size(max = 20)
    @Column(name = "omschrijvingdeelperceel", length = 20)
    private String omschrijvingdeelperceel;

    @Column(name = "plaatscoordinatenperceel")
    private String plaatscoordinatenperceel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kadastraalperceel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidingsoortgrootte() {
        return this.aanduidingsoortgrootte;
    }

    public Kadastraalperceel aanduidingsoortgrootte(String aanduidingsoortgrootte) {
        this.setAanduidingsoortgrootte(aanduidingsoortgrootte);
        return this;
    }

    public void setAanduidingsoortgrootte(String aanduidingsoortgrootte) {
        this.aanduidingsoortgrootte = aanduidingsoortgrootte;
    }

    public String getBegrenzingperceel() {
        return this.begrenzingperceel;
    }

    public Kadastraalperceel begrenzingperceel(String begrenzingperceel) {
        this.setBegrenzingperceel(begrenzingperceel);
        return this;
    }

    public void setBegrenzingperceel(String begrenzingperceel) {
        this.begrenzingperceel = begrenzingperceel;
    }

    public String getGrootteperceel() {
        return this.grootteperceel;
    }

    public Kadastraalperceel grootteperceel(String grootteperceel) {
        this.setGrootteperceel(grootteperceel);
        return this;
    }

    public void setGrootteperceel(String grootteperceel) {
        this.grootteperceel = grootteperceel;
    }

    public String getIndicatiedeelperceel() {
        return this.indicatiedeelperceel;
    }

    public Kadastraalperceel indicatiedeelperceel(String indicatiedeelperceel) {
        this.setIndicatiedeelperceel(indicatiedeelperceel);
        return this;
    }

    public void setIndicatiedeelperceel(String indicatiedeelperceel) {
        this.indicatiedeelperceel = indicatiedeelperceel;
    }

    public String getOmschrijvingdeelperceel() {
        return this.omschrijvingdeelperceel;
    }

    public Kadastraalperceel omschrijvingdeelperceel(String omschrijvingdeelperceel) {
        this.setOmschrijvingdeelperceel(omschrijvingdeelperceel);
        return this;
    }

    public void setOmschrijvingdeelperceel(String omschrijvingdeelperceel) {
        this.omschrijvingdeelperceel = omschrijvingdeelperceel;
    }

    public String getPlaatscoordinatenperceel() {
        return this.plaatscoordinatenperceel;
    }

    public Kadastraalperceel plaatscoordinatenperceel(String plaatscoordinatenperceel) {
        this.setPlaatscoordinatenperceel(plaatscoordinatenperceel);
        return this;
    }

    public void setPlaatscoordinatenperceel(String plaatscoordinatenperceel) {
        this.plaatscoordinatenperceel = plaatscoordinatenperceel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kadastraalperceel)) {
            return false;
        }
        return getId() != null && getId().equals(((Kadastraalperceel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kadastraalperceel{" +
            "id=" + getId() +
            ", aanduidingsoortgrootte='" + getAanduidingsoortgrootte() + "'" +
            ", begrenzingperceel='" + getBegrenzingperceel() + "'" +
            ", grootteperceel='" + getGrootteperceel() + "'" +
            ", indicatiedeelperceel='" + getIndicatiedeelperceel() + "'" +
            ", omschrijvingdeelperceel='" + getOmschrijvingdeelperceel() + "'" +
            ", plaatscoordinatenperceel='" + getPlaatscoordinatenperceel() + "'" +
            "}";
    }
}
