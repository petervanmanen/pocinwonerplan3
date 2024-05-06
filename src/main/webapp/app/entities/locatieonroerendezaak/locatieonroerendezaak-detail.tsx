import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locatieonroerendezaak.reducer';

export const LocatieonroerendezaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locatieonroerendezaakEntity = useAppSelector(state => state.locatieonroerendezaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locatieonroerendezaakDetailsHeading">Locatieonroerendezaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locatieonroerendezaakEntity.id}</dd>
          <dt>
            <span id="adrestype">Adrestype</span>
          </dt>
          <dd>{locatieonroerendezaakEntity.adrestype}</dd>
          <dt>
            <span id="cultuurcodebebouwd">Cultuurcodebebouwd</span>
          </dt>
          <dd>{locatieonroerendezaakEntity.cultuurcodebebouwd}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {locatieonroerendezaakEntity.datumbegingeldigheid ? (
              <TextFormat value={locatieonroerendezaakEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {locatieonroerendezaakEntity.datumeindegeldigheid ? (
              <TextFormat value={locatieonroerendezaakEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{locatieonroerendezaakEntity.geometrie}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{locatieonroerendezaakEntity.locatieomschrijving}</dd>
        </dl>
        <Button tag={Link} to="/locatieonroerendezaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locatieonroerendezaak/${locatieonroerendezaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocatieonroerendezaakDetail;
