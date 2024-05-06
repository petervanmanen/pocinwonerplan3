package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Verblijfstitel.
 */
@Entity
@Table(name = "verblijfstitel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verblijfstitel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidingverblijfstitel")
    private String aanduidingverblijfstitel;

    @Column(name = "datumbegin")
    private LocalDate datumbegin;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumopname")
    private LocalDate datumopname;

    @Column(name = "datumbegingeldigheidverblijfstitel")
    private LocalDate datumbegingeldigheidverblijfstitel;

    @Column(name = "verblijfstitelcode")
    private String verblijfstitelcode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftVerblijfstitel")
    @JsonIgnoreProperties(value = { "heeftVerblijfstitel" }, allowSetters = true)
    private Set<Ingezetene> heeftIngezetenes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verblijfstitel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidingverblijfstitel() {
        return this.aanduidingverblijfstitel;
    }

    public Verblijfstitel aanduidingverblijfstitel(String aanduidingverblijfstitel) {
        this.setAanduidingverblijfstitel(aanduidingverblijfstitel);
        return this;
    }

    public void setAanduidingverblijfstitel(String aanduidingverblijfstitel) {
        this.aanduidingverblijfstitel = aanduidingverblijfstitel;
    }

    public LocalDate getDatumbegin() {
        return this.datumbegin;
    }

    public Verblijfstitel datumbegin(LocalDate datumbegin) {
        this.setDatumbegin(datumbegin);
        return this;
    }

    public void setDatumbegin(LocalDate datumbegin) {
        this.datumbegin = datumbegin;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Verblijfstitel datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumopname() {
        return this.datumopname;
    }

    public Verblijfstitel datumopname(LocalDate datumopname) {
        this.setDatumopname(datumopname);
        return this;
    }

    public void setDatumopname(LocalDate datumopname) {
        this.datumopname = datumopname;
    }

    public LocalDate getDatumbegingeldigheidverblijfstitel() {
        return this.datumbegingeldigheidverblijfstitel;
    }

    public Verblijfstitel datumbegingeldigheidverblijfstitel(LocalDate datumbegingeldigheidverblijfstitel) {
        this.setDatumbegingeldigheidverblijfstitel(datumbegingeldigheidverblijfstitel);
        return this;
    }

    public void setDatumbegingeldigheidverblijfstitel(LocalDate datumbegingeldigheidverblijfstitel) {
        this.datumbegingeldigheidverblijfstitel = datumbegingeldigheidverblijfstitel;
    }

    public String getVerblijfstitelcode() {
        return this.verblijfstitelcode;
    }

    public Verblijfstitel verblijfstitelcode(String verblijfstitelcode) {
        this.setVerblijfstitelcode(verblijfstitelcode);
        return this;
    }

    public void setVerblijfstitelcode(String verblijfstitelcode) {
        this.verblijfstitelcode = verblijfstitelcode;
    }

    public Set<Ingezetene> getHeeftIngezetenes() {
        return this.heeftIngezetenes;
    }

    public void setHeeftIngezetenes(Set<Ingezetene> ingezetenes) {
        if (this.heeftIngezetenes != null) {
            this.heeftIngezetenes.forEach(i -> i.setHeeftVerblijfstitel(null));
        }
        if (ingezetenes != null) {
            ingezetenes.forEach(i -> i.setHeeftVerblijfstitel(this));
        }
        this.heeftIngezetenes = ingezetenes;
    }

    public Verblijfstitel heeftIngezetenes(Set<Ingezetene> ingezetenes) {
        this.setHeeftIngezetenes(ingezetenes);
        return this;
    }

    public Verblijfstitel addHeeftIngezetene(Ingezetene ingezetene) {
        this.heeftIngezetenes.add(ingezetene);
        ingezetene.setHeeftVerblijfstitel(this);
        return this;
    }

    public Verblijfstitel removeHeeftIngezetene(Ingezetene ingezetene) {
        this.heeftIngezetenes.remove(ingezetene);
        ingezetene.setHeeftVerblijfstitel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verblijfstitel)) {
            return false;
        }
        return getId() != null && getId().equals(((Verblijfstitel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verblijfstitel{" +
            "id=" + getId() +
            ", aanduidingverblijfstitel='" + getAanduidingverblijfstitel() + "'" +
            ", datumbegin='" + getDatumbegin() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumopname='" + getDatumopname() + "'" +
            ", datumbegingeldigheidverblijfstitel='" + getDatumbegingeldigheidverblijfstitel() + "'" +
            ", verblijfstitelcode='" + getVerblijfstitelcode() + "'" +
            "}";
    }
}
