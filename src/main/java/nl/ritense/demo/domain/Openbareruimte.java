package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Openbareruimte.
 */
@Entity
@Table(name = "openbareruimte")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Openbareruimte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "huisnummerrangeevenenonevennummers")
    private String huisnummerrangeevenenonevennummers;

    @Column(name = "huisnummerrangeevennummers")
    private String huisnummerrangeevennummers;

    @Column(name = "huisnummerrangeonevennummers")
    private String huisnummerrangeonevennummers;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "labelnaam")
    private String labelnaam;

    @Column(name = "naamopenbareruimte")
    private String naamopenbareruimte;

    @Column(name = "status")
    private String status;

    @Column(name = "straatcode")
    private String straatcode;

    @Column(name = "straatnaam")
    private String straatnaam;

    @Column(name = "typeopenbareruimte")
    private String typeopenbareruimte;

    @Column(name = "versie")
    private String versie;

    @Column(name = "wegsegment")
    private String wegsegment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Openbareruimte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Openbareruimte datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Openbareruimte datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Openbareruimte datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Openbareruimte datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Openbareruimte geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Openbareruimte geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getHuisnummerrangeevenenonevennummers() {
        return this.huisnummerrangeevenenonevennummers;
    }

    public Openbareruimte huisnummerrangeevenenonevennummers(String huisnummerrangeevenenonevennummers) {
        this.setHuisnummerrangeevenenonevennummers(huisnummerrangeevenenonevennummers);
        return this;
    }

    public void setHuisnummerrangeevenenonevennummers(String huisnummerrangeevenenonevennummers) {
        this.huisnummerrangeevenenonevennummers = huisnummerrangeevenenonevennummers;
    }

    public String getHuisnummerrangeevennummers() {
        return this.huisnummerrangeevennummers;
    }

    public Openbareruimte huisnummerrangeevennummers(String huisnummerrangeevennummers) {
        this.setHuisnummerrangeevennummers(huisnummerrangeevennummers);
        return this;
    }

    public void setHuisnummerrangeevennummers(String huisnummerrangeevennummers) {
        this.huisnummerrangeevennummers = huisnummerrangeevennummers;
    }

    public String getHuisnummerrangeonevennummers() {
        return this.huisnummerrangeonevennummers;
    }

    public Openbareruimte huisnummerrangeonevennummers(String huisnummerrangeonevennummers) {
        this.setHuisnummerrangeonevennummers(huisnummerrangeonevennummers);
        return this;
    }

    public void setHuisnummerrangeonevennummers(String huisnummerrangeonevennummers) {
        this.huisnummerrangeonevennummers = huisnummerrangeonevennummers;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Openbareruimte identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Openbareruimte inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getLabelnaam() {
        return this.labelnaam;
    }

    public Openbareruimte labelnaam(String labelnaam) {
        this.setLabelnaam(labelnaam);
        return this;
    }

    public void setLabelnaam(String labelnaam) {
        this.labelnaam = labelnaam;
    }

    public String getNaamopenbareruimte() {
        return this.naamopenbareruimte;
    }

    public Openbareruimte naamopenbareruimte(String naamopenbareruimte) {
        this.setNaamopenbareruimte(naamopenbareruimte);
        return this;
    }

    public void setNaamopenbareruimte(String naamopenbareruimte) {
        this.naamopenbareruimte = naamopenbareruimte;
    }

    public String getStatus() {
        return this.status;
    }

    public Openbareruimte status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStraatcode() {
        return this.straatcode;
    }

    public Openbareruimte straatcode(String straatcode) {
        this.setStraatcode(straatcode);
        return this;
    }

    public void setStraatcode(String straatcode) {
        this.straatcode = straatcode;
    }

    public String getStraatnaam() {
        return this.straatnaam;
    }

    public Openbareruimte straatnaam(String straatnaam) {
        this.setStraatnaam(straatnaam);
        return this;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public String getTypeopenbareruimte() {
        return this.typeopenbareruimte;
    }

    public Openbareruimte typeopenbareruimte(String typeopenbareruimte) {
        this.setTypeopenbareruimte(typeopenbareruimte);
        return this;
    }

    public void setTypeopenbareruimte(String typeopenbareruimte) {
        this.typeopenbareruimte = typeopenbareruimte;
    }

    public String getVersie() {
        return this.versie;
    }

    public Openbareruimte versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public String getWegsegment() {
        return this.wegsegment;
    }

    public Openbareruimte wegsegment(String wegsegment) {
        this.setWegsegment(wegsegment);
        return this;
    }

    public void setWegsegment(String wegsegment) {
        this.wegsegment = wegsegment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Openbareruimte)) {
            return false;
        }
        return getId() != null && getId().equals(((Openbareruimte) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Openbareruimte{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", huisnummerrangeevenenonevennummers='" + getHuisnummerrangeevenenonevennummers() + "'" +
            ", huisnummerrangeevennummers='" + getHuisnummerrangeevennummers() + "'" +
            ", huisnummerrangeonevennummers='" + getHuisnummerrangeonevennummers() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", labelnaam='" + getLabelnaam() + "'" +
            ", naamopenbareruimte='" + getNaamopenbareruimte() + "'" +
            ", status='" + getStatus() + "'" +
            ", straatcode='" + getStraatcode() + "'" +
            ", straatnaam='" + getStraatnaam() + "'" +
            ", typeopenbareruimte='" + getTypeopenbareruimte() + "'" +
            ", versie='" + getVersie() + "'" +
            ", wegsegment='" + getWegsegment() + "'" +
            "}";
    }
}
