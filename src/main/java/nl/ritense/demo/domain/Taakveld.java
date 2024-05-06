package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Taakveld.
 */
@Entity
@Table(name = "taakveld")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Taakveld implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftTaakveld")
    @JsonIgnoreProperties(
        value = {
            "heeftTaakveld",
            "behandeltAgendapunts",
            "hoortbijProgrammas",
            "wordtbehandeldinVergaderings",
            "heeftverslagVergadering",
            "betreftStemming",
            "heeftCategorie",
            "hoortbijDossiers",
            "heeftIndieners",
        },
        allowSetters = true
    )
    private Set<Raadsstuk> heeftRaadsstuks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftTaakvelds")
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Set<Kostenplaats> heeftKostenplaats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Taakveld id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Taakveld naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Raadsstuk> getHeeftRaadsstuks() {
        return this.heeftRaadsstuks;
    }

    public void setHeeftRaadsstuks(Set<Raadsstuk> raadsstuks) {
        if (this.heeftRaadsstuks != null) {
            this.heeftRaadsstuks.forEach(i -> i.setHeeftTaakveld(null));
        }
        if (raadsstuks != null) {
            raadsstuks.forEach(i -> i.setHeeftTaakveld(this));
        }
        this.heeftRaadsstuks = raadsstuks;
    }

    public Taakveld heeftRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setHeeftRaadsstuks(raadsstuks);
        return this;
    }

    public Taakveld addHeeftRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftRaadsstuks.add(raadsstuk);
        raadsstuk.setHeeftTaakveld(this);
        return this;
    }

    public Taakveld removeHeeftRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftRaadsstuks.remove(raadsstuk);
        raadsstuk.setHeeftTaakveld(null);
        return this;
    }

    public Set<Kostenplaats> getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        if (this.heeftKostenplaats != null) {
            this.heeftKostenplaats.forEach(i -> i.removeHeeftTaakveld(this));
        }
        if (kostenplaats != null) {
            kostenplaats.forEach(i -> i.addHeeftTaakveld(this));
        }
        this.heeftKostenplaats = kostenplaats;
    }

    public Taakveld heeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Taakveld addHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.add(kostenplaats);
        kostenplaats.getHeeftTaakvelds().add(this);
        return this;
    }

    public Taakveld removeHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.remove(kostenplaats);
        kostenplaats.getHeeftTaakvelds().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taakveld)) {
            return false;
        }
        return getId() != null && getId().equals(((Taakveld) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taakveld{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
