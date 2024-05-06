import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './strooiroute.reducer';

export const StrooirouteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const strooirouteEntity = useAppSelector(state => state.strooiroute.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="strooirouteDetailsHeading">Strooiroute</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{strooirouteEntity.id}</dd>
          <dt>
            <span id="eroute">Eroute</span>
          </dt>
          <dd>{strooirouteEntity.eroute}</dd>
        </dl>
        <Button tag={Link} to="/strooiroute" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strooiroute/${strooirouteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StrooirouteDetail;
