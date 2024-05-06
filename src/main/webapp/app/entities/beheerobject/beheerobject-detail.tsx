import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beheerobject.reducer';

export const BeheerobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beheerobjectEntity = useAppSelector(state => state.beheerobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beheerobjectDetailsHeading">Beheerobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beheerobjectEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{beheerobjectEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="begingarantieperiode">Begingarantieperiode</span>
          </dt>
          <dd>
            {beheerobjectEntity.begingarantieperiode ? (
              <TextFormat value={beheerobjectEntity.begingarantieperiode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="beheergebied">Beheergebied</span>
          </dt>
          <dd>{beheerobjectEntity.beheergebied}</dd>
          <dt>
            <span id="beheerobjectbeheervak">Beheerobjectbeheervak</span>
          </dt>
          <dd>{beheerobjectEntity.beheerobjectbeheervak}</dd>
          <dt>
            <span id="beheerobjectgebruiksfunctie">Beheerobjectgebruiksfunctie</span>
          </dt>
          <dd>{beheerobjectEntity.beheerobjectgebruiksfunctie}</dd>
          <dt>
            <span id="beheerobjectmemo">Beheerobjectmemo</span>
          </dt>
          <dd>{beheerobjectEntity.beheerobjectmemo}</dd>
          <dt>
            <span id="beschermdefloraenfauna">Beschermdefloraenfauna</span>
          </dt>
          <dd>{beheerobjectEntity.beschermdefloraenfauna ? 'true' : 'false'}</dd>
          <dt>
            <span id="buurt">Buurt</span>
          </dt>
          <dd>{beheerobjectEntity.buurt}</dd>
          <dt>
            <span id="conversieid">Conversieid</span>
          </dt>
          <dd>{beheerobjectEntity.conversieid}</dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {beheerobjectEntity.datummutatie ? (
              <TextFormat value={beheerobjectEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumoplevering">Datumoplevering</span>
          </dt>
          <dd>
            {beheerobjectEntity.datumoplevering ? (
              <TextFormat value={beheerobjectEntity.datumoplevering} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumpublicatielv">Datumpublicatielv</span>
          </dt>
          <dd>
            {beheerobjectEntity.datumpublicatielv ? (
              <TextFormat value={beheerobjectEntity.datumpublicatielv} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumverwijdering">Datumverwijdering</span>
          </dt>
          <dd>
            {beheerobjectEntity.datumverwijdering ? (
              <TextFormat value={beheerobjectEntity.datumverwijdering} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eindegarantieperiode">Eindegarantieperiode</span>
          </dt>
          <dd>
            {beheerobjectEntity.eindegarantieperiode ? (
              <TextFormat value={beheerobjectEntity.eindegarantieperiode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gebiedstype">Gebiedstype</span>
          </dt>
          <dd>{beheerobjectEntity.gebiedstype}</dd>
          <dt>
            <span id="gemeente">Gemeente</span>
          </dt>
          <dd>{beheerobjectEntity.gemeente}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{beheerobjectEntity.geometrie}</dd>
          <dt>
            <span id="gewijzigddoor">Gewijzigddoor</span>
          </dt>
          <dd>{beheerobjectEntity.gewijzigddoor}</dd>
          <dt>
            <span id="grondsoort">Grondsoort</span>
          </dt>
          <dd>{beheerobjectEntity.grondsoort}</dd>
          <dt>
            <span id="grondsoortplus">Grondsoortplus</span>
          </dt>
          <dd>{beheerobjectEntity.grondsoortplus}</dd>
          <dt>
            <span id="identificatieimbor">Identificatieimbor</span>
          </dt>
          <dd>{beheerobjectEntity.identificatieimbor}</dd>
          <dt>
            <span id="identificatieimgeo">Identificatieimgeo</span>
          </dt>
          <dd>{beheerobjectEntity.identificatieimgeo}</dd>
          <dt>
            <span id="jaarvanaanleg">Jaarvanaanleg</span>
          </dt>
          <dd>{beheerobjectEntity.jaarvanaanleg}</dd>
          <dt>
            <span id="eobjectbegintijd">Eobjectbegintijd</span>
          </dt>
          <dd>{beheerobjectEntity.eobjectbegintijd}</dd>
          <dt>
            <span id="eobjecteindtijd">Eobjecteindtijd</span>
          </dt>
          <dd>{beheerobjectEntity.eobjecteindtijd}</dd>
          <dt>
            <span id="onderhoudsplichtige">Onderhoudsplichtige</span>
          </dt>
          <dd>{beheerobjectEntity.onderhoudsplichtige}</dd>
          <dt>
            <span id="openbareruimte">Openbareruimte</span>
          </dt>
          <dd>{beheerobjectEntity.openbareruimte}</dd>
          <dt>
            <span id="postcode">Postcode</span>
          </dt>
          <dd>{beheerobjectEntity.postcode}</dd>
          <dt>
            <span id="relatievehoogteligging">Relatievehoogteligging</span>
          </dt>
          <dd>{beheerobjectEntity.relatievehoogteligging}</dd>
          <dt>
            <span id="stadsdeel">Stadsdeel</span>
          </dt>
          <dd>{beheerobjectEntity.stadsdeel}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{beheerobjectEntity.status}</dd>
          <dt>
            <span id="theoretischeindejaar">Theoretischeindejaar</span>
          </dt>
          <dd>{beheerobjectEntity.theoretischeindejaar}</dd>
          <dt>
            <span id="tijdstipregistratie">Tijdstipregistratie</span>
          </dt>
          <dd>{beheerobjectEntity.tijdstipregistratie}</dd>
          <dt>
            <span id="typebeheerder">Typebeheerder</span>
          </dt>
          <dd>{beheerobjectEntity.typebeheerder}</dd>
          <dt>
            <span id="typebeheerderplus">Typebeheerderplus</span>
          </dt>
          <dd>{beheerobjectEntity.typebeheerderplus}</dd>
          <dt>
            <span id="typeeigenaar">Typeeigenaar</span>
          </dt>
          <dd>{beheerobjectEntity.typeeigenaar}</dd>
          <dt>
            <span id="typeeigenaarplus">Typeeigenaarplus</span>
          </dt>
          <dd>{beheerobjectEntity.typeeigenaarplus}</dd>
          <dt>
            <span id="typeligging">Typeligging</span>
          </dt>
          <dd>{beheerobjectEntity.typeligging}</dd>
          <dt>
            <span id="waterschap">Waterschap</span>
          </dt>
          <dd>{beheerobjectEntity.waterschap}</dd>
          <dt>
            <span id="wijk">Wijk</span>
          </dt>
          <dd>{beheerobjectEntity.wijk}</dd>
          <dt>
            <span id="woonplaats">Woonplaats</span>
          </dt>
          <dd>{beheerobjectEntity.woonplaats}</dd>
          <dt>
            <span id="zettingsgevoeligheid">Zettingsgevoeligheid</span>
          </dt>
          <dd>{beheerobjectEntity.zettingsgevoeligheid}</dd>
          <dt>
            <span id="zettingsgevoeligheidplus">Zettingsgevoeligheidplus</span>
          </dt>
          <dd>{beheerobjectEntity.zettingsgevoeligheidplus}</dd>
          <dt>Betreft Melding</dt>
          <dd>
            {beheerobjectEntity.betreftMeldings
              ? beheerobjectEntity.betreftMeldings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {beheerobjectEntity.betreftMeldings && i === beheerobjectEntity.betreftMeldings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/beheerobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beheerobject/${beheerobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeheerobjectDetail;
