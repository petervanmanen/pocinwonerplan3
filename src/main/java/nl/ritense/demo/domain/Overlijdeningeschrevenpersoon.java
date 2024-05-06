package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overlijdeningeschrevenpersoon.
 */
@Entity
@Table(name = "overlijdeningeschrevenpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overlijdeningeschrevenpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumoverlijden")
    private String datumoverlijden;

    @Column(name = "landoverlijden")
    private String landoverlijden;

    @Column(name = "overlijdensplaats")
    private String overlijdensplaats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overlijdeningeschrevenpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumoverlijden() {
        return this.datumoverlijden;
    }

    public Overlijdeningeschrevenpersoon datumoverlijden(String datumoverlijden) {
        this.setDatumoverlijden(datumoverlijden);
        return this;
    }

    public void setDatumoverlijden(String datumoverlijden) {
        this.datumoverlijden = datumoverlijden;
    }

    public String getLandoverlijden() {
        return this.landoverlijden;
    }

    public Overlijdeningeschrevenpersoon landoverlijden(String landoverlijden) {
        this.setLandoverlijden(landoverlijden);
        return this;
    }

    public void setLandoverlijden(String landoverlijden) {
        this.landoverlijden = landoverlijden;
    }

    public String getOverlijdensplaats() {
        return this.overlijdensplaats;
    }

    public Overlijdeningeschrevenpersoon overlijdensplaats(String overlijdensplaats) {
        this.setOverlijdensplaats(overlijdensplaats);
        return this;
    }

    public void setOverlijdensplaats(String overlijdensplaats) {
        this.overlijdensplaats = overlijdensplaats;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overlijdeningeschrevenpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Overlijdeningeschrevenpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overlijdeningeschrevenpersoon{" +
            "id=" + getId() +
            ", datumoverlijden='" + getDatumoverlijden() + "'" +
            ", landoverlijden='" + getLandoverlijden() + "'" +
            ", overlijdensplaats='" + getOverlijdensplaats() + "'" +
            "}";
    }
}
