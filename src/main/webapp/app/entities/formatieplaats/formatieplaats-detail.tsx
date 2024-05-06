import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './formatieplaats.reducer';

export const FormatieplaatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const formatieplaatsEntity = useAppSelector(state => state.formatieplaats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formatieplaatsDetailsHeading">Formatieplaats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{formatieplaatsEntity.id}</dd>
          <dt>
            <span id="urenperweek">Urenperweek</span>
          </dt>
          <dd>{formatieplaatsEntity.urenperweek}</dd>
        </dl>
        <Button tag={Link} to="/formatieplaats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/formatieplaats/${formatieplaatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormatieplaatsDetail;
