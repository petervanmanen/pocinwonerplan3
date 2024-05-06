import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bouwactiviteit.reducer';

export const BouwactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bouwactiviteitEntity = useAppSelector(state => state.bouwactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bouwactiviteitDetailsHeading">Bouwactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bouwactiviteitEntity.id}</dd>
          <dt>
            <span id="bouwjaarklasse">Bouwjaarklasse</span>
          </dt>
          <dd>{bouwactiviteitEntity.bouwjaarklasse}</dd>
          <dt>
            <span id="bouwjaartot">Bouwjaartot</span>
          </dt>
          <dd>{bouwactiviteitEntity.bouwjaartot}</dd>
          <dt>
            <span id="bouwjaarvan">Bouwjaarvan</span>
          </dt>
          <dd>{bouwactiviteitEntity.bouwjaarvan}</dd>
          <dt>
            <span id="indicatie">Indicatie</span>
          </dt>
          <dd>{bouwactiviteitEntity.indicatie}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{bouwactiviteitEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/bouwactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bouwactiviteit/${bouwactiviteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BouwactiviteitDetail;
