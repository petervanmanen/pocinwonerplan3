package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Raadscommissie.
 */
@Entity
@Table(name = "raadscommissie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Raadscommissie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRaadscommissie")
    @JsonIgnoreProperties(
        value = { "heeftverslagRaadsstuk", "heeftAgendapunts", "heeftRaadscommissie", "wordtbehandeldinRaadsstuks" },
        allowSetters = true
    )
    private Set<Vergadering> heeftVergaderings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "islidvanRaadscommissies")
    @JsonIgnoreProperties(value = { "islidvanRaadscommissies", "isIndiener" }, allowSetters = true)
    private Set<Raadslid> islidvanRaadslids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Raadscommissie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Raadscommissie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Vergadering> getHeeftVergaderings() {
        return this.heeftVergaderings;
    }

    public void setHeeftVergaderings(Set<Vergadering> vergaderings) {
        if (this.heeftVergaderings != null) {
            this.heeftVergaderings.forEach(i -> i.setHeeftRaadscommissie(null));
        }
        if (vergaderings != null) {
            vergaderings.forEach(i -> i.setHeeftRaadscommissie(this));
        }
        this.heeftVergaderings = vergaderings;
    }

    public Raadscommissie heeftVergaderings(Set<Vergadering> vergaderings) {
        this.setHeeftVergaderings(vergaderings);
        return this;
    }

    public Raadscommissie addHeeftVergadering(Vergadering vergadering) {
        this.heeftVergaderings.add(vergadering);
        vergadering.setHeeftRaadscommissie(this);
        return this;
    }

    public Raadscommissie removeHeeftVergadering(Vergadering vergadering) {
        this.heeftVergaderings.remove(vergadering);
        vergadering.setHeeftRaadscommissie(null);
        return this;
    }

    public Set<Raadslid> getIslidvanRaadslids() {
        return this.islidvanRaadslids;
    }

    public void setIslidvanRaadslids(Set<Raadslid> raadslids) {
        if (this.islidvanRaadslids != null) {
            this.islidvanRaadslids.forEach(i -> i.removeIslidvanRaadscommissie(this));
        }
        if (raadslids != null) {
            raadslids.forEach(i -> i.addIslidvanRaadscommissie(this));
        }
        this.islidvanRaadslids = raadslids;
    }

    public Raadscommissie islidvanRaadslids(Set<Raadslid> raadslids) {
        this.setIslidvanRaadslids(raadslids);
        return this;
    }

    public Raadscommissie addIslidvanRaadslid(Raadslid raadslid) {
        this.islidvanRaadslids.add(raadslid);
        raadslid.getIslidvanRaadscommissies().add(this);
        return this;
    }

    public Raadscommissie removeIslidvanRaadslid(Raadslid raadslid) {
        this.islidvanRaadslids.remove(raadslid);
        raadslid.getIslidvanRaadscommissies().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Raadscommissie)) {
            return false;
        }
        return getId() != null && getId().equals(((Raadscommissie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Raadscommissie{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
