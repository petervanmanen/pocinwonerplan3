package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Kadastraleonroerendezaak.
 */
@Entity
@Table(name = "kadastraleonroerendezaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kadastraleonroerendezaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "empty")
    private String empty;

    @Column(name = "appartementsrechtvolgnummer")
    private String appartementsrechtvolgnummer;

    @Column(name = "begrenzing")
    private String begrenzing;

    @Column(name = "cultuurcodeonbebouwd")
    private String cultuurcodeonbebouwd;

    @Column(name = "datumbegingeldigheidkadastraleonroerendezaak")
    private LocalDate datumbegingeldigheidkadastraleonroerendezaak;

    @Column(name = "datumeindegeldigheidkadastraleonroerendezaak")
    private LocalDate datumeindegeldigheidkadastraleonroerendezaak;

    @Column(name = "identificatie")
    private String identificatie;

    @Size(max = 20)
    @Column(name = "kadastralegemeente", length = 20)
    private String kadastralegemeente;

    @Size(max = 20)
    @Column(name = "kadastralegemeentecode", length = 20)
    private String kadastralegemeentecode;

    @Column(name = "koopjaar")
    private String koopjaar;

    @Column(name = "koopsom", precision = 21, scale = 2)
    private BigDecimal koopsom;

    @Column(name = "landinrichtingrentebedrag", precision = 21, scale = 2)
    private BigDecimal landinrichtingrentebedrag;

    @Column(name = "landinrichtingrenteeindejaar")
    private String landinrichtingrenteeindejaar;

    @Column(name = "ligging")
    private String ligging;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "oud")
    private String oud;

    @Column(name = "perceelnummer")
    private String perceelnummer;

    @Column(name = "sectie")
    private String sectie;

    @Column(name = "valutacode")
    private String valutacode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kadastraleonroerendezaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpty() {
        return this.empty;
    }

    public Kadastraleonroerendezaak empty(String empty) {
        this.setEmpty(empty);
        return this;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public String getAppartementsrechtvolgnummer() {
        return this.appartementsrechtvolgnummer;
    }

    public Kadastraleonroerendezaak appartementsrechtvolgnummer(String appartementsrechtvolgnummer) {
        this.setAppartementsrechtvolgnummer(appartementsrechtvolgnummer);
        return this;
    }

    public void setAppartementsrechtvolgnummer(String appartementsrechtvolgnummer) {
        this.appartementsrechtvolgnummer = appartementsrechtvolgnummer;
    }

    public String getBegrenzing() {
        return this.begrenzing;
    }

    public Kadastraleonroerendezaak begrenzing(String begrenzing) {
        this.setBegrenzing(begrenzing);
        return this;
    }

    public void setBegrenzing(String begrenzing) {
        this.begrenzing = begrenzing;
    }

    public String getCultuurcodeonbebouwd() {
        return this.cultuurcodeonbebouwd;
    }

    public Kadastraleonroerendezaak cultuurcodeonbebouwd(String cultuurcodeonbebouwd) {
        this.setCultuurcodeonbebouwd(cultuurcodeonbebouwd);
        return this;
    }

    public void setCultuurcodeonbebouwd(String cultuurcodeonbebouwd) {
        this.cultuurcodeonbebouwd = cultuurcodeonbebouwd;
    }

    public LocalDate getDatumbegingeldigheidkadastraleonroerendezaak() {
        return this.datumbegingeldigheidkadastraleonroerendezaak;
    }

    public Kadastraleonroerendezaak datumbegingeldigheidkadastraleonroerendezaak(LocalDate datumbegingeldigheidkadastraleonroerendezaak) {
        this.setDatumbegingeldigheidkadastraleonroerendezaak(datumbegingeldigheidkadastraleonroerendezaak);
        return this;
    }

    public void setDatumbegingeldigheidkadastraleonroerendezaak(LocalDate datumbegingeldigheidkadastraleonroerendezaak) {
        this.datumbegingeldigheidkadastraleonroerendezaak = datumbegingeldigheidkadastraleonroerendezaak;
    }

    public LocalDate getDatumeindegeldigheidkadastraleonroerendezaak() {
        return this.datumeindegeldigheidkadastraleonroerendezaak;
    }

    public Kadastraleonroerendezaak datumeindegeldigheidkadastraleonroerendezaak(LocalDate datumeindegeldigheidkadastraleonroerendezaak) {
        this.setDatumeindegeldigheidkadastraleonroerendezaak(datumeindegeldigheidkadastraleonroerendezaak);
        return this;
    }

    public void setDatumeindegeldigheidkadastraleonroerendezaak(LocalDate datumeindegeldigheidkadastraleonroerendezaak) {
        this.datumeindegeldigheidkadastraleonroerendezaak = datumeindegeldigheidkadastraleonroerendezaak;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Kadastraleonroerendezaak identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getKadastralegemeente() {
        return this.kadastralegemeente;
    }

    public Kadastraleonroerendezaak kadastralegemeente(String kadastralegemeente) {
        this.setKadastralegemeente(kadastralegemeente);
        return this;
    }

    public void setKadastralegemeente(String kadastralegemeente) {
        this.kadastralegemeente = kadastralegemeente;
    }

    public String getKadastralegemeentecode() {
        return this.kadastralegemeentecode;
    }

    public Kadastraleonroerendezaak kadastralegemeentecode(String kadastralegemeentecode) {
        this.setKadastralegemeentecode(kadastralegemeentecode);
        return this;
    }

    public void setKadastralegemeentecode(String kadastralegemeentecode) {
        this.kadastralegemeentecode = kadastralegemeentecode;
    }

    public String getKoopjaar() {
        return this.koopjaar;
    }

    public Kadastraleonroerendezaak koopjaar(String koopjaar) {
        this.setKoopjaar(koopjaar);
        return this;
    }

    public void setKoopjaar(String koopjaar) {
        this.koopjaar = koopjaar;
    }

    public BigDecimal getKoopsom() {
        return this.koopsom;
    }

    public Kadastraleonroerendezaak koopsom(BigDecimal koopsom) {
        this.setKoopsom(koopsom);
        return this;
    }

    public void setKoopsom(BigDecimal koopsom) {
        this.koopsom = koopsom;
    }

    public BigDecimal getLandinrichtingrentebedrag() {
        return this.landinrichtingrentebedrag;
    }

    public Kadastraleonroerendezaak landinrichtingrentebedrag(BigDecimal landinrichtingrentebedrag) {
        this.setLandinrichtingrentebedrag(landinrichtingrentebedrag);
        return this;
    }

    public void setLandinrichtingrentebedrag(BigDecimal landinrichtingrentebedrag) {
        this.landinrichtingrentebedrag = landinrichtingrentebedrag;
    }

    public String getLandinrichtingrenteeindejaar() {
        return this.landinrichtingrenteeindejaar;
    }

    public Kadastraleonroerendezaak landinrichtingrenteeindejaar(String landinrichtingrenteeindejaar) {
        this.setLandinrichtingrenteeindejaar(landinrichtingrenteeindejaar);
        return this;
    }

    public void setLandinrichtingrenteeindejaar(String landinrichtingrenteeindejaar) {
        this.landinrichtingrenteeindejaar = landinrichtingrenteeindejaar;
    }

    public String getLigging() {
        return this.ligging;
    }

    public Kadastraleonroerendezaak ligging(String ligging) {
        this.setLigging(ligging);
        return this;
    }

    public void setLigging(String ligging) {
        this.ligging = ligging;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Kadastraleonroerendezaak locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Kadastraleonroerendezaak oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOud() {
        return this.oud;
    }

    public Kadastraleonroerendezaak oud(String oud) {
        this.setOud(oud);
        return this;
    }

    public void setOud(String oud) {
        this.oud = oud;
    }

    public String getPerceelnummer() {
        return this.perceelnummer;
    }

    public Kadastraleonroerendezaak perceelnummer(String perceelnummer) {
        this.setPerceelnummer(perceelnummer);
        return this;
    }

    public void setPerceelnummer(String perceelnummer) {
        this.perceelnummer = perceelnummer;
    }

    public String getSectie() {
        return this.sectie;
    }

    public Kadastraleonroerendezaak sectie(String sectie) {
        this.setSectie(sectie);
        return this;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public String getValutacode() {
        return this.valutacode;
    }

    public Kadastraleonroerendezaak valutacode(String valutacode) {
        this.setValutacode(valutacode);
        return this;
    }

    public void setValutacode(String valutacode) {
        this.valutacode = valutacode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kadastraleonroerendezaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Kadastraleonroerendezaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kadastraleonroerendezaak{" +
            "id=" + getId() +
            ", empty='" + getEmpty() + "'" +
            ", appartementsrechtvolgnummer='" + getAppartementsrechtvolgnummer() + "'" +
            ", begrenzing='" + getBegrenzing() + "'" +
            ", cultuurcodeonbebouwd='" + getCultuurcodeonbebouwd() + "'" +
            ", datumbegingeldigheidkadastraleonroerendezaak='" + getDatumbegingeldigheidkadastraleonroerendezaak() + "'" +
            ", datumeindegeldigheidkadastraleonroerendezaak='" + getDatumeindegeldigheidkadastraleonroerendezaak() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", kadastralegemeente='" + getKadastralegemeente() + "'" +
            ", kadastralegemeentecode='" + getKadastralegemeentecode() + "'" +
            ", koopjaar='" + getKoopjaar() + "'" +
            ", koopsom=" + getKoopsom() +
            ", landinrichtingrentebedrag=" + getLandinrichtingrentebedrag() +
            ", landinrichtingrenteeindejaar='" + getLandinrichtingrenteeindejaar() + "'" +
            ", ligging='" + getLigging() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", oud='" + getOud() + "'" +
            ", perceelnummer='" + getPerceelnummer() + "'" +
            ", sectie='" + getSectie() + "'" +
            ", valutacode='" + getValutacode() + "'" +
            "}";
    }
}
