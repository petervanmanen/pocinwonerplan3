package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Combibon.
 */
@Entity
@Table(name = "combibon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Combibon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sanctie")
    private String sanctie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Combibon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSanctie() {
        return this.sanctie;
    }

    public Combibon sanctie(String sanctie) {
        this.setSanctie(sanctie);
        return this;
    }

    public void setSanctie(String sanctie) {
        this.sanctie = sanctie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Combibon)) {
            return false;
        }
        return getId() != null && getId().equals(((Combibon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Combibon{" +
            "id=" + getId() +
            ", sanctie='" + getSanctie() + "'" +
            "}";
    }
}
