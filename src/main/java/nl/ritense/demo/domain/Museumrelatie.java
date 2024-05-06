package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Museumrelatie.
 */
@Entity
@Table(name = "museumrelatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Museumrelatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "relatiesoort")
    private String relatiesoort;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voorMuseumrelatie")
    @JsonIgnoreProperties(
        value = {
            "bestaatuitActiviteits",
            "binnenprogrammaPlans",
            "heeftKostenplaats",
            "voorProgrammasoorts",
            "voorMuseumrelatie",
            "hoortbijRaadsstuks",
        },
        allowSetters = true
    )
    private Set<Programma> voorProgrammas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_museumrelatie__valtbinnen_doelgroep",
        joinColumns = @JoinColumn(name = "museumrelatie_id"),
        inverseJoinColumns = @JoinColumn(name = "valtbinnen_doelgroep_id")
    )
    @JsonIgnoreProperties(
        value = { "bestaatuitDoelgroeps", "bestaatuitDoelgroep2", "valtbinnendoelgroepClients", "valtbinnenMuseumrelaties" },
        allowSetters = true
    )
    private Set<Doelgroep> valtbinnenDoelgroeps = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "versturenaanMuseumrelaties")
    @JsonIgnoreProperties(value = { "versturenaanMuseumrelaties" }, allowSetters = true)
    private Set<Mailing> versturenaanMailings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Museumrelatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelatiesoort() {
        return this.relatiesoort;
    }

    public Museumrelatie relatiesoort(String relatiesoort) {
        this.setRelatiesoort(relatiesoort);
        return this;
    }

    public void setRelatiesoort(String relatiesoort) {
        this.relatiesoort = relatiesoort;
    }

    public Set<Programma> getVoorProgrammas() {
        return this.voorProgrammas;
    }

    public void setVoorProgrammas(Set<Programma> programmas) {
        if (this.voorProgrammas != null) {
            this.voorProgrammas.forEach(i -> i.setVoorMuseumrelatie(null));
        }
        if (programmas != null) {
            programmas.forEach(i -> i.setVoorMuseumrelatie(this));
        }
        this.voorProgrammas = programmas;
    }

    public Museumrelatie voorProgrammas(Set<Programma> programmas) {
        this.setVoorProgrammas(programmas);
        return this;
    }

    public Museumrelatie addVoorProgramma(Programma programma) {
        this.voorProgrammas.add(programma);
        programma.setVoorMuseumrelatie(this);
        return this;
    }

    public Museumrelatie removeVoorProgramma(Programma programma) {
        this.voorProgrammas.remove(programma);
        programma.setVoorMuseumrelatie(null);
        return this;
    }

    public Set<Doelgroep> getValtbinnenDoelgroeps() {
        return this.valtbinnenDoelgroeps;
    }

    public void setValtbinnenDoelgroeps(Set<Doelgroep> doelgroeps) {
        this.valtbinnenDoelgroeps = doelgroeps;
    }

    public Museumrelatie valtbinnenDoelgroeps(Set<Doelgroep> doelgroeps) {
        this.setValtbinnenDoelgroeps(doelgroeps);
        return this;
    }

    public Museumrelatie addValtbinnenDoelgroep(Doelgroep doelgroep) {
        this.valtbinnenDoelgroeps.add(doelgroep);
        return this;
    }

    public Museumrelatie removeValtbinnenDoelgroep(Doelgroep doelgroep) {
        this.valtbinnenDoelgroeps.remove(doelgroep);
        return this;
    }

    public Set<Mailing> getVersturenaanMailings() {
        return this.versturenaanMailings;
    }

    public void setVersturenaanMailings(Set<Mailing> mailings) {
        if (this.versturenaanMailings != null) {
            this.versturenaanMailings.forEach(i -> i.removeVersturenaanMuseumrelatie(this));
        }
        if (mailings != null) {
            mailings.forEach(i -> i.addVersturenaanMuseumrelatie(this));
        }
        this.versturenaanMailings = mailings;
    }

    public Museumrelatie versturenaanMailings(Set<Mailing> mailings) {
        this.setVersturenaanMailings(mailings);
        return this;
    }

    public Museumrelatie addVersturenaanMailing(Mailing mailing) {
        this.versturenaanMailings.add(mailing);
        mailing.getVersturenaanMuseumrelaties().add(this);
        return this;
    }

    public Museumrelatie removeVersturenaanMailing(Mailing mailing) {
        this.versturenaanMailings.remove(mailing);
        mailing.getVersturenaanMuseumrelaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Museumrelatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Museumrelatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Museumrelatie{" +
            "id=" + getId() +
            ", relatiesoort='" + getRelatiesoort() + "'" +
            "}";
    }
}
