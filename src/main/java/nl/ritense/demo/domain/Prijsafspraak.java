package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Prijsafspraak.
 */
@Entity
@Table(name = "prijsafspraak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prijsafspraak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "titel")
    private String titel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftPrijsafspraak")
    @JsonIgnoreProperties(value = { "heeftPrijsafspraak" }, allowSetters = true)
    private Set<Prijsregel> heeftPrijsregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prijsafspraak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Prijsafspraak datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Prijsafspraak datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getTitel() {
        return this.titel;
    }

    public Prijsafspraak titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Set<Prijsregel> getHeeftPrijsregels() {
        return this.heeftPrijsregels;
    }

    public void setHeeftPrijsregels(Set<Prijsregel> prijsregels) {
        if (this.heeftPrijsregels != null) {
            this.heeftPrijsregels.forEach(i -> i.setHeeftPrijsafspraak(null));
        }
        if (prijsregels != null) {
            prijsregels.forEach(i -> i.setHeeftPrijsafspraak(this));
        }
        this.heeftPrijsregels = prijsregels;
    }

    public Prijsafspraak heeftPrijsregels(Set<Prijsregel> prijsregels) {
        this.setHeeftPrijsregels(prijsregels);
        return this;
    }

    public Prijsafspraak addHeeftPrijsregel(Prijsregel prijsregel) {
        this.heeftPrijsregels.add(prijsregel);
        prijsregel.setHeeftPrijsafspraak(this);
        return this;
    }

    public Prijsafspraak removeHeeftPrijsregel(Prijsregel prijsregel) {
        this.heeftPrijsregels.remove(prijsregel);
        prijsregel.setHeeftPrijsafspraak(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prijsafspraak)) {
            return false;
        }
        return getId() != null && getId().equals(((Prijsafspraak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prijsafspraak{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
