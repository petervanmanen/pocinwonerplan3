package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Rekeningnummer.
 */
@Entity
@Table(name = "rekeningnummer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rekeningnummer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bic")
    private String bic;

    @Column(name = "iban")
    private String iban;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rekeningnummer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBic() {
        return this.bic;
    }

    public Rekeningnummer bic(String bic) {
        this.setBic(bic);
        return this;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getIban() {
        return this.iban;
    }

    public Rekeningnummer iban(String iban) {
        this.setIban(iban);
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rekeningnummer)) {
            return false;
        }
        return getId() != null && getId().equals(((Rekeningnummer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rekeningnummer{" +
            "id=" + getId() +
            ", bic='" + getBic() + "'" +
            ", iban='" + getIban() + "'" +
            "}";
    }
}
