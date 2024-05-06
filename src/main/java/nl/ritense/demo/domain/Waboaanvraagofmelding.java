package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Waboaanvraagofmelding.
 */
@Entity
@Table(name = "waboaanvraagofmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Waboaanvraagofmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bouwkosten", precision = 21, scale = 2)
    private BigDecimal bouwkosten;

    @Column(name = "olonummer")
    private String olonummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "projectkosten", precision = 21, scale = 2)
    private BigDecimal projectkosten;

    @Size(max = 100)
    @Column(name = "registratienummer", length = 100)
    private String registratienummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Waboaanvraagofmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBouwkosten() {
        return this.bouwkosten;
    }

    public Waboaanvraagofmelding bouwkosten(BigDecimal bouwkosten) {
        this.setBouwkosten(bouwkosten);
        return this;
    }

    public void setBouwkosten(BigDecimal bouwkosten) {
        this.bouwkosten = bouwkosten;
    }

    public String getOlonummer() {
        return this.olonummer;
    }

    public Waboaanvraagofmelding olonummer(String olonummer) {
        this.setOlonummer(olonummer);
        return this;
    }

    public void setOlonummer(String olonummer) {
        this.olonummer = olonummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Waboaanvraagofmelding omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public BigDecimal getProjectkosten() {
        return this.projectkosten;
    }

    public Waboaanvraagofmelding projectkosten(BigDecimal projectkosten) {
        this.setProjectkosten(projectkosten);
        return this;
    }

    public void setProjectkosten(BigDecimal projectkosten) {
        this.projectkosten = projectkosten;
    }

    public String getRegistratienummer() {
        return this.registratienummer;
    }

    public Waboaanvraagofmelding registratienummer(String registratienummer) {
        this.setRegistratienummer(registratienummer);
        return this;
    }

    public void setRegistratienummer(String registratienummer) {
        this.registratienummer = registratienummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Waboaanvraagofmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Waboaanvraagofmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Waboaanvraagofmelding{" +
            "id=" + getId() +
            ", bouwkosten=" + getBouwkosten() +
            ", olonummer='" + getOlonummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", projectkosten=" + getProjectkosten() +
            ", registratienummer='" + getRegistratienummer() + "'" +
            "}";
    }
}
