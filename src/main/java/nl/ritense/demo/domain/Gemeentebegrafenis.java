package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Gemeentebegrafenis.
 */
@Entity
@Table(name = "gemeentebegrafenis")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gemeentebegrafenis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "achtergrondmelding")
    private String achtergrondmelding;

    @Column(name = "begrafeniskosten", precision = 21, scale = 2)
    private BigDecimal begrafeniskosten;

    @Column(name = "datumafgedaan")
    private LocalDate datumafgedaan;

    @Column(name = "datumbegrafenis")
    private LocalDate datumbegrafenis;

    @Column(name = "datumgemeld")
    private LocalDate datumgemeld;

    @Column(name = "datumruiminggraf")
    private LocalDate datumruiminggraf;

    @Column(name = "doodsoorzaak")
    private String doodsoorzaak;

    @Column(name = "gemeentelijkekosten", precision = 21, scale = 2)
    private BigDecimal gemeentelijkekosten;

    @Column(name = "inkoopordernummer")
    private String inkoopordernummer;

    @Column(name = "melder")
    private String melder;

    @Column(name = "urengemeente")
    private String urengemeente;

    @Column(name = "verhaaldbedrag", precision = 21, scale = 2)
    private BigDecimal verhaaldbedrag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gemeentebegrafenis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchtergrondmelding() {
        return this.achtergrondmelding;
    }

    public Gemeentebegrafenis achtergrondmelding(String achtergrondmelding) {
        this.setAchtergrondmelding(achtergrondmelding);
        return this;
    }

    public void setAchtergrondmelding(String achtergrondmelding) {
        this.achtergrondmelding = achtergrondmelding;
    }

    public BigDecimal getBegrafeniskosten() {
        return this.begrafeniskosten;
    }

    public Gemeentebegrafenis begrafeniskosten(BigDecimal begrafeniskosten) {
        this.setBegrafeniskosten(begrafeniskosten);
        return this;
    }

    public void setBegrafeniskosten(BigDecimal begrafeniskosten) {
        this.begrafeniskosten = begrafeniskosten;
    }

    public LocalDate getDatumafgedaan() {
        return this.datumafgedaan;
    }

    public Gemeentebegrafenis datumafgedaan(LocalDate datumafgedaan) {
        this.setDatumafgedaan(datumafgedaan);
        return this;
    }

    public void setDatumafgedaan(LocalDate datumafgedaan) {
        this.datumafgedaan = datumafgedaan;
    }

    public LocalDate getDatumbegrafenis() {
        return this.datumbegrafenis;
    }

    public Gemeentebegrafenis datumbegrafenis(LocalDate datumbegrafenis) {
        this.setDatumbegrafenis(datumbegrafenis);
        return this;
    }

    public void setDatumbegrafenis(LocalDate datumbegrafenis) {
        this.datumbegrafenis = datumbegrafenis;
    }

    public LocalDate getDatumgemeld() {
        return this.datumgemeld;
    }

    public Gemeentebegrafenis datumgemeld(LocalDate datumgemeld) {
        this.setDatumgemeld(datumgemeld);
        return this;
    }

    public void setDatumgemeld(LocalDate datumgemeld) {
        this.datumgemeld = datumgemeld;
    }

    public LocalDate getDatumruiminggraf() {
        return this.datumruiminggraf;
    }

    public Gemeentebegrafenis datumruiminggraf(LocalDate datumruiminggraf) {
        this.setDatumruiminggraf(datumruiminggraf);
        return this;
    }

    public void setDatumruiminggraf(LocalDate datumruiminggraf) {
        this.datumruiminggraf = datumruiminggraf;
    }

    public String getDoodsoorzaak() {
        return this.doodsoorzaak;
    }

    public Gemeentebegrafenis doodsoorzaak(String doodsoorzaak) {
        this.setDoodsoorzaak(doodsoorzaak);
        return this;
    }

    public void setDoodsoorzaak(String doodsoorzaak) {
        this.doodsoorzaak = doodsoorzaak;
    }

    public BigDecimal getGemeentelijkekosten() {
        return this.gemeentelijkekosten;
    }

    public Gemeentebegrafenis gemeentelijkekosten(BigDecimal gemeentelijkekosten) {
        this.setGemeentelijkekosten(gemeentelijkekosten);
        return this;
    }

    public void setGemeentelijkekosten(BigDecimal gemeentelijkekosten) {
        this.gemeentelijkekosten = gemeentelijkekosten;
    }

    public String getInkoopordernummer() {
        return this.inkoopordernummer;
    }

    public Gemeentebegrafenis inkoopordernummer(String inkoopordernummer) {
        this.setInkoopordernummer(inkoopordernummer);
        return this;
    }

    public void setInkoopordernummer(String inkoopordernummer) {
        this.inkoopordernummer = inkoopordernummer;
    }

    public String getMelder() {
        return this.melder;
    }

    public Gemeentebegrafenis melder(String melder) {
        this.setMelder(melder);
        return this;
    }

    public void setMelder(String melder) {
        this.melder = melder;
    }

    public String getUrengemeente() {
        return this.urengemeente;
    }

    public Gemeentebegrafenis urengemeente(String urengemeente) {
        this.setUrengemeente(urengemeente);
        return this;
    }

    public void setUrengemeente(String urengemeente) {
        this.urengemeente = urengemeente;
    }

    public BigDecimal getVerhaaldbedrag() {
        return this.verhaaldbedrag;
    }

    public Gemeentebegrafenis verhaaldbedrag(BigDecimal verhaaldbedrag) {
        this.setVerhaaldbedrag(verhaaldbedrag);
        return this;
    }

    public void setVerhaaldbedrag(BigDecimal verhaaldbedrag) {
        this.verhaaldbedrag = verhaaldbedrag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gemeentebegrafenis)) {
            return false;
        }
        return getId() != null && getId().equals(((Gemeentebegrafenis) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gemeentebegrafenis{" +
            "id=" + getId() +
            ", achtergrondmelding='" + getAchtergrondmelding() + "'" +
            ", begrafeniskosten=" + getBegrafeniskosten() +
            ", datumafgedaan='" + getDatumafgedaan() + "'" +
            ", datumbegrafenis='" + getDatumbegrafenis() + "'" +
            ", datumgemeld='" + getDatumgemeld() + "'" +
            ", datumruiminggraf='" + getDatumruiminggraf() + "'" +
            ", doodsoorzaak='" + getDoodsoorzaak() + "'" +
            ", gemeentelijkekosten=" + getGemeentelijkekosten() +
            ", inkoopordernummer='" + getInkoopordernummer() + "'" +
            ", melder='" + getMelder() + "'" +
            ", urengemeente='" + getUrengemeente() + "'" +
            ", verhaaldbedrag=" + getVerhaaldbedrag() +
            "}";
    }
}
