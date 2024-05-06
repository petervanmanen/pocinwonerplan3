import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aansluitput.reducer';

export const AansluitputDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aansluitputEntity = useAppSelector(state => state.aansluitput.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aansluitputDetailsHeading">Aansluitput</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aansluitputEntity.id}</dd>
          <dt>
            <span id="aansluitpunt">Aansluitpunt</span>
          </dt>
          <dd>{aansluitputEntity.aansluitpunt}</dd>
          <dt>
            <span id="risicogebied">Risicogebied</span>
          </dt>
          <dd>{aansluitputEntity.risicogebied}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{aansluitputEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/aansluitput" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aansluitput/${aansluitputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AansluitputDetail;
