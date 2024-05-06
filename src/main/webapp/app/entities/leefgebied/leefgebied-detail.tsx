import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leefgebied.reducer';

export const LeefgebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leefgebiedEntity = useAppSelector(state => state.leefgebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leefgebiedDetailsHeading">Leefgebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leefgebiedEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{leefgebiedEntity.naam}</dd>
        </dl>
        <Button tag={Link} to="/leefgebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leefgebied/${leefgebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeefgebiedDetail;
