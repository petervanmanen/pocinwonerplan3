package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Pomp.
 */
@Entity
@Table(name = "pomp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pomp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanslagniveau")
    private String aanslagniveau;

    @Column(name = "beginstanddraaiurenteller")
    private String beginstanddraaiurenteller;

    @Column(name = "besturingskast")
    private String besturingskast;

    @Column(name = "laatstestanddraaiurenteller")
    private String laatstestanddraaiurenteller;

    @Column(name = "laatstestandkwhteller")
    private String laatstestandkwhteller;

    @Column(name = "levensduur")
    private String levensduur;

    @Column(name = "model")
    private String model;

    @Column(name = "motorvermogen")
    private String motorvermogen;

    @Column(name = "onderdeelmetpomp")
    private String onderdeelmetpomp;

    @Column(name = "ontwerpcapaciteit")
    private String ontwerpcapaciteit;

    @Column(name = "pompcapaciteit")
    private String pompcapaciteit;

    @Column(name = "serienummer")
    private String serienummer;

    @Column(name = "type")
    private String type;

    @Column(name = "typeonderdeelmetpomp")
    private String typeonderdeelmetpomp;

    @Column(name = "typeplus")
    private String typeplus;

    @Column(name = "typewaaier")
    private String typewaaier;

    @Column(name = "uitslagpeil")
    private String uitslagpeil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pomp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanslagniveau() {
        return this.aanslagniveau;
    }

    public Pomp aanslagniveau(String aanslagniveau) {
        this.setAanslagniveau(aanslagniveau);
        return this;
    }

    public void setAanslagniveau(String aanslagniveau) {
        this.aanslagniveau = aanslagniveau;
    }

    public String getBeginstanddraaiurenteller() {
        return this.beginstanddraaiurenteller;
    }

    public Pomp beginstanddraaiurenteller(String beginstanddraaiurenteller) {
        this.setBeginstanddraaiurenteller(beginstanddraaiurenteller);
        return this;
    }

    public void setBeginstanddraaiurenteller(String beginstanddraaiurenteller) {
        this.beginstanddraaiurenteller = beginstanddraaiurenteller;
    }

    public String getBesturingskast() {
        return this.besturingskast;
    }

    public Pomp besturingskast(String besturingskast) {
        this.setBesturingskast(besturingskast);
        return this;
    }

    public void setBesturingskast(String besturingskast) {
        this.besturingskast = besturingskast;
    }

    public String getLaatstestanddraaiurenteller() {
        return this.laatstestanddraaiurenteller;
    }

    public Pomp laatstestanddraaiurenteller(String laatstestanddraaiurenteller) {
        this.setLaatstestanddraaiurenteller(laatstestanddraaiurenteller);
        return this;
    }

    public void setLaatstestanddraaiurenteller(String laatstestanddraaiurenteller) {
        this.laatstestanddraaiurenteller = laatstestanddraaiurenteller;
    }

    public String getLaatstestandkwhteller() {
        return this.laatstestandkwhteller;
    }

    public Pomp laatstestandkwhteller(String laatstestandkwhteller) {
        this.setLaatstestandkwhteller(laatstestandkwhteller);
        return this;
    }

    public void setLaatstestandkwhteller(String laatstestandkwhteller) {
        this.laatstestandkwhteller = laatstestandkwhteller;
    }

    public String getLevensduur() {
        return this.levensduur;
    }

    public Pomp levensduur(String levensduur) {
        this.setLevensduur(levensduur);
        return this;
    }

    public void setLevensduur(String levensduur) {
        this.levensduur = levensduur;
    }

    public String getModel() {
        return this.model;
    }

    public Pomp model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMotorvermogen() {
        return this.motorvermogen;
    }

    public Pomp motorvermogen(String motorvermogen) {
        this.setMotorvermogen(motorvermogen);
        return this;
    }

    public void setMotorvermogen(String motorvermogen) {
        this.motorvermogen = motorvermogen;
    }

    public String getOnderdeelmetpomp() {
        return this.onderdeelmetpomp;
    }

    public Pomp onderdeelmetpomp(String onderdeelmetpomp) {
        this.setOnderdeelmetpomp(onderdeelmetpomp);
        return this;
    }

    public void setOnderdeelmetpomp(String onderdeelmetpomp) {
        this.onderdeelmetpomp = onderdeelmetpomp;
    }

    public String getOntwerpcapaciteit() {
        return this.ontwerpcapaciteit;
    }

    public Pomp ontwerpcapaciteit(String ontwerpcapaciteit) {
        this.setOntwerpcapaciteit(ontwerpcapaciteit);
        return this;
    }

    public void setOntwerpcapaciteit(String ontwerpcapaciteit) {
        this.ontwerpcapaciteit = ontwerpcapaciteit;
    }

    public String getPompcapaciteit() {
        return this.pompcapaciteit;
    }

    public Pomp pompcapaciteit(String pompcapaciteit) {
        this.setPompcapaciteit(pompcapaciteit);
        return this;
    }

    public void setPompcapaciteit(String pompcapaciteit) {
        this.pompcapaciteit = pompcapaciteit;
    }

    public String getSerienummer() {
        return this.serienummer;
    }

    public Pomp serienummer(String serienummer) {
        this.setSerienummer(serienummer);
        return this;
    }

    public void setSerienummer(String serienummer) {
        this.serienummer = serienummer;
    }

    public String getType() {
        return this.type;
    }

    public Pomp type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeonderdeelmetpomp() {
        return this.typeonderdeelmetpomp;
    }

    public Pomp typeonderdeelmetpomp(String typeonderdeelmetpomp) {
        this.setTypeonderdeelmetpomp(typeonderdeelmetpomp);
        return this;
    }

    public void setTypeonderdeelmetpomp(String typeonderdeelmetpomp) {
        this.typeonderdeelmetpomp = typeonderdeelmetpomp;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Pomp typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypewaaier() {
        return this.typewaaier;
    }

    public Pomp typewaaier(String typewaaier) {
        this.setTypewaaier(typewaaier);
        return this;
    }

    public void setTypewaaier(String typewaaier) {
        this.typewaaier = typewaaier;
    }

    public String getUitslagpeil() {
        return this.uitslagpeil;
    }

    public Pomp uitslagpeil(String uitslagpeil) {
        this.setUitslagpeil(uitslagpeil);
        return this;
    }

    public void setUitslagpeil(String uitslagpeil) {
        this.uitslagpeil = uitslagpeil;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pomp)) {
            return false;
        }
        return getId() != null && getId().equals(((Pomp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pomp{" +
            "id=" + getId() +
            ", aanslagniveau='" + getAanslagniveau() + "'" +
            ", beginstanddraaiurenteller='" + getBeginstanddraaiurenteller() + "'" +
            ", besturingskast='" + getBesturingskast() + "'" +
            ", laatstestanddraaiurenteller='" + getLaatstestanddraaiurenteller() + "'" +
            ", laatstestandkwhteller='" + getLaatstestandkwhteller() + "'" +
            ", levensduur='" + getLevensduur() + "'" +
            ", model='" + getModel() + "'" +
            ", motorvermogen='" + getMotorvermogen() + "'" +
            ", onderdeelmetpomp='" + getOnderdeelmetpomp() + "'" +
            ", ontwerpcapaciteit='" + getOntwerpcapaciteit() + "'" +
            ", pompcapaciteit='" + getPompcapaciteit() + "'" +
            ", serienummer='" + getSerienummer() + "'" +
            ", type='" + getType() + "'" +
            ", typeonderdeelmetpomp='" + getTypeonderdeelmetpomp() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typewaaier='" + getTypewaaier() + "'" +
            ", uitslagpeil='" + getUitslagpeil() + "'" +
            "}";
    }
}
