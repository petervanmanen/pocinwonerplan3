package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Onbestemdadres.
 */
@Entity
@Table(name = "onbestemdadres")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Onbestemdadres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "huisletter")
    private String huisletter;

    @Column(name = "huisnummer")
    private String huisnummer;

    @Column(name = "huisnummertoevoeging")
    private String huisnummertoevoeging;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "straatnaam")
    private String straatnaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onbestemdadres id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHuisletter() {
        return this.huisletter;
    }

    public Onbestemdadres huisletter(String huisletter) {
        this.setHuisletter(huisletter);
        return this;
    }

    public void setHuisletter(String huisletter) {
        this.huisletter = huisletter;
    }

    public String getHuisnummer() {
        return this.huisnummer;
    }

    public Onbestemdadres huisnummer(String huisnummer) {
        this.setHuisnummer(huisnummer);
        return this;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisnummertoevoeging() {
        return this.huisnummertoevoeging;
    }

    public Onbestemdadres huisnummertoevoeging(String huisnummertoevoeging) {
        this.setHuisnummertoevoeging(huisnummertoevoeging);
        return this;
    }

    public void setHuisnummertoevoeging(String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Onbestemdadres postcode(String postcode) {
        this.setPostcode(postcode);
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStraatnaam() {
        return this.straatnaam;
    }

    public Onbestemdadres straatnaam(String straatnaam) {
        this.setStraatnaam(straatnaam);
        return this;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onbestemdadres)) {
            return false;
        }
        return getId() != null && getId().equals(((Onbestemdadres) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onbestemdadres{" +
            "id=" + getId() +
            ", huisletter='" + getHuisletter() + "'" +
            ", huisnummer='" + getHuisnummer() + "'" +
            ", huisnummertoevoeging='" + getHuisnummertoevoeging() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", straatnaam='" + getStraatnaam() + "'" +
            "}";
    }
}
