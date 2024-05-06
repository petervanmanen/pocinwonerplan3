import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rondleiding.reducer';

export const RondleidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rondleidingEntity = useAppSelector(state => state.rondleiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rondleidingDetailsHeading">Rondleiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rondleidingEntity.id}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{rondleidingEntity.eindtijd}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{rondleidingEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{rondleidingEntity.omschrijving}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{rondleidingEntity.starttijd}</dd>
          <dt>Voor Tentoonstelling</dt>
          <dd>{rondleidingEntity.voorTentoonstelling ? rondleidingEntity.voorTentoonstelling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rondleiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rondleiding/${rondleidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RondleidingDetail;
