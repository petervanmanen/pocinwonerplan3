package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Speeltoestel.
 */
@Entity
@Table(name = "speeltoestel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Speeltoestel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "catalogusprijs")
    private String catalogusprijs;

    @Column(name = "certificaat")
    private Boolean certificaat;

    @Size(max = 20)
    @Column(name = "certificaatnummer", length = 20)
    private String certificaatnummer;

    @Column(name = "certificeringsinstantie")
    private String certificeringsinstantie;

    @Column(name = "controlefrequentie")
    private String controlefrequentie;

    @Column(name = "datumcertificaat")
    private LocalDate datumcertificaat;

    @Column(name = "gemakkelijktoegankelijk")
    private Boolean gemakkelijktoegankelijk;

    @Column(name = "inspectievolgorde")
    private String inspectievolgorde;

    @Column(name = "installatiekosten")
    private String installatiekosten;

    @Column(name = "speelterrein")
    private String speelterrein;

    @Column(name = "speeltoesteltoestelonderdeel")
    private String speeltoesteltoestelonderdeel;

    @Column(name = "technischelevensduur")
    private String technischelevensduur;

    @Column(name = "toestelcode")
    private String toestelcode;

    @Column(name = "toestelgroep")
    private String toestelgroep;

    @Column(name = "toestelnaam")
    private String toestelnaam;

    @Column(name = "type")
    private String type;

    @Column(name = "typenummer")
    private String typenummer;

    @Column(name = "typeplus")
    private String typeplus;

    @Size(max = 100)
    @Column(name = "typeplus_2", length = 100)
    private String typeplus2;

    @Column(name = "valruimtehoogte")
    private String valruimtehoogte;

    @Column(name = "valruimteomvang")
    private String valruimteomvang;

    @Column(name = "vrijevalhoogte")
    private String vrijevalhoogte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Speeltoestel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogusprijs() {
        return this.catalogusprijs;
    }

    public Speeltoestel catalogusprijs(String catalogusprijs) {
        this.setCatalogusprijs(catalogusprijs);
        return this;
    }

    public void setCatalogusprijs(String catalogusprijs) {
        this.catalogusprijs = catalogusprijs;
    }

    public Boolean getCertificaat() {
        return this.certificaat;
    }

    public Speeltoestel certificaat(Boolean certificaat) {
        this.setCertificaat(certificaat);
        return this;
    }

    public void setCertificaat(Boolean certificaat) {
        this.certificaat = certificaat;
    }

    public String getCertificaatnummer() {
        return this.certificaatnummer;
    }

    public Speeltoestel certificaatnummer(String certificaatnummer) {
        this.setCertificaatnummer(certificaatnummer);
        return this;
    }

    public void setCertificaatnummer(String certificaatnummer) {
        this.certificaatnummer = certificaatnummer;
    }

    public String getCertificeringsinstantie() {
        return this.certificeringsinstantie;
    }

    public Speeltoestel certificeringsinstantie(String certificeringsinstantie) {
        this.setCertificeringsinstantie(certificeringsinstantie);
        return this;
    }

    public void setCertificeringsinstantie(String certificeringsinstantie) {
        this.certificeringsinstantie = certificeringsinstantie;
    }

    public String getControlefrequentie() {
        return this.controlefrequentie;
    }

    public Speeltoestel controlefrequentie(String controlefrequentie) {
        this.setControlefrequentie(controlefrequentie);
        return this;
    }

    public void setControlefrequentie(String controlefrequentie) {
        this.controlefrequentie = controlefrequentie;
    }

    public LocalDate getDatumcertificaat() {
        return this.datumcertificaat;
    }

    public Speeltoestel datumcertificaat(LocalDate datumcertificaat) {
        this.setDatumcertificaat(datumcertificaat);
        return this;
    }

    public void setDatumcertificaat(LocalDate datumcertificaat) {
        this.datumcertificaat = datumcertificaat;
    }

    public Boolean getGemakkelijktoegankelijk() {
        return this.gemakkelijktoegankelijk;
    }

    public Speeltoestel gemakkelijktoegankelijk(Boolean gemakkelijktoegankelijk) {
        this.setGemakkelijktoegankelijk(gemakkelijktoegankelijk);
        return this;
    }

    public void setGemakkelijktoegankelijk(Boolean gemakkelijktoegankelijk) {
        this.gemakkelijktoegankelijk = gemakkelijktoegankelijk;
    }

    public String getInspectievolgorde() {
        return this.inspectievolgorde;
    }

    public Speeltoestel inspectievolgorde(String inspectievolgorde) {
        this.setInspectievolgorde(inspectievolgorde);
        return this;
    }

    public void setInspectievolgorde(String inspectievolgorde) {
        this.inspectievolgorde = inspectievolgorde;
    }

    public String getInstallatiekosten() {
        return this.installatiekosten;
    }

    public Speeltoestel installatiekosten(String installatiekosten) {
        this.setInstallatiekosten(installatiekosten);
        return this;
    }

    public void setInstallatiekosten(String installatiekosten) {
        this.installatiekosten = installatiekosten;
    }

    public String getSpeelterrein() {
        return this.speelterrein;
    }

    public Speeltoestel speelterrein(String speelterrein) {
        this.setSpeelterrein(speelterrein);
        return this;
    }

    public void setSpeelterrein(String speelterrein) {
        this.speelterrein = speelterrein;
    }

    public String getSpeeltoesteltoestelonderdeel() {
        return this.speeltoesteltoestelonderdeel;
    }

    public Speeltoestel speeltoesteltoestelonderdeel(String speeltoesteltoestelonderdeel) {
        this.setSpeeltoesteltoestelonderdeel(speeltoesteltoestelonderdeel);
        return this;
    }

    public void setSpeeltoesteltoestelonderdeel(String speeltoesteltoestelonderdeel) {
        this.speeltoesteltoestelonderdeel = speeltoesteltoestelonderdeel;
    }

    public String getTechnischelevensduur() {
        return this.technischelevensduur;
    }

    public Speeltoestel technischelevensduur(String technischelevensduur) {
        this.setTechnischelevensduur(technischelevensduur);
        return this;
    }

    public void setTechnischelevensduur(String technischelevensduur) {
        this.technischelevensduur = technischelevensduur;
    }

    public String getToestelcode() {
        return this.toestelcode;
    }

    public Speeltoestel toestelcode(String toestelcode) {
        this.setToestelcode(toestelcode);
        return this;
    }

    public void setToestelcode(String toestelcode) {
        this.toestelcode = toestelcode;
    }

    public String getToestelgroep() {
        return this.toestelgroep;
    }

    public Speeltoestel toestelgroep(String toestelgroep) {
        this.setToestelgroep(toestelgroep);
        return this;
    }

    public void setToestelgroep(String toestelgroep) {
        this.toestelgroep = toestelgroep;
    }

    public String getToestelnaam() {
        return this.toestelnaam;
    }

    public Speeltoestel toestelnaam(String toestelnaam) {
        this.setToestelnaam(toestelnaam);
        return this;
    }

    public void setToestelnaam(String toestelnaam) {
        this.toestelnaam = toestelnaam;
    }

    public String getType() {
        return this.type;
    }

    public Speeltoestel type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypenummer() {
        return this.typenummer;
    }

    public Speeltoestel typenummer(String typenummer) {
        this.setTypenummer(typenummer);
        return this;
    }

    public void setTypenummer(String typenummer) {
        this.typenummer = typenummer;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Speeltoestel typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypeplus2() {
        return this.typeplus2;
    }

    public Speeltoestel typeplus2(String typeplus2) {
        this.setTypeplus2(typeplus2);
        return this;
    }

    public void setTypeplus2(String typeplus2) {
        this.typeplus2 = typeplus2;
    }

    public String getValruimtehoogte() {
        return this.valruimtehoogte;
    }

    public Speeltoestel valruimtehoogte(String valruimtehoogte) {
        this.setValruimtehoogte(valruimtehoogte);
        return this;
    }

    public void setValruimtehoogte(String valruimtehoogte) {
        this.valruimtehoogte = valruimtehoogte;
    }

    public String getValruimteomvang() {
        return this.valruimteomvang;
    }

    public Speeltoestel valruimteomvang(String valruimteomvang) {
        this.setValruimteomvang(valruimteomvang);
        return this;
    }

    public void setValruimteomvang(String valruimteomvang) {
        this.valruimteomvang = valruimteomvang;
    }

    public String getVrijevalhoogte() {
        return this.vrijevalhoogte;
    }

    public Speeltoestel vrijevalhoogte(String vrijevalhoogte) {
        this.setVrijevalhoogte(vrijevalhoogte);
        return this;
    }

    public void setVrijevalhoogte(String vrijevalhoogte) {
        this.vrijevalhoogte = vrijevalhoogte;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Speeltoestel)) {
            return false;
        }
        return getId() != null && getId().equals(((Speeltoestel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Speeltoestel{" +
            "id=" + getId() +
            ", catalogusprijs='" + getCatalogusprijs() + "'" +
            ", certificaat='" + getCertificaat() + "'" +
            ", certificaatnummer='" + getCertificaatnummer() + "'" +
            ", certificeringsinstantie='" + getCertificeringsinstantie() + "'" +
            ", controlefrequentie='" + getControlefrequentie() + "'" +
            ", datumcertificaat='" + getDatumcertificaat() + "'" +
            ", gemakkelijktoegankelijk='" + getGemakkelijktoegankelijk() + "'" +
            ", inspectievolgorde='" + getInspectievolgorde() + "'" +
            ", installatiekosten='" + getInstallatiekosten() + "'" +
            ", speelterrein='" + getSpeelterrein() + "'" +
            ", speeltoesteltoestelonderdeel='" + getSpeeltoesteltoestelonderdeel() + "'" +
            ", technischelevensduur='" + getTechnischelevensduur() + "'" +
            ", toestelcode='" + getToestelcode() + "'" +
            ", toestelgroep='" + getToestelgroep() + "'" +
            ", toestelnaam='" + getToestelnaam() + "'" +
            ", type='" + getType() + "'" +
            ", typenummer='" + getTypenummer() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typeplus2='" + getTypeplus2() + "'" +
            ", valruimtehoogte='" + getValruimtehoogte() + "'" +
            ", valruimteomvang='" + getValruimteomvang() + "'" +
            ", vrijevalhoogte='" + getVrijevalhoogte() + "'" +
            "}";
    }
}
