import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rit.reducer';

export const RitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ritEntity = useAppSelector(state => state.rit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ritDetailsHeading">Rit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ritEntity.id}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{ritEntity.eindtijd}</dd>
          <dt>
            <span id="ritcode">Ritcode</span>
          </dt>
          <dd>{ritEntity.ritcode}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{ritEntity.starttijd}</dd>
          <dt>Uitgevoerdmet Vuilniswagen</dt>
          <dd>{ritEntity.uitgevoerdmetVuilniswagen ? ritEntity.uitgevoerdmetVuilniswagen.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rit/${ritEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RitDetail;
