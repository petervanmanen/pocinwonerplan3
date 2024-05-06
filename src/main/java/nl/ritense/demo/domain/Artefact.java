package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Artefact.
 */
@Entity
@Table(name = "artefact")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artefact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "artefectnummer", length = 20)
    private String artefectnummer;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "conserveren")
    private Boolean conserveren;

    @Column(name = "datering")
    private String datering;

    @Size(max = 20)
    @Column(name = "dateringcomplex", length = 20)
    private String dateringcomplex;

    @Column(name = "determinatieniveau")
    private String determinatieniveau;

    @Column(name = "dianummer")
    private String dianummer;

    @Column(name = "doosnummer")
    private String doosnummer;

    @Column(name = "exposabel")
    private Boolean exposabel;

    @Column(name = "fotonummer")
    private String fotonummer;

    @Column(name = "functie")
    private String functie;

    @Column(name = "herkomst")
    private String herkomst;

    @Column(name = "key")
    private String key;

    @Column(name = "keydoos")
    private String keydoos;

    @Column(name = "keymagazijnplaatsing")
    private String keymagazijnplaatsing;

    @Column(name = "keyput")
    private String keyput;

    @Column(name = "keyvondst")
    private String keyvondst;

    @Column(name = "literatuur")
    private String literatuur;

    @Column(name = "maten")
    private String maten;

    @Column(name = "naam")
    private String naam;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "origine")
    private String origine;

    @Size(max = 20)
    @Column(name = "projectcd", length = 20)
    private String projectcd;

    @Size(max = 20)
    @Column(name = "putnummer", length = 20)
    private String putnummer;

    @Column(name = "restauratiewenselijk")
    private Boolean restauratiewenselijk;

    @Column(name = "tekeningnummer")
    private String tekeningnummer;

    @Column(name = "type")
    private String type;

    @Column(name = "vondstnummer")
    private String vondstnummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "staatopMagazijnlocatie", "zitinArtefacts" }, allowSetters = true)
    private Doos zitinDoos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvansoortArtefacts" }, allowSetters = true)
    private Artefactsoort isvansoortArtefactsoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bevatArtefacts", "heeftVulling" }, allowSetters = true)
    private Vondst bevatVondst;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artefact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtefectnummer() {
        return this.artefectnummer;
    }

    public Artefact artefectnummer(String artefectnummer) {
        this.setArtefectnummer(artefectnummer);
        return this;
    }

    public void setArtefectnummer(String artefectnummer) {
        this.artefectnummer = artefectnummer;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Artefact beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Boolean getConserveren() {
        return this.conserveren;
    }

    public Artefact conserveren(Boolean conserveren) {
        this.setConserveren(conserveren);
        return this;
    }

    public void setConserveren(Boolean conserveren) {
        this.conserveren = conserveren;
    }

    public String getDatering() {
        return this.datering;
    }

    public Artefact datering(String datering) {
        this.setDatering(datering);
        return this;
    }

    public void setDatering(String datering) {
        this.datering = datering;
    }

    public String getDateringcomplex() {
        return this.dateringcomplex;
    }

    public Artefact dateringcomplex(String dateringcomplex) {
        this.setDateringcomplex(dateringcomplex);
        return this;
    }

    public void setDateringcomplex(String dateringcomplex) {
        this.dateringcomplex = dateringcomplex;
    }

    public String getDeterminatieniveau() {
        return this.determinatieniveau;
    }

    public Artefact determinatieniveau(String determinatieniveau) {
        this.setDeterminatieniveau(determinatieniveau);
        return this;
    }

    public void setDeterminatieniveau(String determinatieniveau) {
        this.determinatieniveau = determinatieniveau;
    }

    public String getDianummer() {
        return this.dianummer;
    }

    public Artefact dianummer(String dianummer) {
        this.setDianummer(dianummer);
        return this;
    }

    public void setDianummer(String dianummer) {
        this.dianummer = dianummer;
    }

    public String getDoosnummer() {
        return this.doosnummer;
    }

    public Artefact doosnummer(String doosnummer) {
        this.setDoosnummer(doosnummer);
        return this;
    }

    public void setDoosnummer(String doosnummer) {
        this.doosnummer = doosnummer;
    }

    public Boolean getExposabel() {
        return this.exposabel;
    }

    public Artefact exposabel(Boolean exposabel) {
        this.setExposabel(exposabel);
        return this;
    }

    public void setExposabel(Boolean exposabel) {
        this.exposabel = exposabel;
    }

    public String getFotonummer() {
        return this.fotonummer;
    }

    public Artefact fotonummer(String fotonummer) {
        this.setFotonummer(fotonummer);
        return this;
    }

    public void setFotonummer(String fotonummer) {
        this.fotonummer = fotonummer;
    }

    public String getFunctie() {
        return this.functie;
    }

    public Artefact functie(String functie) {
        this.setFunctie(functie);
        return this;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Artefact herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getKey() {
        return this.key;
    }

    public Artefact key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeydoos() {
        return this.keydoos;
    }

    public Artefact keydoos(String keydoos) {
        this.setKeydoos(keydoos);
        return this;
    }

    public void setKeydoos(String keydoos) {
        this.keydoos = keydoos;
    }

    public String getKeymagazijnplaatsing() {
        return this.keymagazijnplaatsing;
    }

    public Artefact keymagazijnplaatsing(String keymagazijnplaatsing) {
        this.setKeymagazijnplaatsing(keymagazijnplaatsing);
        return this;
    }

    public void setKeymagazijnplaatsing(String keymagazijnplaatsing) {
        this.keymagazijnplaatsing = keymagazijnplaatsing;
    }

    public String getKeyput() {
        return this.keyput;
    }

    public Artefact keyput(String keyput) {
        this.setKeyput(keyput);
        return this;
    }

    public void setKeyput(String keyput) {
        this.keyput = keyput;
    }

    public String getKeyvondst() {
        return this.keyvondst;
    }

    public Artefact keyvondst(String keyvondst) {
        this.setKeyvondst(keyvondst);
        return this;
    }

    public void setKeyvondst(String keyvondst) {
        this.keyvondst = keyvondst;
    }

    public String getLiteratuur() {
        return this.literatuur;
    }

    public Artefact literatuur(String literatuur) {
        this.setLiteratuur(literatuur);
        return this;
    }

    public void setLiteratuur(String literatuur) {
        this.literatuur = literatuur;
    }

    public String getMaten() {
        return this.maten;
    }

    public Artefact maten(String maten) {
        this.setMaten(maten);
        return this;
    }

    public void setMaten(String maten) {
        this.maten = maten;
    }

    public String getNaam() {
        return this.naam;
    }

    public Artefact naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Artefact opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getOrigine() {
        return this.origine;
    }

    public Artefact origine(String origine) {
        this.setOrigine(origine);
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Artefact projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getPutnummer() {
        return this.putnummer;
    }

    public Artefact putnummer(String putnummer) {
        this.setPutnummer(putnummer);
        return this;
    }

    public void setPutnummer(String putnummer) {
        this.putnummer = putnummer;
    }

    public Boolean getRestauratiewenselijk() {
        return this.restauratiewenselijk;
    }

    public Artefact restauratiewenselijk(Boolean restauratiewenselijk) {
        this.setRestauratiewenselijk(restauratiewenselijk);
        return this;
    }

    public void setRestauratiewenselijk(Boolean restauratiewenselijk) {
        this.restauratiewenselijk = restauratiewenselijk;
    }

    public String getTekeningnummer() {
        return this.tekeningnummer;
    }

    public Artefact tekeningnummer(String tekeningnummer) {
        this.setTekeningnummer(tekeningnummer);
        return this;
    }

    public void setTekeningnummer(String tekeningnummer) {
        this.tekeningnummer = tekeningnummer;
    }

    public String getType() {
        return this.type;
    }

    public Artefact type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVondstnummer() {
        return this.vondstnummer;
    }

    public Artefact vondstnummer(String vondstnummer) {
        this.setVondstnummer(vondstnummer);
        return this;
    }

    public void setVondstnummer(String vondstnummer) {
        this.vondstnummer = vondstnummer;
    }

    public Doos getZitinDoos() {
        return this.zitinDoos;
    }

    public void setZitinDoos(Doos doos) {
        this.zitinDoos = doos;
    }

    public Artefact zitinDoos(Doos doos) {
        this.setZitinDoos(doos);
        return this;
    }

    public Artefactsoort getIsvansoortArtefactsoort() {
        return this.isvansoortArtefactsoort;
    }

    public void setIsvansoortArtefactsoort(Artefactsoort artefactsoort) {
        this.isvansoortArtefactsoort = artefactsoort;
    }

    public Artefact isvansoortArtefactsoort(Artefactsoort artefactsoort) {
        this.setIsvansoortArtefactsoort(artefactsoort);
        return this;
    }

    public Vondst getBevatVondst() {
        return this.bevatVondst;
    }

    public void setBevatVondst(Vondst vondst) {
        this.bevatVondst = vondst;
    }

    public Artefact bevatVondst(Vondst vondst) {
        this.setBevatVondst(vondst);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artefact)) {
            return false;
        }
        return getId() != null && getId().equals(((Artefact) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artefact{" +
            "id=" + getId() +
            ", artefectnummer='" + getArtefectnummer() + "'" +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", conserveren='" + getConserveren() + "'" +
            ", datering='" + getDatering() + "'" +
            ", dateringcomplex='" + getDateringcomplex() + "'" +
            ", determinatieniveau='" + getDeterminatieniveau() + "'" +
            ", dianummer='" + getDianummer() + "'" +
            ", doosnummer='" + getDoosnummer() + "'" +
            ", exposabel='" + getExposabel() + "'" +
            ", fotonummer='" + getFotonummer() + "'" +
            ", functie='" + getFunctie() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", key='" + getKey() + "'" +
            ", keydoos='" + getKeydoos() + "'" +
            ", keymagazijnplaatsing='" + getKeymagazijnplaatsing() + "'" +
            ", keyput='" + getKeyput() + "'" +
            ", keyvondst='" + getKeyvondst() + "'" +
            ", literatuur='" + getLiteratuur() + "'" +
            ", maten='" + getMaten() + "'" +
            ", naam='" + getNaam() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", origine='" + getOrigine() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", putnummer='" + getPutnummer() + "'" +
            ", restauratiewenselijk='" + getRestauratiewenselijk() + "'" +
            ", tekeningnummer='" + getTekeningnummer() + "'" +
            ", type='" + getType() + "'" +
            ", vondstnummer='" + getVondstnummer() + "'" +
            "}";
    }
}
