package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Soortscheiding.
 */
@Entity
@Table(name = "soortscheiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortscheiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "indicatieplusbrpopulatie")
    private String indicatieplusbrpopulatie;

    @Column(name = "typescheiding")
    private String typescheiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortscheiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicatieplusbrpopulatie() {
        return this.indicatieplusbrpopulatie;
    }

    public Soortscheiding indicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.setIndicatieplusbrpopulatie(indicatieplusbrpopulatie);
        return this;
    }

    public void setIndicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.indicatieplusbrpopulatie = indicatieplusbrpopulatie;
    }

    public String getTypescheiding() {
        return this.typescheiding;
    }

    public Soortscheiding typescheiding(String typescheiding) {
        this.setTypescheiding(typescheiding);
        return this;
    }

    public void setTypescheiding(String typescheiding) {
        this.typescheiding = typescheiding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortscheiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortscheiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortscheiding{" +
            "id=" + getId() +
            ", indicatieplusbrpopulatie='" + getIndicatieplusbrpopulatie() + "'" +
            ", typescheiding='" + getTypescheiding() + "'" +
            "}";
    }
}
