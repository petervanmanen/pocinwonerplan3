package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Attribuutsoort.
 */
@Entity
@Table(name = "attribuutsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attribuutsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "authentiek")
    private Boolean authentiek;

    @Column(name = "datumopname")
    private LocalDate datumopname;

    @Column(name = "definitie")
    private String definitie;

    @Column(name = "domein")
    private String domein;

    @Column(name = "eaguid")
    private String eaguid;

    @Column(name = "herkomst")
    private String herkomst;

    @Column(name = "herkomstdefinitie")
    private String herkomstdefinitie;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "identificerend")
    private Boolean identificerend;

    @Column(name = "indicatieafleidbaar")
    private Boolean indicatieafleidbaar;

    @Column(name = "indicatiematerielehistorie")
    private Boolean indicatiematerielehistorie;

    @Column(name = "kardinaliteit")
    private String kardinaliteit;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "mogelijkgeenwaarde")
    private Boolean mogelijkgeenwaarde;

    @Column(name = "naam")
    private String naam;

    @Column(name = "patroon")
    private String patroon;

    @Column(name = "precisie")
    private String precisie;

    @Column(name = "stereotype")
    private String stereotype;

    @Column(name = "toelichting")
    private String toelichting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftAttribuutsoorts" }, allowSetters = true)
    private Datatype heeftDatatype;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Boolean getAuthentiek() {
        return this.authentiek;
    }

    public Attribuutsoort authentiek(Boolean authentiek) {
        this.setAuthentiek(authentiek);
        return this;
    }

    public void setAuthentiek(Boolean authentiek) {
        this.authentiek = authentiek;
    }

    public LocalDate getDatumopname() {
        return this.datumopname;
    }

    public Attribuutsoort datumopname(LocalDate datumopname) {
        this.setDatumopname(datumopname);
        return this;
    }

    public void setDatumopname(LocalDate datumopname) {
        this.datumopname = datumopname;
    }

    public String getDefinitie() {
        return this.definitie;
    }

    public Attribuutsoort definitie(String definitie) {
        this.setDefinitie(definitie);
        return this;
    }

    public void setDefinitie(String definitie) {
        this.definitie = definitie;
    }

    public String getDomein() {
        return this.domein;
    }

    public Attribuutsoort domein(String domein) {
        this.setDomein(domein);
        return this;
    }

    public void setDomein(String domein) {
        this.domein = domein;
    }

    public String getEaguid() {
        return this.eaguid;
    }

    public Attribuutsoort eaguid(String eaguid) {
        this.setEaguid(eaguid);
        return this;
    }

    public void setEaguid(String eaguid) {
        this.eaguid = eaguid;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Attribuutsoort herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getHerkomstdefinitie() {
        return this.herkomstdefinitie;
    }

    public Attribuutsoort herkomstdefinitie(String herkomstdefinitie) {
        this.setHerkomstdefinitie(herkomstdefinitie);
        return this;
    }

    public void setHerkomstdefinitie(String herkomstdefinitie) {
        this.herkomstdefinitie = herkomstdefinitie;
    }

    public String getId() {
        return this.id;
    }

    public Attribuutsoort id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIdentificerend() {
        return this.identificerend;
    }

    public Attribuutsoort identificerend(Boolean identificerend) {
        this.setIdentificerend(identificerend);
        return this;
    }

    public void setIdentificerend(Boolean identificerend) {
        this.identificerend = identificerend;
    }

    public Boolean getIndicatieafleidbaar() {
        return this.indicatieafleidbaar;
    }

    public Attribuutsoort indicatieafleidbaar(Boolean indicatieafleidbaar) {
        this.setIndicatieafleidbaar(indicatieafleidbaar);
        return this;
    }

    public void setIndicatieafleidbaar(Boolean indicatieafleidbaar) {
        this.indicatieafleidbaar = indicatieafleidbaar;
    }

    public Boolean getIndicatiematerielehistorie() {
        return this.indicatiematerielehistorie;
    }

    public Attribuutsoort indicatiematerielehistorie(Boolean indicatiematerielehistorie) {
        this.setIndicatiematerielehistorie(indicatiematerielehistorie);
        return this;
    }

    public void setIndicatiematerielehistorie(Boolean indicatiematerielehistorie) {
        this.indicatiematerielehistorie = indicatiematerielehistorie;
    }

    public String getKardinaliteit() {
        return this.kardinaliteit;
    }

    public Attribuutsoort kardinaliteit(String kardinaliteit) {
        this.setKardinaliteit(kardinaliteit);
        return this;
    }

    public void setKardinaliteit(String kardinaliteit) {
        this.kardinaliteit = kardinaliteit;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Attribuutsoort lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public Boolean getMogelijkgeenwaarde() {
        return this.mogelijkgeenwaarde;
    }

    public Attribuutsoort mogelijkgeenwaarde(Boolean mogelijkgeenwaarde) {
        this.setMogelijkgeenwaarde(mogelijkgeenwaarde);
        return this;
    }

    public void setMogelijkgeenwaarde(Boolean mogelijkgeenwaarde) {
        this.mogelijkgeenwaarde = mogelijkgeenwaarde;
    }

    public String getNaam() {
        return this.naam;
    }

    public Attribuutsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPatroon() {
        return this.patroon;
    }

    public Attribuutsoort patroon(String patroon) {
        this.setPatroon(patroon);
        return this;
    }

    public void setPatroon(String patroon) {
        this.patroon = patroon;
    }

    public String getPrecisie() {
        return this.precisie;
    }

    public Attribuutsoort precisie(String precisie) {
        this.setPrecisie(precisie);
        return this;
    }

    public void setPrecisie(String precisie) {
        this.precisie = precisie;
    }

    public String getStereotype() {
        return this.stereotype;
    }

    public Attribuutsoort stereotype(String stereotype) {
        this.setStereotype(stereotype);
        return this;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Attribuutsoort toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public Datatype getHeeftDatatype() {
        return this.heeftDatatype;
    }

    public void setHeeftDatatype(Datatype datatype) {
        this.heeftDatatype = datatype;
    }

    public Attribuutsoort heeftDatatype(Datatype datatype) {
        this.setHeeftDatatype(datatype);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attribuutsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Attribuutsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attribuutsoort{" +
            "id=" + getId() +
            ", authentiek='" + getAuthentiek() + "'" +
            ", datumopname='" + getDatumopname() + "'" +
            ", definitie='" + getDefinitie() + "'" +
            ", domein='" + getDomein() + "'" +
            ", eaguid='" + getEaguid() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", herkomstdefinitie='" + getHerkomstdefinitie() + "'" +
            ", identificerend='" + getIdentificerend() + "'" +
            ", indicatieafleidbaar='" + getIndicatieafleidbaar() + "'" +
            ", indicatiematerielehistorie='" + getIndicatiematerielehistorie() + "'" +
            ", kardinaliteit='" + getKardinaliteit() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", mogelijkgeenwaarde='" + getMogelijkgeenwaarde() + "'" +
            ", naam='" + getNaam() + "'" +
            ", patroon='" + getPatroon() + "'" +
            ", precisie='" + getPrecisie() + "'" +
            ", stereotype='" + getStereotype() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
