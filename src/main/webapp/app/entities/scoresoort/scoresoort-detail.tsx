import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './scoresoort.reducer';

export const ScoresoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scoresoortEntity = useAppSelector(state => state.scoresoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="scoresoortDetailsHeading">Scoresoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{scoresoortEntity.id}</dd>
          <dt>
            <span id="niveau">Niveau</span>
          </dt>
          <dd>{scoresoortEntity.niveau}</dd>
        </dl>
        <Button tag={Link} to="/scoresoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scoresoort/${scoresoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScoresoortDetail;
