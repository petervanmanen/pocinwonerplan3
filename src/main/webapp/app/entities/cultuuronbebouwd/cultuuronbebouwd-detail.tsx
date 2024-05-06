import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cultuuronbebouwd.reducer';

export const CultuuronbebouwdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cultuuronbebouwdEntity = useAppSelector(state => state.cultuuronbebouwd.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cultuuronbebouwdDetailsHeading">Cultuuronbebouwd</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cultuuronbebouwdEntity.id}</dd>
          <dt>
            <span id="cultuurcodeonbebouwd">Cultuurcodeonbebouwd</span>
          </dt>
          <dd>{cultuuronbebouwdEntity.cultuurcodeonbebouwd}</dd>
        </dl>
        <Button tag={Link} to="/cultuuronbebouwd" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cultuuronbebouwd/${cultuuronbebouwdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CultuuronbebouwdDetail;
