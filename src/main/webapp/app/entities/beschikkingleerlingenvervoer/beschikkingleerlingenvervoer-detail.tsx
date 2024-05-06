import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beschikkingleerlingenvervoer.reducer';

export const BeschikkingleerlingenvervoerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beschikkingleerlingenvervoerEntity = useAppSelector(state => state.beschikkingleerlingenvervoer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beschikkingleerlingenvervoerDetailsHeading">Beschikkingleerlingenvervoer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beschikkingleerlingenvervoerEntity.id}</dd>
        </dl>
        <Button tag={Link} to="/beschikkingleerlingenvervoer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beschikkingleerlingenvervoer/${beschikkingleerlingenvervoerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeschikkingleerlingenvervoerDetail;
