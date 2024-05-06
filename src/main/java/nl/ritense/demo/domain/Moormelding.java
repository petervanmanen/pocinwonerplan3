package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Moormelding.
 */
@Entity
@Table(name = "moormelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Moormelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresaanduiding")
    private String adresaanduiding;

    @Column(name = "datumaanmelding")
    private String datumaanmelding;

    @Column(name = "datumgoedkeuring")
    private String datumgoedkeuring;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Column(name = "goedgekeurd")
    private Boolean goedgekeurd;

    @Column(name = "herstelwerkzaamhedenvereist")
    private Boolean herstelwerkzaamhedenvereist;

    @Column(name = "omschrijvingherstelwerkzaamheden")
    private String omschrijvingherstelwerkzaamheden;

    @Column(name = "publiceren")
    private Boolean publiceren;

    @Column(name = "starttijd")
    private String starttijd;

    @Column(name = "wegbeheerder")
    private String wegbeheerder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Moormelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresaanduiding() {
        return this.adresaanduiding;
    }

    public Moormelding adresaanduiding(String adresaanduiding) {
        this.setAdresaanduiding(adresaanduiding);
        return this;
    }

    public void setAdresaanduiding(String adresaanduiding) {
        this.adresaanduiding = adresaanduiding;
    }

    public String getDatumaanmelding() {
        return this.datumaanmelding;
    }

    public Moormelding datumaanmelding(String datumaanmelding) {
        this.setDatumaanmelding(datumaanmelding);
        return this;
    }

    public void setDatumaanmelding(String datumaanmelding) {
        this.datumaanmelding = datumaanmelding;
    }

    public String getDatumgoedkeuring() {
        return this.datumgoedkeuring;
    }

    public Moormelding datumgoedkeuring(String datumgoedkeuring) {
        this.setDatumgoedkeuring(datumgoedkeuring);
        return this;
    }

    public void setDatumgoedkeuring(String datumgoedkeuring) {
        this.datumgoedkeuring = datumgoedkeuring;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Moormelding eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public Boolean getGoedgekeurd() {
        return this.goedgekeurd;
    }

    public Moormelding goedgekeurd(Boolean goedgekeurd) {
        this.setGoedgekeurd(goedgekeurd);
        return this;
    }

    public void setGoedgekeurd(Boolean goedgekeurd) {
        this.goedgekeurd = goedgekeurd;
    }

    public Boolean getHerstelwerkzaamhedenvereist() {
        return this.herstelwerkzaamhedenvereist;
    }

    public Moormelding herstelwerkzaamhedenvereist(Boolean herstelwerkzaamhedenvereist) {
        this.setHerstelwerkzaamhedenvereist(herstelwerkzaamhedenvereist);
        return this;
    }

    public void setHerstelwerkzaamhedenvereist(Boolean herstelwerkzaamhedenvereist) {
        this.herstelwerkzaamhedenvereist = herstelwerkzaamhedenvereist;
    }

    public String getOmschrijvingherstelwerkzaamheden() {
        return this.omschrijvingherstelwerkzaamheden;
    }

    public Moormelding omschrijvingherstelwerkzaamheden(String omschrijvingherstelwerkzaamheden) {
        this.setOmschrijvingherstelwerkzaamheden(omschrijvingherstelwerkzaamheden);
        return this;
    }

    public void setOmschrijvingherstelwerkzaamheden(String omschrijvingherstelwerkzaamheden) {
        this.omschrijvingherstelwerkzaamheden = omschrijvingherstelwerkzaamheden;
    }

    public Boolean getPubliceren() {
        return this.publiceren;
    }

    public Moormelding publiceren(Boolean publiceren) {
        this.setPubliceren(publiceren);
        return this;
    }

    public void setPubliceren(Boolean publiceren) {
        this.publiceren = publiceren;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Moormelding starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public String getWegbeheerder() {
        return this.wegbeheerder;
    }

    public Moormelding wegbeheerder(String wegbeheerder) {
        this.setWegbeheerder(wegbeheerder);
        return this;
    }

    public void setWegbeheerder(String wegbeheerder) {
        this.wegbeheerder = wegbeheerder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Moormelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Moormelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Moormelding{" +
            "id=" + getId() +
            ", adresaanduiding='" + getAdresaanduiding() + "'" +
            ", datumaanmelding='" + getDatumaanmelding() + "'" +
            ", datumgoedkeuring='" + getDatumgoedkeuring() + "'" +
            ", eindtijd='" + getEindtijd() + "'" +
            ", goedgekeurd='" + getGoedgekeurd() + "'" +
            ", herstelwerkzaamhedenvereist='" + getHerstelwerkzaamhedenvereist() + "'" +
            ", omschrijvingherstelwerkzaamheden='" + getOmschrijvingherstelwerkzaamheden() + "'" +
            ", publiceren='" + getPubliceren() + "'" +
            ", starttijd='" + getStarttijd() + "'" +
            ", wegbeheerder='" + getWegbeheerder() + "'" +
            "}";
    }
}
