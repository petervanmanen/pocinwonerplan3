package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Heffing.
 */
@Entity
@Table(name = "heffing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Heffing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag")
    private String bedrag;

    @Column(name = "code")
    private String code;

    @Column(name = "datumindiening")
    private String datumindiening;

    @Column(name = "gefactureerd")
    private Boolean gefactureerd;

    @Column(name = "inrekening")
    private String inrekening;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "runnummer")
    private String runnummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vermeldinHeffingsverordening", "heeftZaaktype", "heeftgrondslagHeffings" }, allowSetters = true)
    private Heffinggrondslag heeftgrondslagHeffinggrondslag;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftHeffing")
    private Zaak heeftZaak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Heffing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedrag() {
        return this.bedrag;
    }

    public Heffing bedrag(String bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public String getCode() {
        return this.code;
    }

    public Heffing code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatumindiening() {
        return this.datumindiening;
    }

    public Heffing datumindiening(String datumindiening) {
        this.setDatumindiening(datumindiening);
        return this;
    }

    public void setDatumindiening(String datumindiening) {
        this.datumindiening = datumindiening;
    }

    public Boolean getGefactureerd() {
        return this.gefactureerd;
    }

    public Heffing gefactureerd(Boolean gefactureerd) {
        this.setGefactureerd(gefactureerd);
        return this;
    }

    public void setGefactureerd(Boolean gefactureerd) {
        this.gefactureerd = gefactureerd;
    }

    public String getInrekening() {
        return this.inrekening;
    }

    public Heffing inrekening(String inrekening) {
        this.setInrekening(inrekening);
        return this;
    }

    public void setInrekening(String inrekening) {
        this.inrekening = inrekening;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Heffing nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getRunnummer() {
        return this.runnummer;
    }

    public Heffing runnummer(String runnummer) {
        this.setRunnummer(runnummer);
        return this;
    }

    public void setRunnummer(String runnummer) {
        this.runnummer = runnummer;
    }

    public Heffinggrondslag getHeeftgrondslagHeffinggrondslag() {
        return this.heeftgrondslagHeffinggrondslag;
    }

    public void setHeeftgrondslagHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        this.heeftgrondslagHeffinggrondslag = heffinggrondslag;
    }

    public Heffing heeftgrondslagHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        this.setHeeftgrondslagHeffinggrondslag(heffinggrondslag);
        return this;
    }

    public Zaak getHeeftZaak() {
        return this.heeftZaak;
    }

    public void setHeeftZaak(Zaak zaak) {
        if (this.heeftZaak != null) {
            this.heeftZaak.setHeeftHeffing(null);
        }
        if (zaak != null) {
            zaak.setHeeftHeffing(this);
        }
        this.heeftZaak = zaak;
    }

    public Heffing heeftZaak(Zaak zaak) {
        this.setHeeftZaak(zaak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Heffing)) {
            return false;
        }
        return getId() != null && getId().equals(((Heffing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Heffing{" +
            "id=" + getId() +
            ", bedrag='" + getBedrag() + "'" +
            ", code='" + getCode() + "'" +
            ", datumindiening='" + getDatumindiening() + "'" +
            ", gefactureerd='" + getGefactureerd() + "'" +
            ", inrekening='" + getInrekening() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", runnummer='" + getRunnummer() + "'" +
            "}";
    }
}
