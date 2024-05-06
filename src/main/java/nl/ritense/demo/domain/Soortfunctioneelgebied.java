package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Soortfunctioneelgebied.
 */
@Entity
@Table(name = "soortfunctioneelgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortfunctioneelgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "indicatieplusbrpopulatie")
    private String indicatieplusbrpopulatie;

    @Column(name = "typefunctioneelgebied")
    private String typefunctioneelgebied;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortfunctioneelgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicatieplusbrpopulatie() {
        return this.indicatieplusbrpopulatie;
    }

    public Soortfunctioneelgebied indicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.setIndicatieplusbrpopulatie(indicatieplusbrpopulatie);
        return this;
    }

    public void setIndicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.indicatieplusbrpopulatie = indicatieplusbrpopulatie;
    }

    public String getTypefunctioneelgebied() {
        return this.typefunctioneelgebied;
    }

    public Soortfunctioneelgebied typefunctioneelgebied(String typefunctioneelgebied) {
        this.setTypefunctioneelgebied(typefunctioneelgebied);
        return this;
    }

    public void setTypefunctioneelgebied(String typefunctioneelgebied) {
        this.typefunctioneelgebied = typefunctioneelgebied;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortfunctioneelgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortfunctioneelgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortfunctioneelgebied{" +
            "id=" + getId() +
            ", indicatieplusbrpopulatie='" + getIndicatieplusbrpopulatie() + "'" +
            ", typefunctioneelgebied='" + getTypefunctioneelgebied() + "'" +
            "}";
    }
}
