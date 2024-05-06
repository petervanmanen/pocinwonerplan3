import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fietsparkeervoorziening.reducer';

export const FietsparkeervoorzieningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fietsparkeervoorzieningEntity = useAppSelector(state => state.fietsparkeervoorziening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fietsparkeervoorzieningDetailsHeading">Fietsparkeervoorziening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fietsparkeervoorzieningEntity.id}</dd>
          <dt>
            <span id="aantalparkeerplaatsen">Aantalparkeerplaatsen</span>
          </dt>
          <dd>{fietsparkeervoorzieningEntity.aantalparkeerplaatsen}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{fietsparkeervoorzieningEntity.type}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{fietsparkeervoorzieningEntity.typeplus}</dd>
        </dl>
        <Button tag={Link} to="/fietsparkeervoorziening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fietsparkeervoorziening/${fietsparkeervoorzieningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FietsparkeervoorzieningDetail;
