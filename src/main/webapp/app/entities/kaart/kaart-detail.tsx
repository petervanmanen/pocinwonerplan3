import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kaart.reducer';

export const KaartDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kaartEntity = useAppSelector(state => state.kaart.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kaartDetailsHeading">Kaart</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kaartEntity.id}</dd>
          <dt>
            <span id="kaart">Kaart</span>
          </dt>
          <dd>{kaartEntity.kaart}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{kaartEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{kaartEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/kaart" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kaart/${kaartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KaartDetail;
