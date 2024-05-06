import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vastgoedobject.reducer';

export const VastgoedobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vastgoedobjectEntity = useAppSelector(state => state.vastgoedobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vastgoedobjectDetailsHeading">Vastgoedobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vastgoedobjectEntity.id}</dd>
          <dt>
            <span id="aantaletages">Aantaletages</span>
          </dt>
          <dd>{vastgoedobjectEntity.aantaletages}</dd>
          <dt>
            <span id="aantalparkeerplaatsen">Aantalparkeerplaatsen</span>
          </dt>
          <dd>{vastgoedobjectEntity.aantalparkeerplaatsen}</dd>
          <dt>
            <span id="aantalrioleringen">Aantalrioleringen</span>
          </dt>
          <dd>{vastgoedobjectEntity.aantalrioleringen}</dd>
          <dt>
            <span id="adresaanduiding">Adresaanduiding</span>
          </dt>
          <dd>{vastgoedobjectEntity.adresaanduiding}</dd>
          <dt>
            <span id="afgekochteerfpacht">Afgekochteerfpacht</span>
          </dt>
          <dd>{vastgoedobjectEntity.afgekochteerfpacht}</dd>
          <dt>
            <span id="afgesprokenconditiescore">Afgesprokenconditiescore</span>
          </dt>
          <dd>{vastgoedobjectEntity.afgesprokenconditiescore}</dd>
          <dt>
            <span id="afkoopwaarde">Afkoopwaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.afkoopwaarde}</dd>
          <dt>
            <span id="asbestrapportageaanwezig">Asbestrapportageaanwezig</span>
          </dt>
          <dd>{vastgoedobjectEntity.asbestrapportageaanwezig}</dd>
          <dt>
            <span id="bedragaankoop">Bedragaankoop</span>
          </dt>
          <dd>{vastgoedobjectEntity.bedragaankoop}</dd>
          <dt>
            <span id="bestemmingsplan">Bestemmingsplan</span>
          </dt>
          <dd>{vastgoedobjectEntity.bestemmingsplan}</dd>
          <dt>
            <span id="boekwaarde">Boekwaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.boekwaarde}</dd>
          <dt>
            <span id="bouwjaar">Bouwjaar</span>
          </dt>
          <dd>{vastgoedobjectEntity.bouwjaar}</dd>
          <dt>
            <span id="bouwwerk">Bouwwerk</span>
          </dt>
          <dd>{vastgoedobjectEntity.bouwwerk}</dd>
          <dt>
            <span id="bovenliggendniveau">Bovenliggendniveau</span>
          </dt>
          <dd>{vastgoedobjectEntity.bovenliggendniveau}</dd>
          <dt>
            <span id="bovenliggendniveaucode">Bovenliggendniveaucode</span>
          </dt>
          <dd>{vastgoedobjectEntity.bovenliggendniveaucode}</dd>
          <dt>
            <span id="brutovloeroppervlakte">Brutovloeroppervlakte</span>
          </dt>
          <dd>{vastgoedobjectEntity.brutovloeroppervlakte}</dd>
          <dt>
            <span id="co2uitstoot">Co 2 Uitstoot</span>
          </dt>
          <dd>{vastgoedobjectEntity.co2uitstoot}</dd>
          <dt>
            <span id="conditiescore">Conditiescore</span>
          </dt>
          <dd>{vastgoedobjectEntity.conditiescore}</dd>
          <dt>
            <span id="datumafstoten">Datumafstoten</span>
          </dt>
          <dd>
            {vastgoedobjectEntity.datumafstoten ? (
              <TextFormat value={vastgoedobjectEntity.datumafstoten} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumberekeningoppervlak">Datumberekeningoppervlak</span>
          </dt>
          <dd>
            {vastgoedobjectEntity.datumberekeningoppervlak ? (
              <TextFormat value={vastgoedobjectEntity.datumberekeningoppervlak} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeigendom">Datumeigendom</span>
          </dt>
          <dd>
            {vastgoedobjectEntity.datumeigendom ? (
              <TextFormat value={vastgoedobjectEntity.datumeigendom} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumverkoop">Datumverkoop</span>
          </dt>
          <dd>
            {vastgoedobjectEntity.datumverkoop ? (
              <TextFormat value={vastgoedobjectEntity.datumverkoop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deelportefeuille">Deelportefeuille</span>
          </dt>
          <dd>{vastgoedobjectEntity.deelportefeuille}</dd>
          <dt>
            <span id="energiekosten">Energiekosten</span>
          </dt>
          <dd>{vastgoedobjectEntity.energiekosten}</dd>
          <dt>
            <span id="energielabel">Energielabel</span>
          </dt>
          <dd>{vastgoedobjectEntity.energielabel}</dd>
          <dt>
            <span id="energieverbruik">Energieverbruik</span>
          </dt>
          <dd>{vastgoedobjectEntity.energieverbruik}</dd>
          <dt>
            <span id="fiscalewaarde">Fiscalewaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.fiscalewaarde}</dd>
          <dt>
            <span id="foto">Foto</span>
          </dt>
          <dd>{vastgoedobjectEntity.foto}</dd>
          <dt>
            <span id="gearchiveerd">Gearchiveerd</span>
          </dt>
          <dd>{vastgoedobjectEntity.gearchiveerd}</dd>
          <dt>
            <span id="herbouwwaarde">Herbouwwaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.herbouwwaarde}</dd>
          <dt>
            <span id="hoofdstuk">Hoofdstuk</span>
          </dt>
          <dd>{vastgoedobjectEntity.hoofdstuk}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{vastgoedobjectEntity.identificatie}</dd>
          <dt>
            <span id="jaarlaatsterenovatie">Jaarlaatsterenovatie</span>
          </dt>
          <dd>{vastgoedobjectEntity.jaarlaatsterenovatie}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{vastgoedobjectEntity.locatie}</dd>
          <dt>
            <span id="marktwaarde">Marktwaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.marktwaarde}</dd>
          <dt>
            <span id="monument">Monument</span>
          </dt>
          <dd>{vastgoedobjectEntity.monument}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{vastgoedobjectEntity.naam}</dd>
          <dt>
            <span id="eobjectstatus">Eobjectstatus</span>
          </dt>
          <dd>{vastgoedobjectEntity.eobjectstatus}</dd>
          <dt>
            <span id="eobjectstatuscode">Eobjectstatuscode</span>
          </dt>
          <dd>{vastgoedobjectEntity.eobjectstatuscode}</dd>
          <dt>
            <span id="eobjecttype">Eobjecttype</span>
          </dt>
          <dd>{vastgoedobjectEntity.eobjecttype}</dd>
          <dt>
            <span id="eobjecttypecode">Eobjecttypecode</span>
          </dt>
          <dd>{vastgoedobjectEntity.eobjecttypecode}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{vastgoedobjectEntity.omschrijving}</dd>
          <dt>
            <span id="onderhoudscategorie">Onderhoudscategorie</span>
          </dt>
          <dd>{vastgoedobjectEntity.onderhoudscategorie}</dd>
          <dt>
            <span id="oppervlaktekantoor">Oppervlaktekantoor</span>
          </dt>
          <dd>{vastgoedobjectEntity.oppervlaktekantoor}</dd>
          <dt>
            <span id="portefeuille">Portefeuille</span>
          </dt>
          <dd>{vastgoedobjectEntity.portefeuille}</dd>
          <dt>
            <span id="portefeuillecode">Portefeuillecode</span>
          </dt>
          <dd>{vastgoedobjectEntity.portefeuillecode}</dd>
          <dt>
            <span id="provincie">Provincie</span>
          </dt>
          <dd>{vastgoedobjectEntity.provincie}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{vastgoedobjectEntity.toelichting}</dd>
          <dt>
            <span id="verhuurbaarvloeroppervlak">Verhuurbaarvloeroppervlak</span>
          </dt>
          <dd>{vastgoedobjectEntity.verhuurbaarvloeroppervlak}</dd>
          <dt>
            <span id="verkoopbaarheid">Verkoopbaarheid</span>
          </dt>
          <dd>{vastgoedobjectEntity.verkoopbaarheid}</dd>
          <dt>
            <span id="verkoopbedrag">Verkoopbedrag</span>
          </dt>
          <dd>{vastgoedobjectEntity.verkoopbedrag}</dd>
          <dt>
            <span id="verzekerdewaarde">Verzekerdewaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.verzekerdewaarde}</dd>
          <dt>
            <span id="waardegrond">Waardegrond</span>
          </dt>
          <dd>{vastgoedobjectEntity.waardegrond}</dd>
          <dt>
            <span id="waardeopstal">Waardeopstal</span>
          </dt>
          <dd>{vastgoedobjectEntity.waardeopstal}</dd>
          <dt>
            <span id="wijk">Wijk</span>
          </dt>
          <dd>{vastgoedobjectEntity.wijk}</dd>
          <dt>
            <span id="wozwaarde">Wozwaarde</span>
          </dt>
          <dd>{vastgoedobjectEntity.wozwaarde}</dd>
          <dt>Betreft Pand</dt>
          <dd>{vastgoedobjectEntity.betreftPand ? vastgoedobjectEntity.betreftPand.id : ''}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{vastgoedobjectEntity.heeftKostenplaats ? vastgoedobjectEntity.heeftKostenplaats.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vastgoedobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vastgoedobject/${vastgoedobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VastgoedobjectDetail;
