package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Soortoverigbouwwerk.
 */
@Entity
@Table(name = "soortoverigbouwwerk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortoverigbouwwerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "indicatieplusbrpopulatie")
    private String indicatieplusbrpopulatie;

    @Column(name = "typeoverigbouwwerk")
    private String typeoverigbouwwerk;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortoverigbouwwerk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicatieplusbrpopulatie() {
        return this.indicatieplusbrpopulatie;
    }

    public Soortoverigbouwwerk indicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.setIndicatieplusbrpopulatie(indicatieplusbrpopulatie);
        return this;
    }

    public void setIndicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.indicatieplusbrpopulatie = indicatieplusbrpopulatie;
    }

    public String getTypeoverigbouwwerk() {
        return this.typeoverigbouwwerk;
    }

    public Soortoverigbouwwerk typeoverigbouwwerk(String typeoverigbouwwerk) {
        this.setTypeoverigbouwwerk(typeoverigbouwwerk);
        return this;
    }

    public void setTypeoverigbouwwerk(String typeoverigbouwwerk) {
        this.typeoverigbouwwerk = typeoverigbouwwerk;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortoverigbouwwerk)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortoverigbouwwerk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortoverigbouwwerk{" +
            "id=" + getId() +
            ", indicatieplusbrpopulatie='" + getIndicatieplusbrpopulatie() + "'" +
            ", typeoverigbouwwerk='" + getTypeoverigbouwwerk() + "'" +
            "}";
    }
}
