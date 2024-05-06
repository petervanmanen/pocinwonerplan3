package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Raadslid.
 */
@Entity
@Table(name = "raadslid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Raadslid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "datumaanstelling")
    private LocalDate datumaanstelling;

    @Column(name = "datumuittreding")
    private LocalDate datumuittreding;

    @Column(name = "fractie")
    private String fractie;

    @Column(name = "titel")
    private String titel;

    @Column(name = "voornaam")
    private String voornaam;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_raadslid__islidvan_raadscommissie",
        joinColumns = @JoinColumn(name = "raadslid_id"),
        inverseJoinColumns = @JoinColumn(name = "islidvan_raadscommissie_id")
    )
    @JsonIgnoreProperties(value = { "heeftVergaderings", "islidvanRaadslids" }, allowSetters = true)
    private Set<Raadscommissie> islidvanRaadscommissies = new HashSet<>();

    @JsonIgnoreProperties(value = { "isCollegelid", "isRaadslid", "isRechtspersoon", "heeftRaadsstuks" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isRaadslid")
    private Indiener isIndiener;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Raadslid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchternaam() {
        return this.achternaam;
    }

    public Raadslid achternaam(String achternaam) {
        this.setAchternaam(achternaam);
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getDatumaanstelling() {
        return this.datumaanstelling;
    }

    public Raadslid datumaanstelling(LocalDate datumaanstelling) {
        this.setDatumaanstelling(datumaanstelling);
        return this;
    }

    public void setDatumaanstelling(LocalDate datumaanstelling) {
        this.datumaanstelling = datumaanstelling;
    }

    public LocalDate getDatumuittreding() {
        return this.datumuittreding;
    }

    public Raadslid datumuittreding(LocalDate datumuittreding) {
        this.setDatumuittreding(datumuittreding);
        return this;
    }

    public void setDatumuittreding(LocalDate datumuittreding) {
        this.datumuittreding = datumuittreding;
    }

    public String getFractie() {
        return this.fractie;
    }

    public Raadslid fractie(String fractie) {
        this.setFractie(fractie);
        return this;
    }

    public void setFractie(String fractie) {
        this.fractie = fractie;
    }

    public String getTitel() {
        return this.titel;
    }

    public Raadslid titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public Raadslid voornaam(String voornaam) {
        this.setVoornaam(voornaam);
        return this;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public Set<Raadscommissie> getIslidvanRaadscommissies() {
        return this.islidvanRaadscommissies;
    }

    public void setIslidvanRaadscommissies(Set<Raadscommissie> raadscommissies) {
        this.islidvanRaadscommissies = raadscommissies;
    }

    public Raadslid islidvanRaadscommissies(Set<Raadscommissie> raadscommissies) {
        this.setIslidvanRaadscommissies(raadscommissies);
        return this;
    }

    public Raadslid addIslidvanRaadscommissie(Raadscommissie raadscommissie) {
        this.islidvanRaadscommissies.add(raadscommissie);
        return this;
    }

    public Raadslid removeIslidvanRaadscommissie(Raadscommissie raadscommissie) {
        this.islidvanRaadscommissies.remove(raadscommissie);
        return this;
    }

    public Indiener getIsIndiener() {
        return this.isIndiener;
    }

    public void setIsIndiener(Indiener indiener) {
        if (this.isIndiener != null) {
            this.isIndiener.setIsRaadslid(null);
        }
        if (indiener != null) {
            indiener.setIsRaadslid(this);
        }
        this.isIndiener = indiener;
    }

    public Raadslid isIndiener(Indiener indiener) {
        this.setIsIndiener(indiener);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Raadslid)) {
            return false;
        }
        return getId() != null && getId().equals(((Raadslid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Raadslid{" +
            "id=" + getId() +
            ", achternaam='" + getAchternaam() + "'" +
            ", datumaanstelling='" + getDatumaanstelling() + "'" +
            ", datumuittreding='" + getDatumuittreding() + "'" +
            ", fractie='" + getFractie() + "'" +
            ", titel='" + getTitel() + "'" +
            ", voornaam='" + getVoornaam() + "'" +
            "}";
    }
}
