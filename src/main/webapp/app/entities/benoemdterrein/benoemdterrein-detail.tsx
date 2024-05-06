import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benoemdterrein.reducer';

export const BenoemdterreinDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benoemdterreinEntity = useAppSelector(state => state.benoemdterrein.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benoemdterreinDetailsHeading">Benoemdterrein</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{benoemdterreinEntity.id}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{benoemdterreinEntity.identificatie}</dd>
        </dl>
        <Button tag={Link} to="/benoemdterrein" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benoemdterrein/${benoemdterreinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenoemdterreinDetail;
