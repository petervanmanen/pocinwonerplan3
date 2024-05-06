import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './redenbeeindiging.reducer';

export const RedenbeeindigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const redenbeeindigingEntity = useAppSelector(state => state.redenbeeindiging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="redenbeeindigingDetailsHeading">Redenbeeindiging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{redenbeeindigingEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{redenbeeindigingEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{redenbeeindigingEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/redenbeeindiging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/redenbeeindiging/${redenbeeindigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RedenbeeindigingDetail;
