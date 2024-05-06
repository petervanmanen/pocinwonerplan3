import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './waboaanvraagofmelding.reducer';

export const WaboaanvraagofmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const waboaanvraagofmeldingEntity = useAppSelector(state => state.waboaanvraagofmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="waboaanvraagofmeldingDetailsHeading">Waboaanvraagofmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{waboaanvraagofmeldingEntity.id}</dd>
          <dt>
            <span id="bouwkosten">Bouwkosten</span>
          </dt>
          <dd>{waboaanvraagofmeldingEntity.bouwkosten}</dd>
          <dt>
            <span id="olonummer">Olonummer</span>
          </dt>
          <dd>{waboaanvraagofmeldingEntity.olonummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{waboaanvraagofmeldingEntity.omschrijving}</dd>
          <dt>
            <span id="projectkosten">Projectkosten</span>
          </dt>
          <dd>{waboaanvraagofmeldingEntity.projectkosten}</dd>
          <dt>
            <span id="registratienummer">Registratienummer</span>
          </dt>
          <dd>{waboaanvraagofmeldingEntity.registratienummer}</dd>
        </dl>
        <Button tag={Link} to="/waboaanvraagofmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/waboaanvraagofmelding/${waboaanvraagofmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WaboaanvraagofmeldingDetail;
