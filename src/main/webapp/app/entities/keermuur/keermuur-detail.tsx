import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './keermuur.reducer';

export const KeermuurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const keermuurEntity = useAppSelector(state => state.keermuur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="keermuurDetailsHeading">Keermuur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{keermuurEntity.id}</dd>
          <dt>
            <span id="belastingklassenieuw">Belastingklassenieuw</span>
          </dt>
          <dd>{keermuurEntity.belastingklassenieuw}</dd>
          <dt>
            <span id="belastingklasseoud">Belastingklasseoud</span>
          </dt>
          <dd>{keermuurEntity.belastingklasseoud}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{keermuurEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/keermuur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/keermuur/${keermuurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KeermuurDetail;
