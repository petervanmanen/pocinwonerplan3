package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verkooppunt.
 */
@Entity
@Table(name = "verkooppunt")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verkooppunt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "winkelformule")
    private String winkelformule;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verkooppunt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWinkelformule() {
        return this.winkelformule;
    }

    public Verkooppunt winkelformule(String winkelformule) {
        this.setWinkelformule(winkelformule);
        return this;
    }

    public void setWinkelformule(String winkelformule) {
        this.winkelformule = winkelformule;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verkooppunt)) {
            return false;
        }
        return getId() != null && getId().equals(((Verkooppunt) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verkooppunt{" +
            "id=" + getId() +
            ", winkelformule='" + getWinkelformule() + "'" +
            "}";
    }
}
