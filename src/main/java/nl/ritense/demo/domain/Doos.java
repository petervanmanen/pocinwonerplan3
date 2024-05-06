package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doos.
 */
@Entity
@Table(name = "doos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "doosnummer")
    private String doosnummer;

    @Column(name = "herkomst")
    private String herkomst;

    @Column(name = "inhoud")
    private String inhoud;

    @Column(name = "key")
    private String key;

    @Column(name = "keymagazijnlocatie")
    private String keymagazijnlocatie;

    @Column(name = "projectcd")
    private String projectcd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftStelling", "staatopDoos" }, allowSetters = true)
    private Magazijnlocatie staatopMagazijnlocatie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zitinDoos")
    @JsonIgnoreProperties(value = { "zitinDoos", "isvansoortArtefactsoort", "bevatVondst" }, allowSetters = true)
    private Set<Artefact> zitinArtefacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoosnummer() {
        return this.doosnummer;
    }

    public Doos doosnummer(String doosnummer) {
        this.setDoosnummer(doosnummer);
        return this;
    }

    public void setDoosnummer(String doosnummer) {
        this.doosnummer = doosnummer;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Doos herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getInhoud() {
        return this.inhoud;
    }

    public Doos inhoud(String inhoud) {
        this.setInhoud(inhoud);
        return this;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public String getKey() {
        return this.key;
    }

    public Doos key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeymagazijnlocatie() {
        return this.keymagazijnlocatie;
    }

    public Doos keymagazijnlocatie(String keymagazijnlocatie) {
        this.setKeymagazijnlocatie(keymagazijnlocatie);
        return this;
    }

    public void setKeymagazijnlocatie(String keymagazijnlocatie) {
        this.keymagazijnlocatie = keymagazijnlocatie;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Doos projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public Magazijnlocatie getStaatopMagazijnlocatie() {
        return this.staatopMagazijnlocatie;
    }

    public void setStaatopMagazijnlocatie(Magazijnlocatie magazijnlocatie) {
        this.staatopMagazijnlocatie = magazijnlocatie;
    }

    public Doos staatopMagazijnlocatie(Magazijnlocatie magazijnlocatie) {
        this.setStaatopMagazijnlocatie(magazijnlocatie);
        return this;
    }

    public Set<Artefact> getZitinArtefacts() {
        return this.zitinArtefacts;
    }

    public void setZitinArtefacts(Set<Artefact> artefacts) {
        if (this.zitinArtefacts != null) {
            this.zitinArtefacts.forEach(i -> i.setZitinDoos(null));
        }
        if (artefacts != null) {
            artefacts.forEach(i -> i.setZitinDoos(this));
        }
        this.zitinArtefacts = artefacts;
    }

    public Doos zitinArtefacts(Set<Artefact> artefacts) {
        this.setZitinArtefacts(artefacts);
        return this;
    }

    public Doos addZitinArtefact(Artefact artefact) {
        this.zitinArtefacts.add(artefact);
        artefact.setZitinDoos(this);
        return this;
    }

    public Doos removeZitinArtefact(Artefact artefact) {
        this.zitinArtefacts.remove(artefact);
        artefact.setZitinDoos(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doos)) {
            return false;
        }
        return getId() != null && getId().equals(((Doos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doos{" +
            "id=" + getId() +
            ", doosnummer='" + getDoosnummer() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", inhoud='" + getInhoud() + "'" +
            ", key='" + getKey() + "'" +
            ", keymagazijnlocatie='" + getKeymagazijnlocatie() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            "}";
    }
}
