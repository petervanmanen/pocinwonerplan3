package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Enkelvoudigdocument.
 */
@Entity
@Table(name = "enkelvoudigdocument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enkelvoudigdocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bestandsnaam")
    private String bestandsnaam;

    @Size(max = 10)
    @Column(name = "documentformaat", length = 10)
    private String documentformaat;

    @Column(name = "documentinhoud")
    private String documentinhoud;

    @Column(name = "documentlink")
    private String documentlink;

    @Size(max = 20)
    @Column(name = "documentstatus", length = 20)
    private String documentstatus;

    @Size(max = 20)
    @Column(name = "documenttaal", length = 20)
    private String documenttaal;

    @Column(name = "documentversie")
    private String documentversie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enkelvoudigdocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsnaam() {
        return this.bestandsnaam;
    }

    public Enkelvoudigdocument bestandsnaam(String bestandsnaam) {
        this.setBestandsnaam(bestandsnaam);
        return this;
    }

    public void setBestandsnaam(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public String getDocumentformaat() {
        return this.documentformaat;
    }

    public Enkelvoudigdocument documentformaat(String documentformaat) {
        this.setDocumentformaat(documentformaat);
        return this;
    }

    public void setDocumentformaat(String documentformaat) {
        this.documentformaat = documentformaat;
    }

    public String getDocumentinhoud() {
        return this.documentinhoud;
    }

    public Enkelvoudigdocument documentinhoud(String documentinhoud) {
        this.setDocumentinhoud(documentinhoud);
        return this;
    }

    public void setDocumentinhoud(String documentinhoud) {
        this.documentinhoud = documentinhoud;
    }

    public String getDocumentlink() {
        return this.documentlink;
    }

    public Enkelvoudigdocument documentlink(String documentlink) {
        this.setDocumentlink(documentlink);
        return this;
    }

    public void setDocumentlink(String documentlink) {
        this.documentlink = documentlink;
    }

    public String getDocumentstatus() {
        return this.documentstatus;
    }

    public Enkelvoudigdocument documentstatus(String documentstatus) {
        this.setDocumentstatus(documentstatus);
        return this;
    }

    public void setDocumentstatus(String documentstatus) {
        this.documentstatus = documentstatus;
    }

    public String getDocumenttaal() {
        return this.documenttaal;
    }

    public Enkelvoudigdocument documenttaal(String documenttaal) {
        this.setDocumenttaal(documenttaal);
        return this;
    }

    public void setDocumenttaal(String documenttaal) {
        this.documenttaal = documenttaal;
    }

    public String getDocumentversie() {
        return this.documentversie;
    }

    public Enkelvoudigdocument documentversie(String documentversie) {
        this.setDocumentversie(documentversie);
        return this;
    }

    public void setDocumentversie(String documentversie) {
        this.documentversie = documentversie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enkelvoudigdocument)) {
            return false;
        }
        return getId() != null && getId().equals(((Enkelvoudigdocument) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enkelvoudigdocument{" +
            "id=" + getId() +
            ", bestandsnaam='" + getBestandsnaam() + "'" +
            ", documentformaat='" + getDocumentformaat() + "'" +
            ", documentinhoud='" + getDocumentinhoud() + "'" +
            ", documentlink='" + getDocumentlink() + "'" +
            ", documentstatus='" + getDocumentstatus() + "'" +
            ", documenttaal='" + getDocumenttaal() + "'" +
            ", documentversie='" + getDocumentversie() + "'" +
            "}";
    }
}
