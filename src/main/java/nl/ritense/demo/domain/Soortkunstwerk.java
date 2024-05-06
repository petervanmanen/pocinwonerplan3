package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Soortkunstwerk.
 */
@Entity
@Table(name = "soortkunstwerk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortkunstwerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "indicatieplusbrpopulatie")
    private String indicatieplusbrpopulatie;

    @Column(name = "typekunstwerk")
    private String typekunstwerk;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortkunstwerk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicatieplusbrpopulatie() {
        return this.indicatieplusbrpopulatie;
    }

    public Soortkunstwerk indicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.setIndicatieplusbrpopulatie(indicatieplusbrpopulatie);
        return this;
    }

    public void setIndicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.indicatieplusbrpopulatie = indicatieplusbrpopulatie;
    }

    public String getTypekunstwerk() {
        return this.typekunstwerk;
    }

    public Soortkunstwerk typekunstwerk(String typekunstwerk) {
        this.setTypekunstwerk(typekunstwerk);
        return this;
    }

    public void setTypekunstwerk(String typekunstwerk) {
        this.typekunstwerk = typekunstwerk;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortkunstwerk)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortkunstwerk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortkunstwerk{" +
            "id=" + getId() +
            ", indicatieplusbrpopulatie='" + getIndicatieplusbrpopulatie() + "'" +
            ", typekunstwerk='" + getTypekunstwerk() + "'" +
            "}";
    }
}
