package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vondst.
 */
@Entity
@Table(name = "vondst")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vondst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "key")
    private String key;

    @Column(name = "keyvulling")
    private String keyvulling;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "omstandigheden")
    private String omstandigheden;

    @Size(max = 20)
    @Column(name = "projectcd", length = 20)
    private String projectcd;

    @Size(max = 20)
    @Column(name = "putnummer", length = 20)
    private String putnummer;

    @Size(max = 20)
    @Column(name = "spoornummer", length = 20)
    private String spoornummer;

    @Size(max = 20)
    @Column(name = "vlaknummer", length = 20)
    private String vlaknummer;

    @Size(max = 20)
    @Column(name = "vondstnummer", length = 20)
    private String vondstnummer;

    @Size(max = 20)
    @Column(name = "vullingnummer", length = 20)
    private String vullingnummer;

    @Column(name = "xcoordinaat")
    private String xcoordinaat;

    @Column(name = "ycoordinaat")
    private String ycoordinaat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatVondst")
    @JsonIgnoreProperties(value = { "zitinDoos", "isvansoortArtefactsoort", "bevatVondst" }, allowSetters = true)
    private Set<Artefact> bevatArtefacts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVondsts", "heeftSpoor" }, allowSetters = true)
    private Vulling heeftVulling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vondst id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Vondst datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getKey() {
        return this.key;
    }

    public Vondst key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyvulling() {
        return this.keyvulling;
    }

    public Vondst keyvulling(String keyvulling) {
        this.setKeyvulling(keyvulling);
        return this;
    }

    public void setKeyvulling(String keyvulling) {
        this.keyvulling = keyvulling;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Vondst omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmstandigheden() {
        return this.omstandigheden;
    }

    public Vondst omstandigheden(String omstandigheden) {
        this.setOmstandigheden(omstandigheden);
        return this;
    }

    public void setOmstandigheden(String omstandigheden) {
        this.omstandigheden = omstandigheden;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Vondst projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getPutnummer() {
        return this.putnummer;
    }

    public Vondst putnummer(String putnummer) {
        this.setPutnummer(putnummer);
        return this;
    }

    public void setPutnummer(String putnummer) {
        this.putnummer = putnummer;
    }

    public String getSpoornummer() {
        return this.spoornummer;
    }

    public Vondst spoornummer(String spoornummer) {
        this.setSpoornummer(spoornummer);
        return this;
    }

    public void setSpoornummer(String spoornummer) {
        this.spoornummer = spoornummer;
    }

    public String getVlaknummer() {
        return this.vlaknummer;
    }

    public Vondst vlaknummer(String vlaknummer) {
        this.setVlaknummer(vlaknummer);
        return this;
    }

    public void setVlaknummer(String vlaknummer) {
        this.vlaknummer = vlaknummer;
    }

    public String getVondstnummer() {
        return this.vondstnummer;
    }

    public Vondst vondstnummer(String vondstnummer) {
        this.setVondstnummer(vondstnummer);
        return this;
    }

    public void setVondstnummer(String vondstnummer) {
        this.vondstnummer = vondstnummer;
    }

    public String getVullingnummer() {
        return this.vullingnummer;
    }

    public Vondst vullingnummer(String vullingnummer) {
        this.setVullingnummer(vullingnummer);
        return this;
    }

    public void setVullingnummer(String vullingnummer) {
        this.vullingnummer = vullingnummer;
    }

    public String getXcoordinaat() {
        return this.xcoordinaat;
    }

    public Vondst xcoordinaat(String xcoordinaat) {
        this.setXcoordinaat(xcoordinaat);
        return this;
    }

    public void setXcoordinaat(String xcoordinaat) {
        this.xcoordinaat = xcoordinaat;
    }

    public String getYcoordinaat() {
        return this.ycoordinaat;
    }

    public Vondst ycoordinaat(String ycoordinaat) {
        this.setYcoordinaat(ycoordinaat);
        return this;
    }

    public void setYcoordinaat(String ycoordinaat) {
        this.ycoordinaat = ycoordinaat;
    }

    public Set<Artefact> getBevatArtefacts() {
        return this.bevatArtefacts;
    }

    public void setBevatArtefacts(Set<Artefact> artefacts) {
        if (this.bevatArtefacts != null) {
            this.bevatArtefacts.forEach(i -> i.setBevatVondst(null));
        }
        if (artefacts != null) {
            artefacts.forEach(i -> i.setBevatVondst(this));
        }
        this.bevatArtefacts = artefacts;
    }

    public Vondst bevatArtefacts(Set<Artefact> artefacts) {
        this.setBevatArtefacts(artefacts);
        return this;
    }

    public Vondst addBevatArtefact(Artefact artefact) {
        this.bevatArtefacts.add(artefact);
        artefact.setBevatVondst(this);
        return this;
    }

    public Vondst removeBevatArtefact(Artefact artefact) {
        this.bevatArtefacts.remove(artefact);
        artefact.setBevatVondst(null);
        return this;
    }

    public Vulling getHeeftVulling() {
        return this.heeftVulling;
    }

    public void setHeeftVulling(Vulling vulling) {
        this.heeftVulling = vulling;
    }

    public Vondst heeftVulling(Vulling vulling) {
        this.setHeeftVulling(vulling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vondst)) {
            return false;
        }
        return getId() != null && getId().equals(((Vondst) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vondst{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", key='" + getKey() + "'" +
            ", keyvulling='" + getKeyvulling() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", omstandigheden='" + getOmstandigheden() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", putnummer='" + getPutnummer() + "'" +
            ", spoornummer='" + getSpoornummer() + "'" +
            ", vlaknummer='" + getVlaknummer() + "'" +
            ", vondstnummer='" + getVondstnummer() + "'" +
            ", vullingnummer='" + getVullingnummer() + "'" +
            ", xcoordinaat='" + getXcoordinaat() + "'" +
            ", ycoordinaat='" + getYcoordinaat() + "'" +
            "}";
    }
}
