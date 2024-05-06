package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verlengingzaak.
 */
@Entity
@Table(name = "verlengingzaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verlengingzaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "duurverlenging")
    private String duurverlenging;

    @Column(name = "redenverlenging")
    private String redenverlenging;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verlengingzaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDuurverlenging() {
        return this.duurverlenging;
    }

    public Verlengingzaak duurverlenging(String duurverlenging) {
        this.setDuurverlenging(duurverlenging);
        return this;
    }

    public void setDuurverlenging(String duurverlenging) {
        this.duurverlenging = duurverlenging;
    }

    public String getRedenverlenging() {
        return this.redenverlenging;
    }

    public Verlengingzaak redenverlenging(String redenverlenging) {
        this.setRedenverlenging(redenverlenging);
        return this;
    }

    public void setRedenverlenging(String redenverlenging) {
        this.redenverlenging = redenverlenging;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verlengingzaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Verlengingzaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verlengingzaak{" +
            "id=" + getId() +
            ", duurverlenging='" + getDuurverlenging() + "'" +
            ", redenverlenging='" + getRedenverlenging() + "'" +
            "}";
    }
}
