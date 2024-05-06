package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hoofdstuk.
 */
@Entity
@Table(name = "hoofdstuk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hoofdstuk implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftHoofdstuk")
    @JsonIgnoreProperties(
        value = { "heeftProducts", "isvansoortDoelstellingsoort", "heeftHoofdstuk", "betreftBegrotingregels" },
        allowSetters = true
    )
    private Set<Doelstelling> heeftDoelstellings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_hoofdstuk__binnen_periode",
        joinColumns = @JoinColumn(name = "hoofdstuk_id"),
        inverseJoinColumns = @JoinColumn(name = "binnen_periode_id")
    )
    @JsonIgnoreProperties(value = { "stamtuitArchiefs", "stamtuitArchiefstuks", "binnenHoofdstuks" }, allowSetters = true)
    private Set<Periode> binnenPeriodes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftHoofdstuk")
    @JsonIgnoreProperties(
        value = {
            "betreftDoelstelling", "betreftProduct", "betreftKostenplaats", "betreftHoofdrekening", "betreftHoofdstuk", "heeftBegroting",
        },
        allowSetters = true
    )
    private Set<Begrotingregel> betreftBegrotingregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hoofdstuk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Hoofdstuk naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Hoofdstuk nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Hoofdstuk omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Doelstelling> getHeeftDoelstellings() {
        return this.heeftDoelstellings;
    }

    public void setHeeftDoelstellings(Set<Doelstelling> doelstellings) {
        if (this.heeftDoelstellings != null) {
            this.heeftDoelstellings.forEach(i -> i.setHeeftHoofdstuk(null));
        }
        if (doelstellings != null) {
            doelstellings.forEach(i -> i.setHeeftHoofdstuk(this));
        }
        this.heeftDoelstellings = doelstellings;
    }

    public Hoofdstuk heeftDoelstellings(Set<Doelstelling> doelstellings) {
        this.setHeeftDoelstellings(doelstellings);
        return this;
    }

    public Hoofdstuk addHeeftDoelstelling(Doelstelling doelstelling) {
        this.heeftDoelstellings.add(doelstelling);
        doelstelling.setHeeftHoofdstuk(this);
        return this;
    }

    public Hoofdstuk removeHeeftDoelstelling(Doelstelling doelstelling) {
        this.heeftDoelstellings.remove(doelstelling);
        doelstelling.setHeeftHoofdstuk(null);
        return this;
    }

    public Set<Periode> getBinnenPeriodes() {
        return this.binnenPeriodes;
    }

    public void setBinnenPeriodes(Set<Periode> periodes) {
        this.binnenPeriodes = periodes;
    }

    public Hoofdstuk binnenPeriodes(Set<Periode> periodes) {
        this.setBinnenPeriodes(periodes);
        return this;
    }

    public Hoofdstuk addBinnenPeriode(Periode periode) {
        this.binnenPeriodes.add(periode);
        return this;
    }

    public Hoofdstuk removeBinnenPeriode(Periode periode) {
        this.binnenPeriodes.remove(periode);
        return this;
    }

    public Set<Begrotingregel> getBetreftBegrotingregels() {
        return this.betreftBegrotingregels;
    }

    public void setBetreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        if (this.betreftBegrotingregels != null) {
            this.betreftBegrotingregels.forEach(i -> i.setBetreftHoofdstuk(null));
        }
        if (begrotingregels != null) {
            begrotingregels.forEach(i -> i.setBetreftHoofdstuk(this));
        }
        this.betreftBegrotingregels = begrotingregels;
    }

    public Hoofdstuk betreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        this.setBetreftBegrotingregels(begrotingregels);
        return this;
    }

    public Hoofdstuk addBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.add(begrotingregel);
        begrotingregel.setBetreftHoofdstuk(this);
        return this;
    }

    public Hoofdstuk removeBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.remove(begrotingregel);
        begrotingregel.setBetreftHoofdstuk(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hoofdstuk)) {
            return false;
        }
        return getId() != null && getId().equals(((Hoofdstuk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hoofdstuk{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
