package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Zaaktype.
 */
@Entity
@Table(name = "zaaktype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zaaktype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "archiefcode", length = 20)
    private String archiefcode;

    @Column(name = "datumbegingeldigheidzaaktype")
    private String datumbegingeldigheidzaaktype;

    @Column(name = "datumeindegeldigheidzaaktype")
    private String datumeindegeldigheidzaaktype;

    @Column(name = "doorlooptijdbehandeling")
    private String doorlooptijdbehandeling;

    @Column(name = "indicatiepublicatie")
    private String indicatiepublicatie;

    @Column(name = "publicatietekst")
    private String publicatietekst;

    @Column(name = "servicenormbehandeling")
    private String servicenormbehandeling;

    @Column(name = "trefwoord")
    private String trefwoord;

    @Size(max = 20)
    @Column(name = "vertrouwelijkaanduiding", length = 20)
    private String vertrouwelijkaanduiding;

    @Column(name = "zaakcategorie")
    private String zaakcategorie;

    @Column(name = "zaaktypeomschrijving")
    private String zaaktypeomschrijving;

    @Column(name = "zaaktypeomschrijvinggeneriek")
    private String zaaktypeomschrijvinggeneriek;

    @JsonIgnoreProperties(value = { "heeftproductZaak", "heeftZaaktype", "heeftBedrijfsprocestype" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Producttype heeftProducttype;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftZaaktype")
    @JsonIgnoreProperties(value = { "vermeldinHeffingsverordening", "heeftZaaktype", "heeftgrondslagHeffings" }, allowSetters = true)
    private Set<Heffinggrondslag> heeftHeffinggrondslags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftZaaktype")
    @JsonIgnoreProperties(value = { "heeftZaaktype", "isvanStatuses" }, allowSetters = true)
    private Set<Statustype> heeftStatustypes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftprijsPrijs",
            "betreftZaaktypes",
            "leverancierLeverancier",
            "heeftKostenplaats",
            "valtbinnenOmzetgroeps",
            "valtbinnenProductgroeps",
            "betreftWinkelvoorraaditem",
            "heeftDoelstelling",
            "isopdrachtgeverOpdrachtgever",
            "isopdrachtnemerOpdrachtnemer",
            "betreftBalieverkoops",
            "betreftDiensts",
            "betreftBegrotingregels",
        },
        allowSetters = true
    )
    private Product betreftProduct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "heeftProducttypes", "heeftZaaktypes", "isdeelvanDeelprocestype" }, allowSetters = true)
    private Bedrijfsprocestype heeftBedrijfsprocestype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "ingevoerddoorStremmings",
            "gewijzigddoorStremmings",
            "voertuitSchouwrondes",
            "aanvragerSubsidies",
            "isverantwoordelijkevoorZaaktypes",
            "geleverdviaLeverancier",
            "isBetrokkene",
            "uitgevoerddoorParkeerscans",
            "melderMeldings",
            "uitvoerderMeldings",
            "auteurNotities",
            "behandelaarSubsidies",
            "procesleiderAanbestedings",
            "inhuurGunnings",
            "metBalieafspraaks",
            "isgevoerddoorKlantcontacts",
            "rollenApplicaties",
            "afhandelendmedewerkerZaaks",
        },
        allowSetters = true
    )
    private Medewerker isverantwoordelijkevoorMedewerker;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "startZaaktype")
    @JsonIgnoreProperties(value = { "startZaaktype", "heeftOnderwerp", "betreftProduct" }, allowSetters = true)
    private Set<Dienst> startDiensts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvanZaaktype")
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
    private Set<Zaak> isvanZaaks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "isaanleidingvoorZaaktypes")
    @JsonIgnoreProperties(value = { "heeftveldenFormuliersoortvelds", "isaanleidingvoorZaaktypes" }, allowSetters = true)
    private Set<Formuliersoort> isaanleidingvoorFormuliersoorts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zaaktype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchiefcode() {
        return this.archiefcode;
    }

    public Zaaktype archiefcode(String archiefcode) {
        this.setArchiefcode(archiefcode);
        return this;
    }

    public void setArchiefcode(String archiefcode) {
        this.archiefcode = archiefcode;
    }

    public String getDatumbegingeldigheidzaaktype() {
        return this.datumbegingeldigheidzaaktype;
    }

    public Zaaktype datumbegingeldigheidzaaktype(String datumbegingeldigheidzaaktype) {
        this.setDatumbegingeldigheidzaaktype(datumbegingeldigheidzaaktype);
        return this;
    }

    public void setDatumbegingeldigheidzaaktype(String datumbegingeldigheidzaaktype) {
        this.datumbegingeldigheidzaaktype = datumbegingeldigheidzaaktype;
    }

    public String getDatumeindegeldigheidzaaktype() {
        return this.datumeindegeldigheidzaaktype;
    }

    public Zaaktype datumeindegeldigheidzaaktype(String datumeindegeldigheidzaaktype) {
        this.setDatumeindegeldigheidzaaktype(datumeindegeldigheidzaaktype);
        return this;
    }

    public void setDatumeindegeldigheidzaaktype(String datumeindegeldigheidzaaktype) {
        this.datumeindegeldigheidzaaktype = datumeindegeldigheidzaaktype;
    }

    public String getDoorlooptijdbehandeling() {
        return this.doorlooptijdbehandeling;
    }

    public Zaaktype doorlooptijdbehandeling(String doorlooptijdbehandeling) {
        this.setDoorlooptijdbehandeling(doorlooptijdbehandeling);
        return this;
    }

    public void setDoorlooptijdbehandeling(String doorlooptijdbehandeling) {
        this.doorlooptijdbehandeling = doorlooptijdbehandeling;
    }

    public String getIndicatiepublicatie() {
        return this.indicatiepublicatie;
    }

    public Zaaktype indicatiepublicatie(String indicatiepublicatie) {
        this.setIndicatiepublicatie(indicatiepublicatie);
        return this;
    }

    public void setIndicatiepublicatie(String indicatiepublicatie) {
        this.indicatiepublicatie = indicatiepublicatie;
    }

    public String getPublicatietekst() {
        return this.publicatietekst;
    }

    public Zaaktype publicatietekst(String publicatietekst) {
        this.setPublicatietekst(publicatietekst);
        return this;
    }

    public void setPublicatietekst(String publicatietekst) {
        this.publicatietekst = publicatietekst;
    }

    public String getServicenormbehandeling() {
        return this.servicenormbehandeling;
    }

    public Zaaktype servicenormbehandeling(String servicenormbehandeling) {
        this.setServicenormbehandeling(servicenormbehandeling);
        return this;
    }

    public void setServicenormbehandeling(String servicenormbehandeling) {
        this.servicenormbehandeling = servicenormbehandeling;
    }

    public String getTrefwoord() {
        return this.trefwoord;
    }

    public Zaaktype trefwoord(String trefwoord) {
        this.setTrefwoord(trefwoord);
        return this;
    }

    public void setTrefwoord(String trefwoord) {
        this.trefwoord = trefwoord;
    }

    public String getVertrouwelijkaanduiding() {
        return this.vertrouwelijkaanduiding;
    }

    public Zaaktype vertrouwelijkaanduiding(String vertrouwelijkaanduiding) {
        this.setVertrouwelijkaanduiding(vertrouwelijkaanduiding);
        return this;
    }

    public void setVertrouwelijkaanduiding(String vertrouwelijkaanduiding) {
        this.vertrouwelijkaanduiding = vertrouwelijkaanduiding;
    }

    public String getZaakcategorie() {
        return this.zaakcategorie;
    }

    public Zaaktype zaakcategorie(String zaakcategorie) {
        this.setZaakcategorie(zaakcategorie);
        return this;
    }

    public void setZaakcategorie(String zaakcategorie) {
        this.zaakcategorie = zaakcategorie;
    }

    public String getZaaktypeomschrijving() {
        return this.zaaktypeomschrijving;
    }

    public Zaaktype zaaktypeomschrijving(String zaaktypeomschrijving) {
        this.setZaaktypeomschrijving(zaaktypeomschrijving);
        return this;
    }

    public void setZaaktypeomschrijving(String zaaktypeomschrijving) {
        this.zaaktypeomschrijving = zaaktypeomschrijving;
    }

    public String getZaaktypeomschrijvinggeneriek() {
        return this.zaaktypeomschrijvinggeneriek;
    }

    public Zaaktype zaaktypeomschrijvinggeneriek(String zaaktypeomschrijvinggeneriek) {
        this.setZaaktypeomschrijvinggeneriek(zaaktypeomschrijvinggeneriek);
        return this;
    }

    public void setZaaktypeomschrijvinggeneriek(String zaaktypeomschrijvinggeneriek) {
        this.zaaktypeomschrijvinggeneriek = zaaktypeomschrijvinggeneriek;
    }

    public Producttype getHeeftProducttype() {
        return this.heeftProducttype;
    }

    public void setHeeftProducttype(Producttype producttype) {
        this.heeftProducttype = producttype;
    }

    public Zaaktype heeftProducttype(Producttype producttype) {
        this.setHeeftProducttype(producttype);
        return this;
    }

    public Set<Heffinggrondslag> getHeeftHeffinggrondslags() {
        return this.heeftHeffinggrondslags;
    }

    public void setHeeftHeffinggrondslags(Set<Heffinggrondslag> heffinggrondslags) {
        if (this.heeftHeffinggrondslags != null) {
            this.heeftHeffinggrondslags.forEach(i -> i.setHeeftZaaktype(null));
        }
        if (heffinggrondslags != null) {
            heffinggrondslags.forEach(i -> i.setHeeftZaaktype(this));
        }
        this.heeftHeffinggrondslags = heffinggrondslags;
    }

    public Zaaktype heeftHeffinggrondslags(Set<Heffinggrondslag> heffinggrondslags) {
        this.setHeeftHeffinggrondslags(heffinggrondslags);
        return this;
    }

    public Zaaktype addHeeftHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        this.heeftHeffinggrondslags.add(heffinggrondslag);
        heffinggrondslag.setHeeftZaaktype(this);
        return this;
    }

    public Zaaktype removeHeeftHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        this.heeftHeffinggrondslags.remove(heffinggrondslag);
        heffinggrondslag.setHeeftZaaktype(null);
        return this;
    }

    public Set<Statustype> getHeeftStatustypes() {
        return this.heeftStatustypes;
    }

    public void setHeeftStatustypes(Set<Statustype> statustypes) {
        if (this.heeftStatustypes != null) {
            this.heeftStatustypes.forEach(i -> i.setHeeftZaaktype(null));
        }
        if (statustypes != null) {
            statustypes.forEach(i -> i.setHeeftZaaktype(this));
        }
        this.heeftStatustypes = statustypes;
    }

    public Zaaktype heeftStatustypes(Set<Statustype> statustypes) {
        this.setHeeftStatustypes(statustypes);
        return this;
    }

    public Zaaktype addHeeftStatustype(Statustype statustype) {
        this.heeftStatustypes.add(statustype);
        statustype.setHeeftZaaktype(this);
        return this;
    }

    public Zaaktype removeHeeftStatustype(Statustype statustype) {
        this.heeftStatustypes.remove(statustype);
        statustype.setHeeftZaaktype(null);
        return this;
    }

    public Product getBetreftProduct() {
        return this.betreftProduct;
    }

    public void setBetreftProduct(Product product) {
        this.betreftProduct = product;
    }

    public Zaaktype betreftProduct(Product product) {
        this.setBetreftProduct(product);
        return this;
    }

    public Bedrijfsprocestype getHeeftBedrijfsprocestype() {
        return this.heeftBedrijfsprocestype;
    }

    public void setHeeftBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        this.heeftBedrijfsprocestype = bedrijfsprocestype;
    }

    public Zaaktype heeftBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        this.setHeeftBedrijfsprocestype(bedrijfsprocestype);
        return this;
    }

    public Medewerker getIsverantwoordelijkevoorMedewerker() {
        return this.isverantwoordelijkevoorMedewerker;
    }

    public void setIsverantwoordelijkevoorMedewerker(Medewerker medewerker) {
        this.isverantwoordelijkevoorMedewerker = medewerker;
    }

    public Zaaktype isverantwoordelijkevoorMedewerker(Medewerker medewerker) {
        this.setIsverantwoordelijkevoorMedewerker(medewerker);
        return this;
    }

    public Set<Dienst> getStartDiensts() {
        return this.startDiensts;
    }

    public void setStartDiensts(Set<Dienst> diensts) {
        if (this.startDiensts != null) {
            this.startDiensts.forEach(i -> i.setStartZaaktype(null));
        }
        if (diensts != null) {
            diensts.forEach(i -> i.setStartZaaktype(this));
        }
        this.startDiensts = diensts;
    }

    public Zaaktype startDiensts(Set<Dienst> diensts) {
        this.setStartDiensts(diensts);
        return this;
    }

    public Zaaktype addStartDienst(Dienst dienst) {
        this.startDiensts.add(dienst);
        dienst.setStartZaaktype(this);
        return this;
    }

    public Zaaktype removeStartDienst(Dienst dienst) {
        this.startDiensts.remove(dienst);
        dienst.setStartZaaktype(null);
        return this;
    }

    public Set<Zaak> getIsvanZaaks() {
        return this.isvanZaaks;
    }

    public void setIsvanZaaks(Set<Zaak> zaaks) {
        if (this.isvanZaaks != null) {
            this.isvanZaaks.forEach(i -> i.setIsvanZaaktype(null));
        }
        if (zaaks != null) {
            zaaks.forEach(i -> i.setIsvanZaaktype(this));
        }
        this.isvanZaaks = zaaks;
    }

    public Zaaktype isvanZaaks(Set<Zaak> zaaks) {
        this.setIsvanZaaks(zaaks);
        return this;
    }

    public Zaaktype addIsvanZaak(Zaak zaak) {
        this.isvanZaaks.add(zaak);
        zaak.setIsvanZaaktype(this);
        return this;
    }

    public Zaaktype removeIsvanZaak(Zaak zaak) {
        this.isvanZaaks.remove(zaak);
        zaak.setIsvanZaaktype(null);
        return this;
    }

    public Set<Formuliersoort> getIsaanleidingvoorFormuliersoorts() {
        return this.isaanleidingvoorFormuliersoorts;
    }

    public void setIsaanleidingvoorFormuliersoorts(Set<Formuliersoort> formuliersoorts) {
        if (this.isaanleidingvoorFormuliersoorts != null) {
            this.isaanleidingvoorFormuliersoorts.forEach(i -> i.removeIsaanleidingvoorZaaktype(this));
        }
        if (formuliersoorts != null) {
            formuliersoorts.forEach(i -> i.addIsaanleidingvoorZaaktype(this));
        }
        this.isaanleidingvoorFormuliersoorts = formuliersoorts;
    }

    public Zaaktype isaanleidingvoorFormuliersoorts(Set<Formuliersoort> formuliersoorts) {
        this.setIsaanleidingvoorFormuliersoorts(formuliersoorts);
        return this;
    }

    public Zaaktype addIsaanleidingvoorFormuliersoort(Formuliersoort formuliersoort) {
        this.isaanleidingvoorFormuliersoorts.add(formuliersoort);
        formuliersoort.getIsaanleidingvoorZaaktypes().add(this);
        return this;
    }

    public Zaaktype removeIsaanleidingvoorFormuliersoort(Formuliersoort formuliersoort) {
        this.isaanleidingvoorFormuliersoorts.remove(formuliersoort);
        formuliersoort.getIsaanleidingvoorZaaktypes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zaaktype)) {
            return false;
        }
        return getId() != null && getId().equals(((Zaaktype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zaaktype{" +
            "id=" + getId() +
            ", archiefcode='" + getArchiefcode() + "'" +
            ", datumbegingeldigheidzaaktype='" + getDatumbegingeldigheidzaaktype() + "'" +
            ", datumeindegeldigheidzaaktype='" + getDatumeindegeldigheidzaaktype() + "'" +
            ", doorlooptijdbehandeling='" + getDoorlooptijdbehandeling() + "'" +
            ", indicatiepublicatie='" + getIndicatiepublicatie() + "'" +
            ", publicatietekst='" + getPublicatietekst() + "'" +
            ", servicenormbehandeling='" + getServicenormbehandeling() + "'" +
            ", trefwoord='" + getTrefwoord() + "'" +
            ", vertrouwelijkaanduiding='" + getVertrouwelijkaanduiding() + "'" +
            ", zaakcategorie='" + getZaakcategorie() + "'" +
            ", zaaktypeomschrijving='" + getZaaktypeomschrijving() + "'" +
            ", zaaktypeomschrijvinggeneriek='" + getZaaktypeomschrijvinggeneriek() + "'" +
            "}";
    }
}
