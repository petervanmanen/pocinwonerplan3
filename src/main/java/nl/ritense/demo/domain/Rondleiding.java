package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rondleiding.
 */
@Entity
@Table(name = "rondleiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rondleiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "starttijd")
    private String starttijd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "voorRondleidings", "isbedoeldvoorBruikleens", "onderdeelMuseumobjects", "steltsamenSamenstellers" },
        allowSetters = true
    )
    private Tentoonstelling voorTentoonstelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRondleiding")
    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    private Set<Activiteit> heeftActiviteits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rondleiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Rondleiding eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public String getNaam() {
        return this.naam;
    }

    public Rondleiding naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Rondleiding omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Rondleiding starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public Tentoonstelling getVoorTentoonstelling() {
        return this.voorTentoonstelling;
    }

    public void setVoorTentoonstelling(Tentoonstelling tentoonstelling) {
        this.voorTentoonstelling = tentoonstelling;
    }

    public Rondleiding voorTentoonstelling(Tentoonstelling tentoonstelling) {
        this.setVoorTentoonstelling(tentoonstelling);
        return this;
    }

    public Set<Activiteit> getHeeftActiviteits() {
        return this.heeftActiviteits;
    }

    public void setHeeftActiviteits(Set<Activiteit> activiteits) {
        if (this.heeftActiviteits != null) {
            this.heeftActiviteits.forEach(i -> i.setHeeftRondleiding(null));
        }
        if (activiteits != null) {
            activiteits.forEach(i -> i.setHeeftRondleiding(this));
        }
        this.heeftActiviteits = activiteits;
    }

    public Rondleiding heeftActiviteits(Set<Activiteit> activiteits) {
        this.setHeeftActiviteits(activiteits);
        return this;
    }

    public Rondleiding addHeeftActiviteit(Activiteit activiteit) {
        this.heeftActiviteits.add(activiteit);
        activiteit.setHeeftRondleiding(this);
        return this;
    }

    public Rondleiding removeHeeftActiviteit(Activiteit activiteit) {
        this.heeftActiviteits.remove(activiteit);
        activiteit.setHeeftRondleiding(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rondleiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Rondleiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rondleiding{" +
            "id=" + getId() +
            ", eindtijd='" + getEindtijd() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", starttijd='" + getStarttijd() + "'" +
            "}";
    }
}
