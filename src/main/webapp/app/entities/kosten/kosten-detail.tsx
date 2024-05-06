import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kosten.reducer';

export const KostenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kostenEntity = useAppSelector(state => state.kosten.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kostenDetailsHeading">Kosten</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kostenEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{kostenEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{kostenEntity.aantal}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{kostenEntity.bedrag}</dd>
          <dt>
            <span id="bedragtotaal">Bedragtotaal</span>
          </dt>
          <dd>{kostenEntity.bedragtotaal}</dd>
          <dt>
            <span id="datumaanmaak">Datumaanmaak</span>
          </dt>
          <dd>
            {kostenEntity.datumaanmaak ? <TextFormat value={kostenEntity.datumaanmaak} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {kostenEntity.datummutatie ? <TextFormat value={kostenEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{kostenEntity.eenheid}</dd>
          <dt>
            <span id="geaccordeerd">Geaccordeerd</span>
          </dt>
          <dd>{kostenEntity.geaccordeerd}</dd>
          <dt>
            <span id="gefactureerdop">Gefactureerdop</span>
          </dt>
          <dd>
            {kostenEntity.gefactureerdop ? (
              <TextFormat value={kostenEntity.gefactureerdop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gemuteerddoor">Gemuteerddoor</span>
          </dt>
          <dd>{kostenEntity.gemuteerddoor}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{kostenEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{kostenEntity.omschrijving}</dd>
          <dt>
            <span id="opbasisvangrondslag">Opbasisvangrondslag</span>
          </dt>
          <dd>{kostenEntity.opbasisvangrondslag}</dd>
          <dt>
            <span id="tarief">Tarief</span>
          </dt>
          <dd>{kostenEntity.tarief}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{kostenEntity.type}</dd>
          <dt>
            <span id="vastgesteldbedrag">Vastgesteldbedrag</span>
          </dt>
          <dd>{kostenEntity.vastgesteldbedrag}</dd>
        </dl>
        <Button tag={Link} to="/kosten" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kosten/${kostenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KostenDetail;
