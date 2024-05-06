import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leveringsvorm.reducer';

export const LeveringsvormDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leveringsvormEntity = useAppSelector(state => state.leveringsvorm.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leveringsvormDetailsHeading">Leveringsvorm</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leveringsvormEntity.id}</dd>
          <dt>
            <span id="leveringsvormcode">Leveringsvormcode</span>
          </dt>
          <dd>{leveringsvormEntity.leveringsvormcode}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{leveringsvormEntity.naam}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{leveringsvormEntity.wet}</dd>
        </dl>
        <Button tag={Link} to="/leveringsvorm" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leveringsvorm/${leveringsvormEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeveringsvormDetail;
