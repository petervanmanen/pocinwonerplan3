import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inkooppakket.reducer';

export const InkooppakketDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inkooppakketEntity = useAppSelector(state => state.inkooppakket.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inkooppakketDetailsHeading">Inkooppakket</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inkooppakketEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{inkooppakketEntity.code}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{inkooppakketEntity.naam}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{inkooppakketEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/inkooppakket" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inkooppakket/${inkooppakketEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InkooppakketDetail;
