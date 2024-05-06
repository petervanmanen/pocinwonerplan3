package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Voorziening.
 */
@Entity
@Table(name = "voorziening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Voorziening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalbeschikbaar")
    private String aantalbeschikbaar;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftVoorziening")
    @JsonIgnoreProperties(value = { "heeftLeverancier", "bevatContract", "heeftVoorziening" }, allowSetters = true)
    private Set<Tarief> heeftTariefs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "valtbinnenVoorzienings" }, allowSetters = true)
    private Voorzieningsoort valtbinnenVoorzieningsoort;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftVoorziening")
    @JsonIgnoreProperties(value = { "betreftVoorziening", "betreftZaal", "heeftActiviteit" }, allowSetters = true)
    private Set<Reservering> betreftReserverings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voorzieningVoorziening")
    @JsonIgnoreProperties(
        value = {
            "geleverdeprestatieBeschikking",
            "prestatievoorClient",
            "geleverdezorgToewijzing",
            "voorzieningVoorziening",
            "leverdeprestatieLeverancier",
        },
        allowSetters = true
    )
    private Set<Levering> voorzieningLeverings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Voorziening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalbeschikbaar() {
        return this.aantalbeschikbaar;
    }

    public Voorziening aantalbeschikbaar(String aantalbeschikbaar) {
        this.setAantalbeschikbaar(aantalbeschikbaar);
        return this;
    }

    public void setAantalbeschikbaar(String aantalbeschikbaar) {
        this.aantalbeschikbaar = aantalbeschikbaar;
    }

    public String getNaam() {
        return this.naam;
    }

    public Voorziening naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Voorziening omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Tarief> getHeeftTariefs() {
        return this.heeftTariefs;
    }

    public void setHeeftTariefs(Set<Tarief> tariefs) {
        if (this.heeftTariefs != null) {
            this.heeftTariefs.forEach(i -> i.setHeeftVoorziening(null));
        }
        if (tariefs != null) {
            tariefs.forEach(i -> i.setHeeftVoorziening(this));
        }
        this.heeftTariefs = tariefs;
    }

    public Voorziening heeftTariefs(Set<Tarief> tariefs) {
        this.setHeeftTariefs(tariefs);
        return this;
    }

    public Voorziening addHeeftTarief(Tarief tarief) {
        this.heeftTariefs.add(tarief);
        tarief.setHeeftVoorziening(this);
        return this;
    }

    public Voorziening removeHeeftTarief(Tarief tarief) {
        this.heeftTariefs.remove(tarief);
        tarief.setHeeftVoorziening(null);
        return this;
    }

    public Voorzieningsoort getValtbinnenVoorzieningsoort() {
        return this.valtbinnenVoorzieningsoort;
    }

    public void setValtbinnenVoorzieningsoort(Voorzieningsoort voorzieningsoort) {
        this.valtbinnenVoorzieningsoort = voorzieningsoort;
    }

    public Voorziening valtbinnenVoorzieningsoort(Voorzieningsoort voorzieningsoort) {
        this.setValtbinnenVoorzieningsoort(voorzieningsoort);
        return this;
    }

    public Set<Reservering> getBetreftReserverings() {
        return this.betreftReserverings;
    }

    public void setBetreftReserverings(Set<Reservering> reserverings) {
        if (this.betreftReserverings != null) {
            this.betreftReserverings.forEach(i -> i.setBetreftVoorziening(null));
        }
        if (reserverings != null) {
            reserverings.forEach(i -> i.setBetreftVoorziening(this));
        }
        this.betreftReserverings = reserverings;
    }

    public Voorziening betreftReserverings(Set<Reservering> reserverings) {
        this.setBetreftReserverings(reserverings);
        return this;
    }

    public Voorziening addBetreftReservering(Reservering reservering) {
        this.betreftReserverings.add(reservering);
        reservering.setBetreftVoorziening(this);
        return this;
    }

    public Voorziening removeBetreftReservering(Reservering reservering) {
        this.betreftReserverings.remove(reservering);
        reservering.setBetreftVoorziening(null);
        return this;
    }

    public Set<Levering> getVoorzieningLeverings() {
        return this.voorzieningLeverings;
    }

    public void setVoorzieningLeverings(Set<Levering> leverings) {
        if (this.voorzieningLeverings != null) {
            this.voorzieningLeverings.forEach(i -> i.setVoorzieningVoorziening(null));
        }
        if (leverings != null) {
            leverings.forEach(i -> i.setVoorzieningVoorziening(this));
        }
        this.voorzieningLeverings = leverings;
    }

    public Voorziening voorzieningLeverings(Set<Levering> leverings) {
        this.setVoorzieningLeverings(leverings);
        return this;
    }

    public Voorziening addVoorzieningLevering(Levering levering) {
        this.voorzieningLeverings.add(levering);
        levering.setVoorzieningVoorziening(this);
        return this;
    }

    public Voorziening removeVoorzieningLevering(Levering levering) {
        this.voorzieningLeverings.remove(levering);
        levering.setVoorzieningVoorziening(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voorziening)) {
            return false;
        }
        return getId() != null && getId().equals(((Voorziening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voorziening{" +
            "id=" + getId() +
            ", aantalbeschikbaar='" + getAantalbeschikbaar() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
