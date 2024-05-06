import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zaak.reducer';

export const ZaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zaakEntity = useAppSelector(state => state.zaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zaakDetailsHeading">Zaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zaakEntity.id}</dd>
          <dt>
            <span id="empty">Empty</span>
          </dt>
          <dd>{zaakEntity.empty}</dd>
          <dt>
            <span id="archiefnominatie">Archiefnominatie</span>
          </dt>
          <dd>{zaakEntity.archiefnominatie}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{zaakEntity.datumeinde}</dd>
          <dt>
            <span id="datumeindegepland">Datumeindegepland</span>
          </dt>
          <dd>{zaakEntity.datumeindegepland}</dd>
          <dt>
            <span id="datumeindeuiterlijkeafdoening">Datumeindeuiterlijkeafdoening</span>
          </dt>
          <dd>{zaakEntity.datumeindeuiterlijkeafdoening}</dd>
          <dt>
            <span id="datumlaatstebetaling">Datumlaatstebetaling</span>
          </dt>
          <dd>{zaakEntity.datumlaatstebetaling}</dd>
          <dt>
            <span id="datumpublicatie">Datumpublicatie</span>
          </dt>
          <dd>{zaakEntity.datumpublicatie}</dd>
          <dt>
            <span id="datumregistratie">Datumregistratie</span>
          </dt>
          <dd>{zaakEntity.datumregistratie}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{zaakEntity.datumstart}</dd>
          <dt>
            <span id="datumvernietigingdossier">Datumvernietigingdossier</span>
          </dt>
          <dd>{zaakEntity.datumvernietigingdossier}</dd>
          <dt>
            <span id="document">Document</span>
          </dt>
          <dd>{zaakEntity.document}</dd>
          <dt>
            <span id="duurverlenging">Duurverlenging</span>
          </dt>
          <dd>{zaakEntity.duurverlenging}</dd>
          <dt>
            <span id="indicatiebetaling">Indicatiebetaling</span>
          </dt>
          <dd>{zaakEntity.indicatiebetaling}</dd>
          <dt>
            <span id="indicatiedeelzaken">Indicatiedeelzaken</span>
          </dt>
          <dd>{zaakEntity.indicatiedeelzaken}</dd>
          <dt>
            <span id="indicatieopschorting">Indicatieopschorting</span>
          </dt>
          <dd>{zaakEntity.indicatieopschorting}</dd>
          <dt>
            <span id="leges">Leges</span>
          </dt>
          <dd>{zaakEntity.leges}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{zaakEntity.omschrijving}</dd>
          <dt>
            <span id="omschrijvingresultaat">Omschrijvingresultaat</span>
          </dt>
          <dd>{zaakEntity.omschrijvingresultaat}</dd>
          <dt>
            <span id="redenopschorting">Redenopschorting</span>
          </dt>
          <dd>{zaakEntity.redenopschorting}</dd>
          <dt>
            <span id="redenverlenging">Redenverlenging</span>
          </dt>
          <dd>{zaakEntity.redenverlenging}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{zaakEntity.toelichting}</dd>
          <dt>
            <span id="toelichtingresultaat">Toelichtingresultaat</span>
          </dt>
          <dd>{zaakEntity.toelichtingresultaat}</dd>
          <dt>
            <span id="vertrouwelijkheid">Vertrouwelijkheid</span>
          </dt>
          <dd>{zaakEntity.vertrouwelijkheid}</dd>
          <dt>
            <span id="zaakidentificatie">Zaakidentificatie</span>
          </dt>
          <dd>{zaakEntity.zaakidentificatie}</dd>
          <dt>
            <span id="zaakniveau">Zaakniveau</span>
          </dt>
          <dd>{zaakEntity.zaakniveau}</dd>
          <dt>Heeftproduct Producttype</dt>
          <dd>{zaakEntity.heeftproductProducttype ? zaakEntity.heeftproductProducttype.id : ''}</dd>
          <dt>Heeft Klantbeoordeling</dt>
          <dd>{zaakEntity.heeftKlantbeoordeling ? zaakEntity.heeftKlantbeoordeling.id : ''}</dd>
          <dt>Heeft Heffing</dt>
          <dd>{zaakEntity.heeftHeffing ? zaakEntity.heeftHeffing.id : ''}</dd>
          <dt>Betreft Project</dt>
          <dd>{zaakEntity.betreftProject ? zaakEntity.betreftProject.id : ''}</dd>
          <dt>Isvan Zaaktype</dt>
          <dd>{zaakEntity.isvanZaaktype ? zaakEntity.isvanZaaktype.id : ''}</dd>
          <dt>Kent Document</dt>
          <dd>
            {zaakEntity.kentDocuments
              ? zaakEntity.kentDocuments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {zaakEntity.kentDocuments && i === zaakEntity.kentDocuments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Afhandelendmedewerker Medewerker</dt>
          <dd>
            {zaakEntity.afhandelendmedewerkerMedewerkers
              ? zaakEntity.afhandelendmedewerkerMedewerkers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {zaakEntity.afhandelendmedewerkerMedewerkers && i === zaakEntity.afhandelendmedewerkerMedewerkers.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Grondslag</dt>
          <dd>
            {zaakEntity.heeftGrondslags
              ? zaakEntity.heeftGrondslags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {zaakEntity.heeftGrondslags && i === zaakEntity.heeftGrondslags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Uitgevoerdbinnen Bedrijfsproces</dt>
          <dd>
            {zaakEntity.uitgevoerdbinnenBedrijfsproces
              ? zaakEntity.uitgevoerdbinnenBedrijfsproces.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {zaakEntity.uitgevoerdbinnenBedrijfsproces && i === zaakEntity.uitgevoerdbinnenBedrijfsproces.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Oefentuit Betrokkene</dt>
          <dd>
            {zaakEntity.oefentuitBetrokkenes
              ? zaakEntity.oefentuitBetrokkenes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {zaakEntity.oefentuitBetrokkenes && i === zaakEntity.oefentuitBetrokkenes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/zaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zaak/${zaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZaakDetail;
