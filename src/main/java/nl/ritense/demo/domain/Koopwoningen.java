package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Koopwoningen.
 */
@Entity
@Table(name = "koopwoningen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Koopwoningen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "koopprijs", precision = 21, scale = 2)
    private BigDecimal koopprijs;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Koopwoningen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getKoopprijs() {
        return this.koopprijs;
    }

    public Koopwoningen koopprijs(BigDecimal koopprijs) {
        this.setKoopprijs(koopprijs);
        return this;
    }

    public void setKoopprijs(BigDecimal koopprijs) {
        this.koopprijs = koopprijs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Koopwoningen)) {
            return false;
        }
        return getId() != null && getId().equals(((Koopwoningen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Koopwoningen{" +
            "id=" + getId() +
            ", koopprijs=" + getKoopprijs() +
            "}";
    }
}
