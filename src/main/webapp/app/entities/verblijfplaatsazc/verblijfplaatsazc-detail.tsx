import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfplaatsazc.reducer';

export const VerblijfplaatsazcDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfplaatsazcEntity = useAppSelector(state => state.verblijfplaatsazc.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfplaatsazcDetailsHeading">Verblijfplaatsazc</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfplaatsazcEntity.id}</dd>
        </dl>
        <Button tag={Link} to="/verblijfplaatsazc" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verblijfplaatsazc/${verblijfplaatsazcEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfplaatsazcDetail;
