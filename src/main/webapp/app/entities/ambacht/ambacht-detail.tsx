import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ambacht.reducer';

export const AmbachtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ambachtEntity = useAppSelector(state => state.ambacht.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ambachtDetailsHeading">Ambacht</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ambachtEntity.id}</dd>
          <dt>
            <span id="ambachtsoort">Ambachtsoort</span>
          </dt>
          <dd>{ambachtEntity.ambachtsoort}</dd>
          <dt>
            <span id="jaarambachttot">Jaarambachttot</span>
          </dt>
          <dd>{ambachtEntity.jaarambachttot}</dd>
          <dt>
            <span id="jaarambachtvanaf">Jaarambachtvanaf</span>
          </dt>
          <dd>{ambachtEntity.jaarambachtvanaf}</dd>
        </dl>
        <Button tag={Link} to="/ambacht" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ambacht/${ambachtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AmbachtDetail;
