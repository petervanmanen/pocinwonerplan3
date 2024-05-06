package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Beperkingsgebied.
 */
@Entity
@Table(name = "beperkingsgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beperkingsgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "groep")
    private String groep;

    @Column(name = "naam")
    private String naam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beperkingsgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroep() {
        return this.groep;
    }

    public Beperkingsgebied groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public String getNaam() {
        return this.naam;
    }

    public Beperkingsgebied naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beperkingsgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Beperkingsgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beperkingsgebied{" +
            "id=" + getId() +
            ", groep='" + getGroep() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
