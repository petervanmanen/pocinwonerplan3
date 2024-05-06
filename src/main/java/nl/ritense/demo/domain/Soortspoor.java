package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Soortspoor.
 */
@Entity
@Table(name = "soortspoor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortspoor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "functiespoor")
    private String functiespoor;

    @Column(name = "indicatieplusbrpopulatie")
    private String indicatieplusbrpopulatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortspoor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctiespoor() {
        return this.functiespoor;
    }

    public Soortspoor functiespoor(String functiespoor) {
        this.setFunctiespoor(functiespoor);
        return this;
    }

    public void setFunctiespoor(String functiespoor) {
        this.functiespoor = functiespoor;
    }

    public String getIndicatieplusbrpopulatie() {
        return this.indicatieplusbrpopulatie;
    }

    public Soortspoor indicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.setIndicatieplusbrpopulatie(indicatieplusbrpopulatie);
        return this;
    }

    public void setIndicatieplusbrpopulatie(String indicatieplusbrpopulatie) {
        this.indicatieplusbrpopulatie = indicatieplusbrpopulatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortspoor)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortspoor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortspoor{" +
            "id=" + getId() +
            ", functiespoor='" + getFunctiespoor() + "'" +
            ", indicatieplusbrpopulatie='" + getIndicatieplusbrpopulatie() + "'" +
            "}";
    }
}
