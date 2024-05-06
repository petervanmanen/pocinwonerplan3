package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Magazijnlocatie.
 */
@Entity
@Table(name = "magazijnlocatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Magazijnlocatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "key", length = 20)
    private String key;

    @Size(max = 8)
    @Column(name = "vaknummer", length = 8)
    private String vaknummer;

    @Size(max = 8)
    @Column(name = "volgletter", length = 8)
    private String volgletter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftMagazijnlocaties", "heeftKasts", "heeftDepot", "istevindeninVindplaats" }, allowSetters = true)
    private Stelling heeftStelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staatopMagazijnlocatie")
    @JsonIgnoreProperties(value = { "staatopMagazijnlocatie", "zitinArtefacts" }, allowSetters = true)
    private Set<Doos> staatopDoos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Magazijnlocatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public Magazijnlocatie key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVaknummer() {
        return this.vaknummer;
    }

    public Magazijnlocatie vaknummer(String vaknummer) {
        this.setVaknummer(vaknummer);
        return this;
    }

    public void setVaknummer(String vaknummer) {
        this.vaknummer = vaknummer;
    }

    public String getVolgletter() {
        return this.volgletter;
    }

    public Magazijnlocatie volgletter(String volgletter) {
        this.setVolgletter(volgletter);
        return this;
    }

    public void setVolgletter(String volgletter) {
        this.volgletter = volgletter;
    }

    public Stelling getHeeftStelling() {
        return this.heeftStelling;
    }

    public void setHeeftStelling(Stelling stelling) {
        this.heeftStelling = stelling;
    }

    public Magazijnlocatie heeftStelling(Stelling stelling) {
        this.setHeeftStelling(stelling);
        return this;
    }

    public Set<Doos> getStaatopDoos() {
        return this.staatopDoos;
    }

    public void setStaatopDoos(Set<Doos> doos) {
        if (this.staatopDoos != null) {
            this.staatopDoos.forEach(i -> i.setStaatopMagazijnlocatie(null));
        }
        if (doos != null) {
            doos.forEach(i -> i.setStaatopMagazijnlocatie(this));
        }
        this.staatopDoos = doos;
    }

    public Magazijnlocatie staatopDoos(Set<Doos> doos) {
        this.setStaatopDoos(doos);
        return this;
    }

    public Magazijnlocatie addStaatopDoos(Doos doos) {
        this.staatopDoos.add(doos);
        doos.setStaatopMagazijnlocatie(this);
        return this;
    }

    public Magazijnlocatie removeStaatopDoos(Doos doos) {
        this.staatopDoos.remove(doos);
        doos.setStaatopMagazijnlocatie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Magazijnlocatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Magazijnlocatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Magazijnlocatie{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", vaknummer='" + getVaknummer() + "'" +
            ", volgletter='" + getVolgletter() + "'" +
            "}";
    }
}
