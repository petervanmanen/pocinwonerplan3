package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Agendapunt.
 */
@Entity
@Table(name = "agendapunt")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agendapunt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "titel")
    private String titel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftverslagRaadsstuk", "heeftAgendapunts", "heeftRaadscommissie", "wordtbehandeldinRaadsstuks" },
        allowSetters = true
    )
    private Vergadering heeftVergadering;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoortbijAgendapunt")
    @JsonIgnoreProperties(value = { "betreftRaadsstuk", "hoortbijAgendapunt" }, allowSetters = true)
    private Set<Stemming> hoortbijStemmings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "behandeltAgendapunts")
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
    private Set<Raadsstuk> behandeltRaadsstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agendapunt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Agendapunt nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Agendapunt omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTitel() {
        return this.titel;
    }

    public Agendapunt titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Vergadering getHeeftVergadering() {
        return this.heeftVergadering;
    }

    public void setHeeftVergadering(Vergadering vergadering) {
        this.heeftVergadering = vergadering;
    }

    public Agendapunt heeftVergadering(Vergadering vergadering) {
        this.setHeeftVergadering(vergadering);
        return this;
    }

    public Set<Stemming> getHoortbijStemmings() {
        return this.hoortbijStemmings;
    }

    public void setHoortbijStemmings(Set<Stemming> stemmings) {
        if (this.hoortbijStemmings != null) {
            this.hoortbijStemmings.forEach(i -> i.setHoortbijAgendapunt(null));
        }
        if (stemmings != null) {
            stemmings.forEach(i -> i.setHoortbijAgendapunt(this));
        }
        this.hoortbijStemmings = stemmings;
    }

    public Agendapunt hoortbijStemmings(Set<Stemming> stemmings) {
        this.setHoortbijStemmings(stemmings);
        return this;
    }

    public Agendapunt addHoortbijStemming(Stemming stemming) {
        this.hoortbijStemmings.add(stemming);
        stemming.setHoortbijAgendapunt(this);
        return this;
    }

    public Agendapunt removeHoortbijStemming(Stemming stemming) {
        this.hoortbijStemmings.remove(stemming);
        stemming.setHoortbijAgendapunt(null);
        return this;
    }

    public Set<Raadsstuk> getBehandeltRaadsstuks() {
        return this.behandeltRaadsstuks;
    }

    public void setBehandeltRaadsstuks(Set<Raadsstuk> raadsstuks) {
        if (this.behandeltRaadsstuks != null) {
            this.behandeltRaadsstuks.forEach(i -> i.removeBehandeltAgendapunt(this));
        }
        if (raadsstuks != null) {
            raadsstuks.forEach(i -> i.addBehandeltAgendapunt(this));
        }
        this.behandeltRaadsstuks = raadsstuks;
    }

    public Agendapunt behandeltRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setBehandeltRaadsstuks(raadsstuks);
        return this;
    }

    public Agendapunt addBehandeltRaadsstuk(Raadsstuk raadsstuk) {
        this.behandeltRaadsstuks.add(raadsstuk);
        raadsstuk.getBehandeltAgendapunts().add(this);
        return this;
    }

    public Agendapunt removeBehandeltRaadsstuk(Raadsstuk raadsstuk) {
        this.behandeltRaadsstuks.remove(raadsstuk);
        raadsstuk.getBehandeltAgendapunts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agendapunt)) {
            return false;
        }
        return getId() != null && getId().equals(((Agendapunt) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agendapunt{" +
            "id=" + getId() +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
