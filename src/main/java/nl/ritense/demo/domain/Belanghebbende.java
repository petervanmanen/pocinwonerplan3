package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Belanghebbende.
 */
@Entity
@Table(name = "belanghebbende")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Belanghebbende implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumtot")
    private LocalDate datumtot;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftBelanghebbendes")
    @JsonIgnoreProperties(
        value = {
            "betreftBruikleen",
            "locatieStandplaats",
            "heeftBelanghebbendes",
            "onderdeelTentoonstellings",
            "bevatCollecties",
            "betreftIncidents",
        },
        allowSetters = true
    )
    private Set<Museumobject> heeftMuseumobjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Belanghebbende id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Belanghebbende datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumtot() {
        return this.datumtot;
    }

    public Belanghebbende datumtot(LocalDate datumtot) {
        this.setDatumtot(datumtot);
        return this;
    }

    public void setDatumtot(LocalDate datumtot) {
        this.datumtot = datumtot;
    }

    public Set<Museumobject> getHeeftMuseumobjects() {
        return this.heeftMuseumobjects;
    }

    public void setHeeftMuseumobjects(Set<Museumobject> museumobjects) {
        if (this.heeftMuseumobjects != null) {
            this.heeftMuseumobjects.forEach(i -> i.removeHeeftBelanghebbende(this));
        }
        if (museumobjects != null) {
            museumobjects.forEach(i -> i.addHeeftBelanghebbende(this));
        }
        this.heeftMuseumobjects = museumobjects;
    }

    public Belanghebbende heeftMuseumobjects(Set<Museumobject> museumobjects) {
        this.setHeeftMuseumobjects(museumobjects);
        return this;
    }

    public Belanghebbende addHeeftMuseumobject(Museumobject museumobject) {
        this.heeftMuseumobjects.add(museumobject);
        museumobject.getHeeftBelanghebbendes().add(this);
        return this;
    }

    public Belanghebbende removeHeeftMuseumobject(Museumobject museumobject) {
        this.heeftMuseumobjects.remove(museumobject);
        museumobject.getHeeftBelanghebbendes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Belanghebbende)) {
            return false;
        }
        return getId() != null && getId().equals(((Belanghebbende) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Belanghebbende{" +
            "id=" + getId() +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtot='" + getDatumtot() + "'" +
            "}";
    }
}
