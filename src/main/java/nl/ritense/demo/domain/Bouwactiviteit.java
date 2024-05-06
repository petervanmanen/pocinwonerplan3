package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Bouwactiviteit.
 */
@Entity
@Table(name = "bouwactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bouwactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bouwjaarklasse")
    private String bouwjaarklasse;

    @Column(name = "bouwjaartot")
    private String bouwjaartot;

    @Column(name = "bouwjaarvan")
    private String bouwjaarvan;

    @Size(max = 8)
    @Column(name = "indicatie", length = 8)
    private String indicatie;

    @Column(name = "omschrijving")
    private String omschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bouwactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBouwjaarklasse() {
        return this.bouwjaarklasse;
    }

    public Bouwactiviteit bouwjaarklasse(String bouwjaarklasse) {
        this.setBouwjaarklasse(bouwjaarklasse);
        return this;
    }

    public void setBouwjaarklasse(String bouwjaarklasse) {
        this.bouwjaarklasse = bouwjaarklasse;
    }

    public String getBouwjaartot() {
        return this.bouwjaartot;
    }

    public Bouwactiviteit bouwjaartot(String bouwjaartot) {
        this.setBouwjaartot(bouwjaartot);
        return this;
    }

    public void setBouwjaartot(String bouwjaartot) {
        this.bouwjaartot = bouwjaartot;
    }

    public String getBouwjaarvan() {
        return this.bouwjaarvan;
    }

    public Bouwactiviteit bouwjaarvan(String bouwjaarvan) {
        this.setBouwjaarvan(bouwjaarvan);
        return this;
    }

    public void setBouwjaarvan(String bouwjaarvan) {
        this.bouwjaarvan = bouwjaarvan;
    }

    public String getIndicatie() {
        return this.indicatie;
    }

    public Bouwactiviteit indicatie(String indicatie) {
        this.setIndicatie(indicatie);
        return this;
    }

    public void setIndicatie(String indicatie) {
        this.indicatie = indicatie;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bouwactiviteit omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bouwactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Bouwactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bouwactiviteit{" +
            "id=" + getId() +
            ", bouwjaarklasse='" + getBouwjaarklasse() + "'" +
            ", bouwjaartot='" + getBouwjaartot() + "'" +
            ", bouwjaarvan='" + getBouwjaarvan() + "'" +
            ", indicatie='" + getIndicatie() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
