package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Productsoort.
 */
@Entity
@Table(name = "productsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Productsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "tarief", precision = 21, scale = 2)
    private BigDecimal tarief;

    @Size(max = 20)
    @Column(name = "tariefperiode", length = 20)
    private String tariefperiode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortProductsoort")
    @JsonIgnoreProperties(
        value = { "resulteertParkeerrecht", "houderRechtspersoon", "soortProductgroep", "soortProductsoort" },
        allowSetters = true
    )
    private Set<Parkeervergunning> soortParkeervergunnings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortParkeervergunnings", "valtbinnenProductsoorts", "valtbinnenProducts" }, allowSetters = true)
    private Productgroep valtbinnenProductgroep;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Productsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Productsoort code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Productsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public BigDecimal getTarief() {
        return this.tarief;
    }

    public Productsoort tarief(BigDecimal tarief) {
        this.setTarief(tarief);
        return this;
    }

    public void setTarief(BigDecimal tarief) {
        this.tarief = tarief;
    }

    public String getTariefperiode() {
        return this.tariefperiode;
    }

    public Productsoort tariefperiode(String tariefperiode) {
        this.setTariefperiode(tariefperiode);
        return this;
    }

    public void setTariefperiode(String tariefperiode) {
        this.tariefperiode = tariefperiode;
    }

    public Set<Parkeervergunning> getSoortParkeervergunnings() {
        return this.soortParkeervergunnings;
    }

    public void setSoortParkeervergunnings(Set<Parkeervergunning> parkeervergunnings) {
        if (this.soortParkeervergunnings != null) {
            this.soortParkeervergunnings.forEach(i -> i.setSoortProductsoort(null));
        }
        if (parkeervergunnings != null) {
            parkeervergunnings.forEach(i -> i.setSoortProductsoort(this));
        }
        this.soortParkeervergunnings = parkeervergunnings;
    }

    public Productsoort soortParkeervergunnings(Set<Parkeervergunning> parkeervergunnings) {
        this.setSoortParkeervergunnings(parkeervergunnings);
        return this;
    }

    public Productsoort addSoortParkeervergunning(Parkeervergunning parkeervergunning) {
        this.soortParkeervergunnings.add(parkeervergunning);
        parkeervergunning.setSoortProductsoort(this);
        return this;
    }

    public Productsoort removeSoortParkeervergunning(Parkeervergunning parkeervergunning) {
        this.soortParkeervergunnings.remove(parkeervergunning);
        parkeervergunning.setSoortProductsoort(null);
        return this;
    }

    public Productgroep getValtbinnenProductgroep() {
        return this.valtbinnenProductgroep;
    }

    public void setValtbinnenProductgroep(Productgroep productgroep) {
        this.valtbinnenProductgroep = productgroep;
    }

    public Productsoort valtbinnenProductgroep(Productgroep productgroep) {
        this.setValtbinnenProductgroep(productgroep);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Productsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Productsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Productsoort{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", tarief=" + getTarief() +
            ", tariefperiode='" + getTariefperiode() + "'" +
            "}";
    }
}
