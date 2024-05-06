package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Begroting.
 */
@Entity
@Table(name = "begroting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Begroting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Size(max = 20)
    @Column(name = "nummer", length = 20)
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftBegroting")
    @JsonIgnoreProperties(
        value = {
            "betreftDoelstelling", "betreftProduct", "betreftKostenplaats", "betreftHoofdrekening", "betreftHoofdstuk", "heeftBegroting",
        },
        allowSetters = true
    )
    private Set<Begrotingregel> heeftBegrotingregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Begroting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Begroting naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Begroting nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Begroting omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Begrotingregel> getHeeftBegrotingregels() {
        return this.heeftBegrotingregels;
    }

    public void setHeeftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        if (this.heeftBegrotingregels != null) {
            this.heeftBegrotingregels.forEach(i -> i.setHeeftBegroting(null));
        }
        if (begrotingregels != null) {
            begrotingregels.forEach(i -> i.setHeeftBegroting(this));
        }
        this.heeftBegrotingregels = begrotingregels;
    }

    public Begroting heeftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        this.setHeeftBegrotingregels(begrotingregels);
        return this;
    }

    public Begroting addHeeftBegrotingregel(Begrotingregel begrotingregel) {
        this.heeftBegrotingregels.add(begrotingregel);
        begrotingregel.setHeeftBegroting(this);
        return this;
    }

    public Begroting removeHeeftBegrotingregel(Begrotingregel begrotingregel) {
        this.heeftBegrotingregels.remove(begrotingregel);
        begrotingregel.setHeeftBegroting(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Begroting)) {
            return false;
        }
        return getId() != null && getId().equals(((Begroting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Begroting{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
