import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './uitlaatconstructie.reducer';

export const UitlaatconstructieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const uitlaatconstructieEntity = useAppSelector(state => state.uitlaatconstructie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="uitlaatconstructieDetailsHeading">Uitlaatconstructie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{uitlaatconstructieEntity.id}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{uitlaatconstructieEntity.type}</dd>
          <dt>
            <span id="waterobject">Waterobject</span>
          </dt>
          <dd>{uitlaatconstructieEntity.waterobject}</dd>
        </dl>
        <Button tag={Link} to="/uitlaatconstructie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/uitlaatconstructie/${uitlaatconstructieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UitlaatconstructieDetail;
