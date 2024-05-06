package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Uren.
 */
@Entity
@Table(name = "uren")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uren implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aantalvolgensinzetUren")
    @JsonIgnoreProperties(
        value = {
            "dienstverbandconformfunctieFunctie",
            "aantalvolgensinzetUren",
            "medewerkerheeftdienstverbandWerknemer",
            "aantalvolgensinzetInzet",
        },
        allowSetters = true
    )
    private Set<Dienstverband> aantalvolgensinzetDienstverbands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uren id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Uren aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public Set<Dienstverband> getAantalvolgensinzetDienstverbands() {
        return this.aantalvolgensinzetDienstverbands;
    }

    public void setAantalvolgensinzetDienstverbands(Set<Dienstverband> dienstverbands) {
        if (this.aantalvolgensinzetDienstverbands != null) {
            this.aantalvolgensinzetDienstverbands.forEach(i -> i.setAantalvolgensinzetUren(null));
        }
        if (dienstverbands != null) {
            dienstverbands.forEach(i -> i.setAantalvolgensinzetUren(this));
        }
        this.aantalvolgensinzetDienstverbands = dienstverbands;
    }

    public Uren aantalvolgensinzetDienstverbands(Set<Dienstverband> dienstverbands) {
        this.setAantalvolgensinzetDienstverbands(dienstverbands);
        return this;
    }

    public Uren addAantalvolgensinzetDienstverband(Dienstverband dienstverband) {
        this.aantalvolgensinzetDienstverbands.add(dienstverband);
        dienstverband.setAantalvolgensinzetUren(this);
        return this;
    }

    public Uren removeAantalvolgensinzetDienstverband(Dienstverband dienstverband) {
        this.aantalvolgensinzetDienstverbands.remove(dienstverband);
        dienstverband.setAantalvolgensinzetUren(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uren)) {
            return false;
        }
        return getId() != null && getId().equals(((Uren) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uren{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            "}";
    }
}
