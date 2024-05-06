import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './huishouden.reducer';

export const HuishoudenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const huishoudenEntity = useAppSelector(state => state.huishouden.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="huishoudenDetailsHeading">Huishouden</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{huishoudenEntity.id}</dd>
          <dt>Maaktonderdeeluitvan Client</dt>
          <dd>
            {huishoudenEntity.maaktonderdeeluitvanClients
              ? huishoudenEntity.maaktonderdeeluitvanClients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {huishoudenEntity.maaktonderdeeluitvanClients && i === huishoudenEntity.maaktonderdeeluitvanClients.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Maaktonderdeelvan Relatie</dt>
          <dd>
            {huishoudenEntity.maaktonderdeelvanRelaties
              ? huishoudenEntity.maaktonderdeelvanRelaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {huishoudenEntity.maaktonderdeelvanRelaties && i === huishoudenEntity.maaktonderdeelvanRelaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/huishouden" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/huishouden/${huishoudenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HuishoudenDetail;
