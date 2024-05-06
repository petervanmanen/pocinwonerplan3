import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './adresseerbaarobject.reducer';

export const AdresseerbaarobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adresseerbaarobjectEntity = useAppSelector(state => state.adresseerbaarobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adresseerbaarobjectDetailsHeading">Adresseerbaarobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{adresseerbaarobjectEntity.id}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{adresseerbaarobjectEntity.identificatie}</dd>
          <dt>
            <span id="typeadresseerbaarobject">Typeadresseerbaarobject</span>
          </dt>
          <dd>{adresseerbaarobjectEntity.typeadresseerbaarobject}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{adresseerbaarobjectEntity.versie}</dd>
        </dl>
        <Button tag={Link} to="/adresseerbaarobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adresseerbaarobject/${adresseerbaarobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdresseerbaarobjectDetail;
