import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ligplaats.reducer';

export const LigplaatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ligplaatsEntity = useAppSelector(state => state.ligplaats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ligplaatsDetailsHeading">Ligplaats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ligplaatsEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {ligplaatsEntity.datumbegingeldigheid ? (
              <TextFormat value={ligplaatsEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {ligplaatsEntity.datumeinde ? (
              <TextFormat value={ligplaatsEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {ligplaatsEntity.datumeindegeldigheid ? (
              <TextFormat value={ligplaatsEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {ligplaatsEntity.datumingang ? (
              <TextFormat value={ligplaatsEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentdatum">Documentdatum</span>
          </dt>
          <dd>
            {ligplaatsEntity.documentdatum ? (
              <TextFormat value={ligplaatsEntity.documentdatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentnummer">Documentnummer</span>
          </dt>
          <dd>{ligplaatsEntity.documentnummer}</dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{ligplaatsEntity.geconstateerd}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{ligplaatsEntity.geometrie}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{ligplaatsEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{ligplaatsEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{ligplaatsEntity.status}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{ligplaatsEntity.versie}</dd>
        </dl>
        <Button tag={Link} to="/ligplaats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ligplaats/${ligplaatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LigplaatsDetail;
