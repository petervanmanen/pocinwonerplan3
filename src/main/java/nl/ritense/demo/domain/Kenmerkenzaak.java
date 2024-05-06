package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Kenmerkenzaak.
 */
@Entity
@Table(name = "kenmerkenzaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kenmerkenzaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Column(name = "kenmerkbron")
    private String kenmerkbron;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kenmerkenzaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Kenmerkenzaak kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public String getKenmerkbron() {
        return this.kenmerkbron;
    }

    public Kenmerkenzaak kenmerkbron(String kenmerkbron) {
        this.setKenmerkbron(kenmerkbron);
        return this;
    }

    public void setKenmerkbron(String kenmerkbron) {
        this.kenmerkbron = kenmerkbron;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kenmerkenzaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Kenmerkenzaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kenmerkenzaak{" +
            "id=" + getId() +
            ", kenmerk='" + getKenmerk() + "'" +
            ", kenmerkbron='" + getKenmerkbron() + "'" +
            "}";
    }
}
