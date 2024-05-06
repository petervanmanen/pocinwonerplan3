package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Maatschappelijkeactiviteit.
 */
@Entity
@Table(name = "maatschappelijkeactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Maatschappelijkeactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresbinnenland")
    private String adresbinnenland;

    @Size(max = 100)
    @Column(name = "adrescorrespondentie", length = 100)
    private String adrescorrespondentie;

    @Column(name = "datumaanvang")
    private LocalDate datumaanvang;

    @Column(name = "datumeindegeldig")
    private LocalDate datumeindegeldig;

    @Column(name = "datumfaillisement")
    private LocalDate datumfaillisement;

    @Column(name = "indicatieeconomischactief")
    private String indicatieeconomischactief;

    @Size(max = 8)
    @Column(name = "kvknummer", length = 8)
    private String kvknummer;

    @Size(max = 100)
    @Column(name = "rechtsvorm", length = 100)
    private String rechtsvorm;

    @Size(max = 10)
    @Column(name = "rsin", length = 10)
    private String rsin;

    @Size(max = 100)
    @Column(name = "statutairenaam", length = 100)
    private String statutairenaam;

    @Size(max = 20)
    @Column(name = "telefoonnummer", length = 20)
    private String telefoonnummer;

    @Size(max = 100)
    @Column(name = "url", length = 100)
    private String url;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Maatschappelijkeactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresbinnenland() {
        return this.adresbinnenland;
    }

    public Maatschappelijkeactiviteit adresbinnenland(String adresbinnenland) {
        this.setAdresbinnenland(adresbinnenland);
        return this;
    }

    public void setAdresbinnenland(String adresbinnenland) {
        this.adresbinnenland = adresbinnenland;
    }

    public String getAdrescorrespondentie() {
        return this.adrescorrespondentie;
    }

    public Maatschappelijkeactiviteit adrescorrespondentie(String adrescorrespondentie) {
        this.setAdrescorrespondentie(adrescorrespondentie);
        return this;
    }

    public void setAdrescorrespondentie(String adrescorrespondentie) {
        this.adrescorrespondentie = adrescorrespondentie;
    }

    public LocalDate getDatumaanvang() {
        return this.datumaanvang;
    }

    public Maatschappelijkeactiviteit datumaanvang(LocalDate datumaanvang) {
        this.setDatumaanvang(datumaanvang);
        return this;
    }

    public void setDatumaanvang(LocalDate datumaanvang) {
        this.datumaanvang = datumaanvang;
    }

    public LocalDate getDatumeindegeldig() {
        return this.datumeindegeldig;
    }

    public Maatschappelijkeactiviteit datumeindegeldig(LocalDate datumeindegeldig) {
        this.setDatumeindegeldig(datumeindegeldig);
        return this;
    }

    public void setDatumeindegeldig(LocalDate datumeindegeldig) {
        this.datumeindegeldig = datumeindegeldig;
    }

    public LocalDate getDatumfaillisement() {
        return this.datumfaillisement;
    }

    public Maatschappelijkeactiviteit datumfaillisement(LocalDate datumfaillisement) {
        this.setDatumfaillisement(datumfaillisement);
        return this;
    }

    public void setDatumfaillisement(LocalDate datumfaillisement) {
        this.datumfaillisement = datumfaillisement;
    }

    public String getIndicatieeconomischactief() {
        return this.indicatieeconomischactief;
    }

    public Maatschappelijkeactiviteit indicatieeconomischactief(String indicatieeconomischactief) {
        this.setIndicatieeconomischactief(indicatieeconomischactief);
        return this;
    }

    public void setIndicatieeconomischactief(String indicatieeconomischactief) {
        this.indicatieeconomischactief = indicatieeconomischactief;
    }

    public String getKvknummer() {
        return this.kvknummer;
    }

    public Maatschappelijkeactiviteit kvknummer(String kvknummer) {
        this.setKvknummer(kvknummer);
        return this;
    }

    public void setKvknummer(String kvknummer) {
        this.kvknummer = kvknummer;
    }

    public String getRechtsvorm() {
        return this.rechtsvorm;
    }

    public Maatschappelijkeactiviteit rechtsvorm(String rechtsvorm) {
        this.setRechtsvorm(rechtsvorm);
        return this;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getRsin() {
        return this.rsin;
    }

    public Maatschappelijkeactiviteit rsin(String rsin) {
        this.setRsin(rsin);
        return this;
    }

    public void setRsin(String rsin) {
        this.rsin = rsin;
    }

    public String getStatutairenaam() {
        return this.statutairenaam;
    }

    public Maatschappelijkeactiviteit statutairenaam(String statutairenaam) {
        this.setStatutairenaam(statutairenaam);
        return this;
    }

    public void setStatutairenaam(String statutairenaam) {
        this.statutairenaam = statutairenaam;
    }

    public String getTelefoonnummer() {
        return this.telefoonnummer;
    }

    public Maatschappelijkeactiviteit telefoonnummer(String telefoonnummer) {
        this.setTelefoonnummer(telefoonnummer);
        return this;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getUrl() {
        return this.url;
    }

    public Maatschappelijkeactiviteit url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Maatschappelijkeactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Maatschappelijkeactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Maatschappelijkeactiviteit{" +
            "id=" + getId() +
            ", adresbinnenland='" + getAdresbinnenland() + "'" +
            ", adrescorrespondentie='" + getAdrescorrespondentie() + "'" +
            ", datumaanvang='" + getDatumaanvang() + "'" +
            ", datumeindegeldig='" + getDatumeindegeldig() + "'" +
            ", datumfaillisement='" + getDatumfaillisement() + "'" +
            ", indicatieeconomischactief='" + getIndicatieeconomischactief() + "'" +
            ", kvknummer='" + getKvknummer() + "'" +
            ", rechtsvorm='" + getRechtsvorm() + "'" +
            ", rsin='" + getRsin() + "'" +
            ", statutairenaam='" + getStatutairenaam() + "'" +
            ", telefoonnummer='" + getTelefoonnummer() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
