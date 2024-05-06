import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kolk.reducer';

export const KolkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kolkEntity = useAppSelector(state => state.kolk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kolkDetailsHeading">Kolk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kolkEntity.id}</dd>
          <dt>
            <span id="bereikbaarheidkolk">Bereikbaarheidkolk</span>
          </dt>
          <dd>{kolkEntity.bereikbaarheidkolk}</dd>
          <dt>
            <span id="risicogebied">Risicogebied</span>
          </dt>
          <dd>{kolkEntity.risicogebied}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{kolkEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/kolk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kolk/${kolkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KolkDetail;
