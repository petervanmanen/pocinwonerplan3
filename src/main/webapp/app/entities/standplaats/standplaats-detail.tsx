import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './standplaats.reducer';

export const StandplaatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const standplaatsEntity = useAppSelector(state => state.standplaats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="standplaatsDetailsHeading">Standplaats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{standplaatsEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{standplaatsEntity.adres}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{standplaatsEntity.beschrijving}</dd>
          <dt>
            <span id="naaminstelling">Naaminstelling</span>
          </dt>
          <dd>{standplaatsEntity.naaminstelling}</dd>
        </dl>
        <Button tag={Link} to="/standplaats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/standplaats/${standplaatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StandplaatsDetail;
