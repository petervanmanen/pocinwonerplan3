package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Classificatie.
 */
@Entity
@Table(name = "classificatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Classificatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "bevatpersoonsgegevens")
    private String bevatpersoonsgegevens;

    @Column(name = "gerelateerdpersoonsgegevens")
    private String gerelateerdpersoonsgegevens;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "geclassificeerdalsClassificaties")
    @JsonIgnoreProperties(value = { "geclassificeerdalsClassificaties", "bevatApplicatie" }, allowSetters = true)
    private Set<Gegeven> geclassificeerdalsGegevens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getBevatpersoonsgegevens() {
        return this.bevatpersoonsgegevens;
    }

    public Classificatie bevatpersoonsgegevens(String bevatpersoonsgegevens) {
        this.setBevatpersoonsgegevens(bevatpersoonsgegevens);
        return this;
    }

    public void setBevatpersoonsgegevens(String bevatpersoonsgegevens) {
        this.bevatpersoonsgegevens = bevatpersoonsgegevens;
    }

    public String getGerelateerdpersoonsgegevens() {
        return this.gerelateerdpersoonsgegevens;
    }

    public Classificatie gerelateerdpersoonsgegevens(String gerelateerdpersoonsgegevens) {
        this.setGerelateerdpersoonsgegevens(gerelateerdpersoonsgegevens);
        return this;
    }

    public void setGerelateerdpersoonsgegevens(String gerelateerdpersoonsgegevens) {
        this.gerelateerdpersoonsgegevens = gerelateerdpersoonsgegevens;
    }

    public String getId() {
        return this.id;
    }

    public Classificatie id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Gegeven> getGeclassificeerdalsGegevens() {
        return this.geclassificeerdalsGegevens;
    }

    public void setGeclassificeerdalsGegevens(Set<Gegeven> gegevens) {
        if (this.geclassificeerdalsGegevens != null) {
            this.geclassificeerdalsGegevens.forEach(i -> i.removeGeclassificeerdalsClassificatie(this));
        }
        if (gegevens != null) {
            gegevens.forEach(i -> i.addGeclassificeerdalsClassificatie(this));
        }
        this.geclassificeerdalsGegevens = gegevens;
    }

    public Classificatie geclassificeerdalsGegevens(Set<Gegeven> gegevens) {
        this.setGeclassificeerdalsGegevens(gegevens);
        return this;
    }

    public Classificatie addGeclassificeerdalsGegeven(Gegeven gegeven) {
        this.geclassificeerdalsGegevens.add(gegeven);
        gegeven.getGeclassificeerdalsClassificaties().add(this);
        return this;
    }

    public Classificatie removeGeclassificeerdalsGegeven(Gegeven gegeven) {
        this.geclassificeerdalsGegevens.remove(gegeven);
        gegeven.getGeclassificeerdalsClassificaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classificatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Classificatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classificatie{" +
            "id=" + getId() +
            ", bevatpersoonsgegevens='" + getBevatpersoonsgegevens() + "'" +
            ", gerelateerdpersoonsgegevens='" + getGerelateerdpersoonsgegevens() + "'" +
            "}";
    }
}
