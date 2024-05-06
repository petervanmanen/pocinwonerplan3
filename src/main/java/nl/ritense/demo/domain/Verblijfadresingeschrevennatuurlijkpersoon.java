package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verblijfadresingeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "verblijfadresingeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verblijfadresingeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresherkomst")
    private String adresherkomst;

    @Column(name = "beschrijvinglocatie")
    private String beschrijvinglocatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verblijfadresingeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresherkomst() {
        return this.adresherkomst;
    }

    public Verblijfadresingeschrevennatuurlijkpersoon adresherkomst(String adresherkomst) {
        this.setAdresherkomst(adresherkomst);
        return this;
    }

    public void setAdresherkomst(String adresherkomst) {
        this.adresherkomst = adresherkomst;
    }

    public String getBeschrijvinglocatie() {
        return this.beschrijvinglocatie;
    }

    public Verblijfadresingeschrevennatuurlijkpersoon beschrijvinglocatie(String beschrijvinglocatie) {
        this.setBeschrijvinglocatie(beschrijvinglocatie);
        return this;
    }

    public void setBeschrijvinglocatie(String beschrijvinglocatie) {
        this.beschrijvinglocatie = beschrijvinglocatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verblijfadresingeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Verblijfadresingeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verblijfadresingeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", adresherkomst='" + getAdresherkomst() + "'" +
            ", beschrijvinglocatie='" + getBeschrijvinglocatie() + "'" +
            "}";
    }
}
