package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vordering.
 */
@Entity
@Table(name = "vordering")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vordering implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Column(name = "bedragbtw")
    private String bedragbtw;

    @Column(name = "datumaanmaak")
    private LocalDate datumaanmaak;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Column(name = "geaccordeerd")
    private String geaccordeerd;

    @Column(name = "geaccordeerddoor")
    private String geaccordeerddoor;

    @Column(name = "geaccordeerdop")
    private LocalDate geaccordeerdop;

    @Column(name = "geexporteerd")
    private String geexporteerd;

    @Column(name = "gemuteerddoor")
    private String gemuteerddoor;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "totaalbedrag")
    private String totaalbedrag;

    @Column(name = "totaalbedraginclusief")
    private String totaalbedraginclusief;

    @Column(name = "vorderingnummer")
    private String vorderingnummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vordering id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Vordering aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public String getBedragbtw() {
        return this.bedragbtw;
    }

    public Vordering bedragbtw(String bedragbtw) {
        this.setBedragbtw(bedragbtw);
        return this;
    }

    public void setBedragbtw(String bedragbtw) {
        this.bedragbtw = bedragbtw;
    }

    public LocalDate getDatumaanmaak() {
        return this.datumaanmaak;
    }

    public Vordering datumaanmaak(LocalDate datumaanmaak) {
        this.setDatumaanmaak(datumaanmaak);
        return this;
    }

    public void setDatumaanmaak(LocalDate datumaanmaak) {
        this.datumaanmaak = datumaanmaak;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Vordering datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public String getGeaccordeerd() {
        return this.geaccordeerd;
    }

    public Vordering geaccordeerd(String geaccordeerd) {
        this.setGeaccordeerd(geaccordeerd);
        return this;
    }

    public void setGeaccordeerd(String geaccordeerd) {
        this.geaccordeerd = geaccordeerd;
    }

    public String getGeaccordeerddoor() {
        return this.geaccordeerddoor;
    }

    public Vordering geaccordeerddoor(String geaccordeerddoor) {
        this.setGeaccordeerddoor(geaccordeerddoor);
        return this;
    }

    public void setGeaccordeerddoor(String geaccordeerddoor) {
        this.geaccordeerddoor = geaccordeerddoor;
    }

    public LocalDate getGeaccordeerdop() {
        return this.geaccordeerdop;
    }

    public Vordering geaccordeerdop(LocalDate geaccordeerdop) {
        this.setGeaccordeerdop(geaccordeerdop);
        return this;
    }

    public void setGeaccordeerdop(LocalDate geaccordeerdop) {
        this.geaccordeerdop = geaccordeerdop;
    }

    public String getGeexporteerd() {
        return this.geexporteerd;
    }

    public Vordering geexporteerd(String geexporteerd) {
        this.setGeexporteerd(geexporteerd);
        return this;
    }

    public void setGeexporteerd(String geexporteerd) {
        this.geexporteerd = geexporteerd;
    }

    public String getGemuteerddoor() {
        return this.gemuteerddoor;
    }

    public Vordering gemuteerddoor(String gemuteerddoor) {
        this.setGemuteerddoor(gemuteerddoor);
        return this;
    }

    public void setGemuteerddoor(String gemuteerddoor) {
        this.gemuteerddoor = gemuteerddoor;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Vordering omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTotaalbedrag() {
        return this.totaalbedrag;
    }

    public Vordering totaalbedrag(String totaalbedrag) {
        this.setTotaalbedrag(totaalbedrag);
        return this;
    }

    public void setTotaalbedrag(String totaalbedrag) {
        this.totaalbedrag = totaalbedrag;
    }

    public String getTotaalbedraginclusief() {
        return this.totaalbedraginclusief;
    }

    public Vordering totaalbedraginclusief(String totaalbedraginclusief) {
        this.setTotaalbedraginclusief(totaalbedraginclusief);
        return this;
    }

    public void setTotaalbedraginclusief(String totaalbedraginclusief) {
        this.totaalbedraginclusief = totaalbedraginclusief;
    }

    public String getVorderingnummer() {
        return this.vorderingnummer;
    }

    public Vordering vorderingnummer(String vorderingnummer) {
        this.setVorderingnummer(vorderingnummer);
        return this;
    }

    public void setVorderingnummer(String vorderingnummer) {
        this.vorderingnummer = vorderingnummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vordering)) {
            return false;
        }
        return getId() != null && getId().equals(((Vordering) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vordering{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", bedragbtw='" + getBedragbtw() + "'" +
            ", datumaanmaak='" + getDatumaanmaak() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", geaccordeerd='" + getGeaccordeerd() + "'" +
            ", geaccordeerddoor='" + getGeaccordeerddoor() + "'" +
            ", geaccordeerdop='" + getGeaccordeerdop() + "'" +
            ", geexporteerd='" + getGeexporteerd() + "'" +
            ", gemuteerddoor='" + getGemuteerddoor() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", totaalbedrag='" + getTotaalbedrag() + "'" +
            ", totaalbedraginclusief='" + getTotaalbedraginclusief() + "'" +
            ", vorderingnummer='" + getVorderingnummer() + "'" +
            "}";
    }
}
