import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './organisatorischeeenheidhr.reducer';

export const OrganisatorischeeenheidhrDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const organisatorischeeenheidhrEntity = useAppSelector(state => state.organisatorischeeenheidhr.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organisatorischeeenheidhrDetailsHeading">Organisatorischeeenheidhr</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{organisatorischeeenheidhrEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{organisatorischeeenheidhrEntity.naam}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{organisatorischeeenheidhrEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/organisatorischeeenheidhr" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organisatorischeeenheidhr/${organisatorischeeenheidhrEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrganisatorischeeenheidhrDetail;
