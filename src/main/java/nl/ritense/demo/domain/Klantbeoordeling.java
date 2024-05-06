package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Klantbeoordeling.
 */
@Entity
@Table(name = "klantbeoordeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Klantbeoordeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "beoordeling", length = 10)
    private String beoordeling;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "contactopnemen")
    private Boolean contactopnemen;

    @Column(name = "ddbeoordeling")
    private LocalDate ddbeoordeling;

    @Size(max = 100)
    @Column(name = "kanaal", length = 100)
    private String kanaal;

    @Column(name = "onderwerp")
    private String onderwerp;

    @Column(name = "subcategorie")
    private String subcategorie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKlantbeoordeling")
    @JsonIgnoreProperties(value = { "heeftKlantbeoordeling" }, allowSetters = true)
    private Set<Klantbeoordelingreden> heeftKlantbeoordelingredens = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "heeftproductProducttype",
            "heeftKlantbeoordeling",
            "heeftHeffing",
            "heeftbetalingBetalings",
            "heeftStatuses",
            "betreftProject",
            "isvanZaaktype",
            "kentDocuments",
            "afhandelendmedewerkerMedewerkers",
            "leidttotVerzoek",
            "heeftSubsidie",
            "betreftAanbesteding",
            "heeftbetrekkingopBalieafspraaks",
            "isuitkomstvanBesluits",
            "heeftbetrekkingopKlantcontacts",
            "heeftGrondslags",
            "uitgevoerdbinnenBedrijfsproces",
            "oefentuitBetrokkenes",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftKlantbeoordeling")
    private Zaak heeftZaak;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "isMedewerker", "doetKlantbeoordelings", "oefentuitZaaks", "heeftklantcontactenKlantcontacts" },
        allowSetters = true
    )
    private Betrokkene doetBetrokkene;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Klantbeoordeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeoordeling() {
        return this.beoordeling;
    }

    public Klantbeoordeling beoordeling(String beoordeling) {
        this.setBeoordeling(beoordeling);
        return this;
    }

    public void setBeoordeling(String beoordeling) {
        this.beoordeling = beoordeling;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Klantbeoordeling categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Boolean getContactopnemen() {
        return this.contactopnemen;
    }

    public Klantbeoordeling contactopnemen(Boolean contactopnemen) {
        this.setContactopnemen(contactopnemen);
        return this;
    }

    public void setContactopnemen(Boolean contactopnemen) {
        this.contactopnemen = contactopnemen;
    }

    public LocalDate getDdbeoordeling() {
        return this.ddbeoordeling;
    }

    public Klantbeoordeling ddbeoordeling(LocalDate ddbeoordeling) {
        this.setDdbeoordeling(ddbeoordeling);
        return this;
    }

    public void setDdbeoordeling(LocalDate ddbeoordeling) {
        this.ddbeoordeling = ddbeoordeling;
    }

    public String getKanaal() {
        return this.kanaal;
    }

    public Klantbeoordeling kanaal(String kanaal) {
        this.setKanaal(kanaal);
        return this;
    }

    public void setKanaal(String kanaal) {
        this.kanaal = kanaal;
    }

    public String getOnderwerp() {
        return this.onderwerp;
    }

    public Klantbeoordeling onderwerp(String onderwerp) {
        this.setOnderwerp(onderwerp);
        return this;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public String getSubcategorie() {
        return this.subcategorie;
    }

    public Klantbeoordeling subcategorie(String subcategorie) {
        this.setSubcategorie(subcategorie);
        return this;
    }

    public void setSubcategorie(String subcategorie) {
        this.subcategorie = subcategorie;
    }

    public Set<Klantbeoordelingreden> getHeeftKlantbeoordelingredens() {
        return this.heeftKlantbeoordelingredens;
    }

    public void setHeeftKlantbeoordelingredens(Set<Klantbeoordelingreden> klantbeoordelingredens) {
        if (this.heeftKlantbeoordelingredens != null) {
            this.heeftKlantbeoordelingredens.forEach(i -> i.setHeeftKlantbeoordeling(null));
        }
        if (klantbeoordelingredens != null) {
            klantbeoordelingredens.forEach(i -> i.setHeeftKlantbeoordeling(this));
        }
        this.heeftKlantbeoordelingredens = klantbeoordelingredens;
    }

    public Klantbeoordeling heeftKlantbeoordelingredens(Set<Klantbeoordelingreden> klantbeoordelingredens) {
        this.setHeeftKlantbeoordelingredens(klantbeoordelingredens);
        return this;
    }

    public Klantbeoordeling addHeeftKlantbeoordelingreden(Klantbeoordelingreden klantbeoordelingreden) {
        this.heeftKlantbeoordelingredens.add(klantbeoordelingreden);
        klantbeoordelingreden.setHeeftKlantbeoordeling(this);
        return this;
    }

    public Klantbeoordeling removeHeeftKlantbeoordelingreden(Klantbeoordelingreden klantbeoordelingreden) {
        this.heeftKlantbeoordelingredens.remove(klantbeoordelingreden);
        klantbeoordelingreden.setHeeftKlantbeoordeling(null);
        return this;
    }

    public Zaak getHeeftZaak() {
        return this.heeftZaak;
    }

    public void setHeeftZaak(Zaak zaak) {
        if (this.heeftZaak != null) {
            this.heeftZaak.setHeeftKlantbeoordeling(null);
        }
        if (zaak != null) {
            zaak.setHeeftKlantbeoordeling(this);
        }
        this.heeftZaak = zaak;
    }

    public Klantbeoordeling heeftZaak(Zaak zaak) {
        this.setHeeftZaak(zaak);
        return this;
    }

    public Betrokkene getDoetBetrokkene() {
        return this.doetBetrokkene;
    }

    public void setDoetBetrokkene(Betrokkene betrokkene) {
        this.doetBetrokkene = betrokkene;
    }

    public Klantbeoordeling doetBetrokkene(Betrokkene betrokkene) {
        this.setDoetBetrokkene(betrokkene);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Klantbeoordeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Klantbeoordeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Klantbeoordeling{" +
            "id=" + getId() +
            ", beoordeling='" + getBeoordeling() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", contactopnemen='" + getContactopnemen() + "'" +
            ", ddbeoordeling='" + getDdbeoordeling() + "'" +
            ", kanaal='" + getKanaal() + "'" +
            ", onderwerp='" + getOnderwerp() + "'" +
            ", subcategorie='" + getSubcategorie() + "'" +
            "}";
    }
}
