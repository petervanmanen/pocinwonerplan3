import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sportterrein.reducer';

export const SportterreinDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sportterreinEntity = useAppSelector(state => state.sportterrein.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sportterreinDetailsHeading">Sportterrein</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sportterreinEntity.id}</dd>
          <dt>
            <span id="drainage">Drainage</span>
          </dt>
          <dd>{sportterreinEntity.drainage ? 'true' : 'false'}</dd>
          <dt>
            <span id="gebruiksvorm">Gebruiksvorm</span>
          </dt>
          <dd>{sportterreinEntity.gebruiksvorm}</dd>
          <dt>
            <span id="sportcomplex">Sportcomplex</span>
          </dt>
          <dd>{sportterreinEntity.sportcomplex}</dd>
          <dt>
            <span id="sportterreintypesport">Sportterreintypesport</span>
          </dt>
          <dd>{sportterreinEntity.sportterreintypesport}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{sportterreinEntity.type}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{sportterreinEntity.typeplus}</dd>
          <dt>
            <span id="veldnummer">Veldnummer</span>
          </dt>
          <dd>{sportterreinEntity.veldnummer}</dd>
          <dt>
            <span id="verlicht">Verlicht</span>
          </dt>
          <dd>{sportterreinEntity.verlicht ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/sportterrein" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sportterrein/${sportterreinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SportterreinDetail;
