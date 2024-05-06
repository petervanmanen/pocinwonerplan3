package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Kolk.
 */
@Entity
@Table(name = "kolk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kolk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bereikbaarheidkolk")
    private String bereikbaarheidkolk;

    @Column(name = "risicogebied")
    private String risicogebied;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kolk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBereikbaarheidkolk() {
        return this.bereikbaarheidkolk;
    }

    public Kolk bereikbaarheidkolk(String bereikbaarheidkolk) {
        this.setBereikbaarheidkolk(bereikbaarheidkolk);
        return this;
    }

    public void setBereikbaarheidkolk(String bereikbaarheidkolk) {
        this.bereikbaarheidkolk = bereikbaarheidkolk;
    }

    public String getRisicogebied() {
        return this.risicogebied;
    }

    public Kolk risicogebied(String risicogebied) {
        this.setRisicogebied(risicogebied);
        return this;
    }

    public void setRisicogebied(String risicogebied) {
        this.risicogebied = risicogebied;
    }

    public String getType() {
        return this.type;
    }

    public Kolk type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kolk)) {
            return false;
        }
        return getId() != null && getId().equals(((Kolk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kolk{" +
            "id=" + getId() +
            ", bereikbaarheidkolk='" + getBereikbaarheidkolk() + "'" +
            ", risicogebied='" + getRisicogebied() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
