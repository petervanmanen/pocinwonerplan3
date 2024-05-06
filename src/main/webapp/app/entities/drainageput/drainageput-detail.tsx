import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './drainageput.reducer';

export const DrainageputDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const drainageputEntity = useAppSelector(state => state.drainageput.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="drainageputDetailsHeading">Drainageput</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{drainageputEntity.id}</dd>
          <dt>
            <span id="risicogebied">Risicogebied</span>
          </dt>
          <dd>{drainageputEntity.risicogebied}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{drainageputEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/drainageput" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/drainageput/${drainageputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DrainageputDetail;
