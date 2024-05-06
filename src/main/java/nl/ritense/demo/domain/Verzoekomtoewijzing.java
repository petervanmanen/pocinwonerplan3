package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Verzoekomtoewijzing.
 */
@Entity
@Table(name = "verzoekomtoewijzing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verzoekomtoewijzing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschikkingsnummer")
    private String beschikkingsnummer;

    @Column(name = "commentaar")
    private String commentaar;

    @Column(name = "datumeindetoewijzing")
    private LocalDate datumeindetoewijzing;

    @Column(name = "datumingangbeschikking")
    private LocalDate datumingangbeschikking;

    @Column(name = "datumingangtoewijzing")
    private LocalDate datumingangtoewijzing;

    @Column(name = "datumontvangst")
    private LocalDate datumontvangst;

    @Column(name = "eenheid")
    private String eenheid;

    @Column(name = "frequentie")
    private String frequentie;

    @Column(name = "raamcontract")
    private Boolean raamcontract;

    @Column(name = "referentieaanbieder")
    private String referentieaanbieder;

    @Column(name = "soortverwijzer")
    private String soortverwijzer;

    @Column(name = "verwijzer")
    private String verwijzer;

    @Column(name = "volume")
    private String volume;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verzoekomtoewijzing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschikkingsnummer() {
        return this.beschikkingsnummer;
    }

    public Verzoekomtoewijzing beschikkingsnummer(String beschikkingsnummer) {
        this.setBeschikkingsnummer(beschikkingsnummer);
        return this;
    }

    public void setBeschikkingsnummer(String beschikkingsnummer) {
        this.beschikkingsnummer = beschikkingsnummer;
    }

    public String getCommentaar() {
        return this.commentaar;
    }

    public Verzoekomtoewijzing commentaar(String commentaar) {
        this.setCommentaar(commentaar);
        return this;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public LocalDate getDatumeindetoewijzing() {
        return this.datumeindetoewijzing;
    }

    public Verzoekomtoewijzing datumeindetoewijzing(LocalDate datumeindetoewijzing) {
        this.setDatumeindetoewijzing(datumeindetoewijzing);
        return this;
    }

    public void setDatumeindetoewijzing(LocalDate datumeindetoewijzing) {
        this.datumeindetoewijzing = datumeindetoewijzing;
    }

    public LocalDate getDatumingangbeschikking() {
        return this.datumingangbeschikking;
    }

    public Verzoekomtoewijzing datumingangbeschikking(LocalDate datumingangbeschikking) {
        this.setDatumingangbeschikking(datumingangbeschikking);
        return this;
    }

    public void setDatumingangbeschikking(LocalDate datumingangbeschikking) {
        this.datumingangbeschikking = datumingangbeschikking;
    }

    public LocalDate getDatumingangtoewijzing() {
        return this.datumingangtoewijzing;
    }

    public Verzoekomtoewijzing datumingangtoewijzing(LocalDate datumingangtoewijzing) {
        this.setDatumingangtoewijzing(datumingangtoewijzing);
        return this;
    }

    public void setDatumingangtoewijzing(LocalDate datumingangtoewijzing) {
        this.datumingangtoewijzing = datumingangtoewijzing;
    }

    public LocalDate getDatumontvangst() {
        return this.datumontvangst;
    }

    public Verzoekomtoewijzing datumontvangst(LocalDate datumontvangst) {
        this.setDatumontvangst(datumontvangst);
        return this;
    }

    public void setDatumontvangst(LocalDate datumontvangst) {
        this.datumontvangst = datumontvangst;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Verzoekomtoewijzing eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getFrequentie() {
        return this.frequentie;
    }

    public Verzoekomtoewijzing frequentie(String frequentie) {
        this.setFrequentie(frequentie);
        return this;
    }

    public void setFrequentie(String frequentie) {
        this.frequentie = frequentie;
    }

    public Boolean getRaamcontract() {
        return this.raamcontract;
    }

    public Verzoekomtoewijzing raamcontract(Boolean raamcontract) {
        this.setRaamcontract(raamcontract);
        return this;
    }

    public void setRaamcontract(Boolean raamcontract) {
        this.raamcontract = raamcontract;
    }

    public String getReferentieaanbieder() {
        return this.referentieaanbieder;
    }

    public Verzoekomtoewijzing referentieaanbieder(String referentieaanbieder) {
        this.setReferentieaanbieder(referentieaanbieder);
        return this;
    }

    public void setReferentieaanbieder(String referentieaanbieder) {
        this.referentieaanbieder = referentieaanbieder;
    }

    public String getSoortverwijzer() {
        return this.soortverwijzer;
    }

    public Verzoekomtoewijzing soortverwijzer(String soortverwijzer) {
        this.setSoortverwijzer(soortverwijzer);
        return this;
    }

    public void setSoortverwijzer(String soortverwijzer) {
        this.soortverwijzer = soortverwijzer;
    }

    public String getVerwijzer() {
        return this.verwijzer;
    }

    public Verzoekomtoewijzing verwijzer(String verwijzer) {
        this.setVerwijzer(verwijzer);
        return this;
    }

    public void setVerwijzer(String verwijzer) {
        this.verwijzer = verwijzer;
    }

    public String getVolume() {
        return this.volume;
    }

    public Verzoekomtoewijzing volume(String volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verzoekomtoewijzing)) {
            return false;
        }
        return getId() != null && getId().equals(((Verzoekomtoewijzing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verzoekomtoewijzing{" +
            "id=" + getId() +
            ", beschikkingsnummer='" + getBeschikkingsnummer() + "'" +
            ", commentaar='" + getCommentaar() + "'" +
            ", datumeindetoewijzing='" + getDatumeindetoewijzing() + "'" +
            ", datumingangbeschikking='" + getDatumingangbeschikking() + "'" +
            ", datumingangtoewijzing='" + getDatumingangtoewijzing() + "'" +
            ", datumontvangst='" + getDatumontvangst() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", frequentie='" + getFrequentie() + "'" +
            ", raamcontract='" + getRaamcontract() + "'" +
            ", referentieaanbieder='" + getReferentieaanbieder() + "'" +
            ", soortverwijzer='" + getSoortverwijzer() + "'" +
            ", verwijzer='" + getVerwijzer() + "'" +
            ", volume='" + getVolume() + "'" +
            "}";
    }
}
