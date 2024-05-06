package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Thema.
 */
@Entity
@Table(name = "thema")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Thema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subthemaThema2")
    @JsonIgnoreProperties(value = { "subthemaThemas", "subthemaThema2", "heeftthemaRegelteksts" }, allowSetters = true)
    private Set<Thema> subthemaThemas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subthemaThemas", "subthemaThema2", "heeftthemaRegelteksts" }, allowSetters = true)
    private Thema subthemaThema2;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftthemaThemas")
    @JsonIgnoreProperties(
        value = {
            "werkingsgebiedRegelteksts",
            "isgerelateerdRegelteksts",
            "heeftthemaThemas",
            "heeftidealisatieIdealisaties",
            "werkingsgebiedLocaties",
            "bevatOmgevingsdocument",
            "werkingsgebiedRegeltekst2",
            "isgerelateerdRegeltekst2",
        },
        allowSetters = true
    )
    private Set<Regeltekst> heeftthemaRegelteksts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Thema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Thema naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Thema omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Thema> getSubthemaThemas() {
        return this.subthemaThemas;
    }

    public void setSubthemaThemas(Set<Thema> themas) {
        if (this.subthemaThemas != null) {
            this.subthemaThemas.forEach(i -> i.setSubthemaThema2(null));
        }
        if (themas != null) {
            themas.forEach(i -> i.setSubthemaThema2(this));
        }
        this.subthemaThemas = themas;
    }

    public Thema subthemaThemas(Set<Thema> themas) {
        this.setSubthemaThemas(themas);
        return this;
    }

    public Thema addSubthemaThema(Thema thema) {
        this.subthemaThemas.add(thema);
        thema.setSubthemaThema2(this);
        return this;
    }

    public Thema removeSubthemaThema(Thema thema) {
        this.subthemaThemas.remove(thema);
        thema.setSubthemaThema2(null);
        return this;
    }

    public Thema getSubthemaThema2() {
        return this.subthemaThema2;
    }

    public void setSubthemaThema2(Thema thema) {
        this.subthemaThema2 = thema;
    }

    public Thema subthemaThema2(Thema thema) {
        this.setSubthemaThema2(thema);
        return this;
    }

    public Set<Regeltekst> getHeeftthemaRegelteksts() {
        return this.heeftthemaRegelteksts;
    }

    public void setHeeftthemaRegelteksts(Set<Regeltekst> regelteksts) {
        if (this.heeftthemaRegelteksts != null) {
            this.heeftthemaRegelteksts.forEach(i -> i.removeHeeftthemaThema(this));
        }
        if (regelteksts != null) {
            regelteksts.forEach(i -> i.addHeeftthemaThema(this));
        }
        this.heeftthemaRegelteksts = regelteksts;
    }

    public Thema heeftthemaRegelteksts(Set<Regeltekst> regelteksts) {
        this.setHeeftthemaRegelteksts(regelteksts);
        return this;
    }

    public Thema addHeeftthemaRegeltekst(Regeltekst regeltekst) {
        this.heeftthemaRegelteksts.add(regeltekst);
        regeltekst.getHeeftthemaThemas().add(this);
        return this;
    }

    public Thema removeHeeftthemaRegeltekst(Regeltekst regeltekst) {
        this.heeftthemaRegelteksts.remove(regeltekst);
        regeltekst.getHeeftthemaThemas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thema)) {
            return false;
        }
        return getId() != null && getId().equals(((Thema) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thema{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
