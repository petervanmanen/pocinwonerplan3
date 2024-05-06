package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Aanvraaginkooporder.
 */
@Entity
@Table(name = "aanvraaginkooporder")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanvraaginkooporder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "betalingovermeerjaren")
    private String betalingovermeerjaren;

    @Column(name = "correspondentienummer")
    private String correspondentienummer;

    @Column(name = "inhuuranders")
    private String inhuuranders;

    @Column(name = "leveringofdienst")
    private String leveringofdienst;

    @Column(name = "nettototaalbedrag", precision = 21, scale = 2)
    private BigDecimal nettototaalbedrag;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "onderwerp")
    private String onderwerp;

    @Column(name = "reactie")
    private String reactie;

    @Column(name = "status")
    private String status;

    @Column(name = "wijzevaninhuur")
    private String wijzevaninhuur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanvraaginkooporder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBetalingovermeerjaren() {
        return this.betalingovermeerjaren;
    }

    public Aanvraaginkooporder betalingovermeerjaren(String betalingovermeerjaren) {
        this.setBetalingovermeerjaren(betalingovermeerjaren);
        return this;
    }

    public void setBetalingovermeerjaren(String betalingovermeerjaren) {
        this.betalingovermeerjaren = betalingovermeerjaren;
    }

    public String getCorrespondentienummer() {
        return this.correspondentienummer;
    }

    public Aanvraaginkooporder correspondentienummer(String correspondentienummer) {
        this.setCorrespondentienummer(correspondentienummer);
        return this;
    }

    public void setCorrespondentienummer(String correspondentienummer) {
        this.correspondentienummer = correspondentienummer;
    }

    public String getInhuuranders() {
        return this.inhuuranders;
    }

    public Aanvraaginkooporder inhuuranders(String inhuuranders) {
        this.setInhuuranders(inhuuranders);
        return this;
    }

    public void setInhuuranders(String inhuuranders) {
        this.inhuuranders = inhuuranders;
    }

    public String getLeveringofdienst() {
        return this.leveringofdienst;
    }

    public Aanvraaginkooporder leveringofdienst(String leveringofdienst) {
        this.setLeveringofdienst(leveringofdienst);
        return this;
    }

    public void setLeveringofdienst(String leveringofdienst) {
        this.leveringofdienst = leveringofdienst;
    }

    public BigDecimal getNettototaalbedrag() {
        return this.nettototaalbedrag;
    }

    public Aanvraaginkooporder nettototaalbedrag(BigDecimal nettototaalbedrag) {
        this.setNettototaalbedrag(nettototaalbedrag);
        return this;
    }

    public void setNettototaalbedrag(BigDecimal nettototaalbedrag) {
        this.nettototaalbedrag = nettototaalbedrag;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Aanvraaginkooporder omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOnderwerp() {
        return this.onderwerp;
    }

    public Aanvraaginkooporder onderwerp(String onderwerp) {
        this.setOnderwerp(onderwerp);
        return this;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public String getReactie() {
        return this.reactie;
    }

    public Aanvraaginkooporder reactie(String reactie) {
        this.setReactie(reactie);
        return this;
    }

    public void setReactie(String reactie) {
        this.reactie = reactie;
    }

    public String getStatus() {
        return this.status;
    }

    public Aanvraaginkooporder status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWijzevaninhuur() {
        return this.wijzevaninhuur;
    }

    public Aanvraaginkooporder wijzevaninhuur(String wijzevaninhuur) {
        this.setWijzevaninhuur(wijzevaninhuur);
        return this;
    }

    public void setWijzevaninhuur(String wijzevaninhuur) {
        this.wijzevaninhuur = wijzevaninhuur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanvraaginkooporder)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanvraaginkooporder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanvraaginkooporder{" +
            "id=" + getId() +
            ", betalingovermeerjaren='" + getBetalingovermeerjaren() + "'" +
            ", correspondentienummer='" + getCorrespondentienummer() + "'" +
            ", inhuuranders='" + getInhuuranders() + "'" +
            ", leveringofdienst='" + getLeveringofdienst() + "'" +
            ", nettototaalbedrag=" + getNettototaalbedrag() +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", onderwerp='" + getOnderwerp() + "'" +
            ", reactie='" + getReactie() + "'" +
            ", status='" + getStatus() + "'" +
            ", wijzevaninhuur='" + getWijzevaninhuur() + "'" +
            "}";
    }
}
