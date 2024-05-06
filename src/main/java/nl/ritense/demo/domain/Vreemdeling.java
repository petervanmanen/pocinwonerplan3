package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Vreemdeling.
 */
@Entity
@Table(name = "vreemdeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vreemdeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "sociaalreferent", length = 10)
    private String sociaalreferent;

    @Size(max = 10)
    @Column(name = "vnummer", length = 10)
    private String vnummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vreemdeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSociaalreferent() {
        return this.sociaalreferent;
    }

    public Vreemdeling sociaalreferent(String sociaalreferent) {
        this.setSociaalreferent(sociaalreferent);
        return this;
    }

    public void setSociaalreferent(String sociaalreferent) {
        this.sociaalreferent = sociaalreferent;
    }

    public String getVnummer() {
        return this.vnummer;
    }

    public Vreemdeling vnummer(String vnummer) {
        this.setVnummer(vnummer);
        return this;
    }

    public void setVnummer(String vnummer) {
        this.vnummer = vnummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vreemdeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Vreemdeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vreemdeling{" +
            "id=" + getId() +
            ", sociaalreferent='" + getSociaalreferent() + "'" +
            ", vnummer='" + getVnummer() + "'" +
            "}";
    }
}
