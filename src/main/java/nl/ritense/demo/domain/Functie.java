package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Functie.
 */
@Entity
@Table(name = "functie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Functie implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitgevoerddoorFunctie")
    @JsonIgnoreProperties(value = { "isopdrachtgeverProducts", "uitgevoerddoorFunctie" }, allowSetters = true)
    private Set<Opdrachtgever> uitgevoerddoorOpdrachtgevers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitgevoerddoorFunctie")
    @JsonIgnoreProperties(value = { "isopdrachtnemerProducts", "uitgevoerddoorFunctie" }, allowSetters = true)
    private Set<Opdrachtnemer> uitgevoerddoorOpdrachtnemers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstverbandconformfunctieFunctie")
    @JsonIgnoreProperties(
        value = {
            "dienstverbandconformfunctieFunctie",
            "aantalvolgensinzetUren",
            "medewerkerheeftdienstverbandWerknemer",
            "aantalvolgensinzetInzet",
        },
        allowSetters = true
    )
    private Set<Dienstverband> dienstverbandconformfunctieDienstverbands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacaturebijfunctieFunctie")
    @JsonIgnoreProperties(value = { "vacaturebijfunctieFunctie", "opvacatureSollicitaties" }, allowSetters = true)
    private Set<Vacature> vacaturebijfunctieVacatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Functie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroep() {
        return this.groep;
    }

    public Functie groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public String getNaam() {
        return this.naam;
    }

    public Functie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Opdrachtgever> getUitgevoerddoorOpdrachtgevers() {
        return this.uitgevoerddoorOpdrachtgevers;
    }

    public void setUitgevoerddoorOpdrachtgevers(Set<Opdrachtgever> opdrachtgevers) {
        if (this.uitgevoerddoorOpdrachtgevers != null) {
            this.uitgevoerddoorOpdrachtgevers.forEach(i -> i.setUitgevoerddoorFunctie(null));
        }
        if (opdrachtgevers != null) {
            opdrachtgevers.forEach(i -> i.setUitgevoerddoorFunctie(this));
        }
        this.uitgevoerddoorOpdrachtgevers = opdrachtgevers;
    }

    public Functie uitgevoerddoorOpdrachtgevers(Set<Opdrachtgever> opdrachtgevers) {
        this.setUitgevoerddoorOpdrachtgevers(opdrachtgevers);
        return this;
    }

    public Functie addUitgevoerddoorOpdrachtgever(Opdrachtgever opdrachtgever) {
        this.uitgevoerddoorOpdrachtgevers.add(opdrachtgever);
        opdrachtgever.setUitgevoerddoorFunctie(this);
        return this;
    }

    public Functie removeUitgevoerddoorOpdrachtgever(Opdrachtgever opdrachtgever) {
        this.uitgevoerddoorOpdrachtgevers.remove(opdrachtgever);
        opdrachtgever.setUitgevoerddoorFunctie(null);
        return this;
    }

    public Set<Opdrachtnemer> getUitgevoerddoorOpdrachtnemers() {
        return this.uitgevoerddoorOpdrachtnemers;
    }

    public void setUitgevoerddoorOpdrachtnemers(Set<Opdrachtnemer> opdrachtnemers) {
        if (this.uitgevoerddoorOpdrachtnemers != null) {
            this.uitgevoerddoorOpdrachtnemers.forEach(i -> i.setUitgevoerddoorFunctie(null));
        }
        if (opdrachtnemers != null) {
            opdrachtnemers.forEach(i -> i.setUitgevoerddoorFunctie(this));
        }
        this.uitgevoerddoorOpdrachtnemers = opdrachtnemers;
    }

    public Functie uitgevoerddoorOpdrachtnemers(Set<Opdrachtnemer> opdrachtnemers) {
        this.setUitgevoerddoorOpdrachtnemers(opdrachtnemers);
        return this;
    }

    public Functie addUitgevoerddoorOpdrachtnemer(Opdrachtnemer opdrachtnemer) {
        this.uitgevoerddoorOpdrachtnemers.add(opdrachtnemer);
        opdrachtnemer.setUitgevoerddoorFunctie(this);
        return this;
    }

    public Functie removeUitgevoerddoorOpdrachtnemer(Opdrachtnemer opdrachtnemer) {
        this.uitgevoerddoorOpdrachtnemers.remove(opdrachtnemer);
        opdrachtnemer.setUitgevoerddoorFunctie(null);
        return this;
    }

    public Set<Dienstverband> getDienstverbandconformfunctieDienstverbands() {
        return this.dienstverbandconformfunctieDienstverbands;
    }

    public void setDienstverbandconformfunctieDienstverbands(Set<Dienstverband> dienstverbands) {
        if (this.dienstverbandconformfunctieDienstverbands != null) {
            this.dienstverbandconformfunctieDienstverbands.forEach(i -> i.setDienstverbandconformfunctieFunctie(null));
        }
        if (dienstverbands != null) {
            dienstverbands.forEach(i -> i.setDienstverbandconformfunctieFunctie(this));
        }
        this.dienstverbandconformfunctieDienstverbands = dienstverbands;
    }

    public Functie dienstverbandconformfunctieDienstverbands(Set<Dienstverband> dienstverbands) {
        this.setDienstverbandconformfunctieDienstverbands(dienstverbands);
        return this;
    }

    public Functie addDienstverbandconformfunctieDienstverband(Dienstverband dienstverband) {
        this.dienstverbandconformfunctieDienstverbands.add(dienstverband);
        dienstverband.setDienstverbandconformfunctieFunctie(this);
        return this;
    }

    public Functie removeDienstverbandconformfunctieDienstverband(Dienstverband dienstverband) {
        this.dienstverbandconformfunctieDienstverbands.remove(dienstverband);
        dienstverband.setDienstverbandconformfunctieFunctie(null);
        return this;
    }

    public Set<Vacature> getVacaturebijfunctieVacatures() {
        return this.vacaturebijfunctieVacatures;
    }

    public void setVacaturebijfunctieVacatures(Set<Vacature> vacatures) {
        if (this.vacaturebijfunctieVacatures != null) {
            this.vacaturebijfunctieVacatures.forEach(i -> i.setVacaturebijfunctieFunctie(null));
        }
        if (vacatures != null) {
            vacatures.forEach(i -> i.setVacaturebijfunctieFunctie(this));
        }
        this.vacaturebijfunctieVacatures = vacatures;
    }

    public Functie vacaturebijfunctieVacatures(Set<Vacature> vacatures) {
        this.setVacaturebijfunctieVacatures(vacatures);
        return this;
    }

    public Functie addVacaturebijfunctieVacature(Vacature vacature) {
        this.vacaturebijfunctieVacatures.add(vacature);
        vacature.setVacaturebijfunctieFunctie(this);
        return this;
    }

    public Functie removeVacaturebijfunctieVacature(Vacature vacature) {
        this.vacaturebijfunctieVacatures.remove(vacature);
        vacature.setVacaturebijfunctieFunctie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Functie)) {
            return false;
        }
        return getId() != null && getId().equals(((Functie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Functie{" +
            "id=" + getId() +
            ", groep='" + getGroep() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
