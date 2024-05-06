import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rioleringsgebied.reducer';

export const RioleringsgebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rioleringsgebiedEntity = useAppSelector(state => state.rioleringsgebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rioleringsgebiedDetailsHeading">Rioleringsgebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rioleringsgebiedEntity.id}</dd>
          <dt>
            <span id="rioleringsgebied">Rioleringsgebied</span>
          </dt>
          <dd>{rioleringsgebiedEntity.rioleringsgebied}</dd>
          <dt>
            <span id="zuiveringsgebied">Zuiveringsgebied</span>
          </dt>
          <dd>{rioleringsgebiedEntity.zuiveringsgebied}</dd>
        </dl>
        <Button tag={Link} to="/rioleringsgebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rioleringsgebied/${rioleringsgebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RioleringsgebiedDetail;
