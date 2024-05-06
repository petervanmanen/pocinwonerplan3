package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Opschortingzaak.
 */
@Entity
@Table(name = "opschortingzaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opschortingzaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "indicatieopschorting")
    private String indicatieopschorting;

    @Column(name = "redenopschorting")
    private String redenopschorting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opschortingzaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicatieopschorting() {
        return this.indicatieopschorting;
    }

    public Opschortingzaak indicatieopschorting(String indicatieopschorting) {
        this.setIndicatieopschorting(indicatieopschorting);
        return this;
    }

    public void setIndicatieopschorting(String indicatieopschorting) {
        this.indicatieopschorting = indicatieopschorting;
    }

    public String getRedenopschorting() {
        return this.redenopschorting;
    }

    public Opschortingzaak redenopschorting(String redenopschorting) {
        this.setRedenopschorting(redenopschorting);
        return this;
    }

    public void setRedenopschorting(String redenopschorting) {
        this.redenopschorting = redenopschorting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opschortingzaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Opschortingzaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opschortingzaak{" +
            "id=" + getId() +
            ", indicatieopschorting='" + getIndicatieopschorting() + "'" +
            ", redenopschorting='" + getRedenopschorting() + "'" +
            "}";
    }
}
