package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Gebiedsaanwijzing.
 */
@Entity
@Table(name = "gebiedsaanwijzing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebiedsaanwijzing implements Serializable {

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

    @Column(name = "nen_3610_id")
    private String nen3610id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_gebiedsaanwijzing__verwijstnaar_locatie",
        joinColumns = @JoinColumn(name = "gebiedsaanwijzing_id"),
        inverseJoinColumns = @JoinColumn(name = "verwijstnaar_locatie_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Set<Locatie> verwijstnaarLocaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "beschrijftgebiedsaanwijzingGebiedsaanwijzings")
    @JsonIgnoreProperties(value = { "beschrijftgebiedsaanwijzingGebiedsaanwijzings" }, allowSetters = true)
    private Set<Instructieregel> beschrijftgebiedsaanwijzingInstructieregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebiedsaanwijzing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroep() {
        return this.groep;
    }

    public Gebiedsaanwijzing groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public String getNaam() {
        return this.naam;
    }

    public Gebiedsaanwijzing naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNen3610id() {
        return this.nen3610id;
    }

    public Gebiedsaanwijzing nen3610id(String nen3610id) {
        this.setNen3610id(nen3610id);
        return this;
    }

    public void setNen3610id(String nen3610id) {
        this.nen3610id = nen3610id;
    }

    public Set<Locatie> getVerwijstnaarLocaties() {
        return this.verwijstnaarLocaties;
    }

    public void setVerwijstnaarLocaties(Set<Locatie> locaties) {
        this.verwijstnaarLocaties = locaties;
    }

    public Gebiedsaanwijzing verwijstnaarLocaties(Set<Locatie> locaties) {
        this.setVerwijstnaarLocaties(locaties);
        return this;
    }

    public Gebiedsaanwijzing addVerwijstnaarLocatie(Locatie locatie) {
        this.verwijstnaarLocaties.add(locatie);
        return this;
    }

    public Gebiedsaanwijzing removeVerwijstnaarLocatie(Locatie locatie) {
        this.verwijstnaarLocaties.remove(locatie);
        return this;
    }

    public Set<Instructieregel> getBeschrijftgebiedsaanwijzingInstructieregels() {
        return this.beschrijftgebiedsaanwijzingInstructieregels;
    }

    public void setBeschrijftgebiedsaanwijzingInstructieregels(Set<Instructieregel> instructieregels) {
        if (this.beschrijftgebiedsaanwijzingInstructieregels != null) {
            this.beschrijftgebiedsaanwijzingInstructieregels.forEach(i -> i.removeBeschrijftgebiedsaanwijzingGebiedsaanwijzing(this));
        }
        if (instructieregels != null) {
            instructieregels.forEach(i -> i.addBeschrijftgebiedsaanwijzingGebiedsaanwijzing(this));
        }
        this.beschrijftgebiedsaanwijzingInstructieregels = instructieregels;
    }

    public Gebiedsaanwijzing beschrijftgebiedsaanwijzingInstructieregels(Set<Instructieregel> instructieregels) {
        this.setBeschrijftgebiedsaanwijzingInstructieregels(instructieregels);
        return this;
    }

    public Gebiedsaanwijzing addBeschrijftgebiedsaanwijzingInstructieregel(Instructieregel instructieregel) {
        this.beschrijftgebiedsaanwijzingInstructieregels.add(instructieregel);
        instructieregel.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings().add(this);
        return this;
    }

    public Gebiedsaanwijzing removeBeschrijftgebiedsaanwijzingInstructieregel(Instructieregel instructieregel) {
        this.beschrijftgebiedsaanwijzingInstructieregels.remove(instructieregel);
        instructieregel.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebiedsaanwijzing)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebiedsaanwijzing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebiedsaanwijzing{" +
            "id=" + getId() +
            ", groep='" + getGroep() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nen3610id='" + getNen3610id() + "'" +
            "}";
    }
}
