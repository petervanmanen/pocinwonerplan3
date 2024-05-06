package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_dossier__hoortbij_raadsstuk",
        joinColumns = @JoinColumn(name = "dossier_id"),
        inverseJoinColumns = @JoinColumn(name = "hoortbij_raadsstuk_id")
    )
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
    private Set<Raadsstuk> hoortbijRaadsstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Dossier naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Raadsstuk> getHoortbijRaadsstuks() {
        return this.hoortbijRaadsstuks;
    }

    public void setHoortbijRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.hoortbijRaadsstuks = raadsstuks;
    }

    public Dossier hoortbijRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setHoortbijRaadsstuks(raadsstuks);
        return this;
    }

    public Dossier addHoortbijRaadsstuk(Raadsstuk raadsstuk) {
        this.hoortbijRaadsstuks.add(raadsstuk);
        return this;
    }

    public Dossier removeHoortbijRaadsstuk(Raadsstuk raadsstuk) {
        this.hoortbijRaadsstuks.remove(raadsstuk);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return getId() != null && getId().equals(((Dossier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
