package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Heffinggrondslag.
 */
@Entity
@Table(name = "heffinggrondslag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Heffinggrondslag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "domein")
    private String domein;

    @Column(name = "hoofdstuk")
    private String hoofdstuk;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Size(max = 8)
    @Column(name = "paragraaf", length = 8)
    private String paragraaf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vermeldinHeffinggrondslags" }, allowSetters = true)
    private Heffingsverordening vermeldinHeffingsverordening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftProducttype",
            "heeftHeffinggrondslags",
            "heeftStatustypes",
            "betreftProduct",
            "heeftBedrijfsprocestype",
            "isverantwoordelijkevoorMedewerker",
            "startDiensts",
            "isvanZaaks",
            "isaanleidingvoorFormuliersoorts",
        },
        allowSetters = true
    )
    private Zaaktype heeftZaaktype;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftgrondslagHeffinggrondslag")
    @JsonIgnoreProperties(value = { "heeftgrondslagHeffinggrondslag", "heeftZaak" }, allowSetters = true)
    private Set<Heffing> heeftgrondslagHeffings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Heffinggrondslag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Heffinggrondslag bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getDomein() {
        return this.domein;
    }

    public Heffinggrondslag domein(String domein) {
        this.setDomein(domein);
        return this;
    }

    public void setDomein(String domein) {
        this.domein = domein;
    }

    public String getHoofdstuk() {
        return this.hoofdstuk;
    }

    public Heffinggrondslag hoofdstuk(String hoofdstuk) {
        this.setHoofdstuk(hoofdstuk);
        return this;
    }

    public void setHoofdstuk(String hoofdstuk) {
        this.hoofdstuk = hoofdstuk;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Heffinggrondslag omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getParagraaf() {
        return this.paragraaf;
    }

    public Heffinggrondslag paragraaf(String paragraaf) {
        this.setParagraaf(paragraaf);
        return this;
    }

    public void setParagraaf(String paragraaf) {
        this.paragraaf = paragraaf;
    }

    public Heffingsverordening getVermeldinHeffingsverordening() {
        return this.vermeldinHeffingsverordening;
    }

    public void setVermeldinHeffingsverordening(Heffingsverordening heffingsverordening) {
        this.vermeldinHeffingsverordening = heffingsverordening;
    }

    public Heffinggrondslag vermeldinHeffingsverordening(Heffingsverordening heffingsverordening) {
        this.setVermeldinHeffingsverordening(heffingsverordening);
        return this;
    }

    public Zaaktype getHeeftZaaktype() {
        return this.heeftZaaktype;
    }

    public void setHeeftZaaktype(Zaaktype zaaktype) {
        this.heeftZaaktype = zaaktype;
    }

    public Heffinggrondslag heeftZaaktype(Zaaktype zaaktype) {
        this.setHeeftZaaktype(zaaktype);
        return this;
    }

    public Set<Heffing> getHeeftgrondslagHeffings() {
        return this.heeftgrondslagHeffings;
    }

    public void setHeeftgrondslagHeffings(Set<Heffing> heffings) {
        if (this.heeftgrondslagHeffings != null) {
            this.heeftgrondslagHeffings.forEach(i -> i.setHeeftgrondslagHeffinggrondslag(null));
        }
        if (heffings != null) {
            heffings.forEach(i -> i.setHeeftgrondslagHeffinggrondslag(this));
        }
        this.heeftgrondslagHeffings = heffings;
    }

    public Heffinggrondslag heeftgrondslagHeffings(Set<Heffing> heffings) {
        this.setHeeftgrondslagHeffings(heffings);
        return this;
    }

    public Heffinggrondslag addHeeftgrondslagHeffing(Heffing heffing) {
        this.heeftgrondslagHeffings.add(heffing);
        heffing.setHeeftgrondslagHeffinggrondslag(this);
        return this;
    }

    public Heffinggrondslag removeHeeftgrondslagHeffing(Heffing heffing) {
        this.heeftgrondslagHeffings.remove(heffing);
        heffing.setHeeftgrondslagHeffinggrondslag(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Heffinggrondslag)) {
            return false;
        }
        return getId() != null && getId().equals(((Heffinggrondslag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Heffinggrondslag{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", domein='" + getDomein() + "'" +
            ", hoofdstuk='" + getHoofdstuk() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", paragraaf='" + getParagraaf() + "'" +
            "}";
    }
}
