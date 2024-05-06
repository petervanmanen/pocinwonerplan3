package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Gemeente.
 */
@Entity
@Table(name = "gemeente")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gemeente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "gemeentecode")
    private String gemeentecode;

    @Column(name = "gemeentenaam")
    private String gemeentenaam;

    @Column(name = "gemeentenaamnen")
    private String gemeentenaamnen;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "versie")
    private String versie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isgekoppeldaanGemeente")
    @JsonIgnoreProperties(value = { "isgekoppeldaanGemeente" }, allowSetters = true)
    private Set<Asielstatushouder> isgekoppeldaanAsielstatushouders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gemeente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Gemeente datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Gemeente datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Gemeente datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Gemeente datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Gemeente geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGemeentecode() {
        return this.gemeentecode;
    }

    public Gemeente gemeentecode(String gemeentecode) {
        this.setGemeentecode(gemeentecode);
        return this;
    }

    public void setGemeentecode(String gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public String getGemeentenaam() {
        return this.gemeentenaam;
    }

    public Gemeente gemeentenaam(String gemeentenaam) {
        this.setGemeentenaam(gemeentenaam);
        return this;
    }

    public void setGemeentenaam(String gemeentenaam) {
        this.gemeentenaam = gemeentenaam;
    }

    public String getGemeentenaamnen() {
        return this.gemeentenaamnen;
    }

    public Gemeente gemeentenaamnen(String gemeentenaamnen) {
        this.setGemeentenaamnen(gemeentenaamnen);
        return this;
    }

    public void setGemeentenaamnen(String gemeentenaamnen) {
        this.gemeentenaamnen = gemeentenaamnen;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Gemeente geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Gemeente identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Gemeente inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getVersie() {
        return this.versie;
    }

    public Gemeente versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public Set<Asielstatushouder> getIsgekoppeldaanAsielstatushouders() {
        return this.isgekoppeldaanAsielstatushouders;
    }

    public void setIsgekoppeldaanAsielstatushouders(Set<Asielstatushouder> asielstatushouders) {
        if (this.isgekoppeldaanAsielstatushouders != null) {
            this.isgekoppeldaanAsielstatushouders.forEach(i -> i.setIsgekoppeldaanGemeente(null));
        }
        if (asielstatushouders != null) {
            asielstatushouders.forEach(i -> i.setIsgekoppeldaanGemeente(this));
        }
        this.isgekoppeldaanAsielstatushouders = asielstatushouders;
    }

    public Gemeente isgekoppeldaanAsielstatushouders(Set<Asielstatushouder> asielstatushouders) {
        this.setIsgekoppeldaanAsielstatushouders(asielstatushouders);
        return this;
    }

    public Gemeente addIsgekoppeldaanAsielstatushouder(Asielstatushouder asielstatushouder) {
        this.isgekoppeldaanAsielstatushouders.add(asielstatushouder);
        asielstatushouder.setIsgekoppeldaanGemeente(this);
        return this;
    }

    public Gemeente removeIsgekoppeldaanAsielstatushouder(Asielstatushouder asielstatushouder) {
        this.isgekoppeldaanAsielstatushouders.remove(asielstatushouder);
        asielstatushouder.setIsgekoppeldaanGemeente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gemeente)) {
            return false;
        }
        return getId() != null && getId().equals(((Gemeente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gemeente{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", gemeentecode='" + getGemeentecode() + "'" +
            ", gemeentenaam='" + getGemeentenaam() + "'" +
            ", gemeentenaamnen='" + getGemeentenaamnen() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
