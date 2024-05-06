import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './relatie.reducer';

export const RelatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const relatieEntity = useAppSelector(state => state.relatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="relatieDetailsHeading">Relatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{relatieEntity.id}</dd>
          <dt>
            <span id="relatiesoort">Relatiesoort</span>
          </dt>
          <dd>{relatieEntity.relatiesoort}</dd>
          <dt>Issoort Relatiesoort</dt>
          <dd>{relatieEntity.issoortRelatiesoort ? relatieEntity.issoortRelatiesoort.id : ''}</dd>
          <dt>Maaktonderdeelvan Huishouden</dt>
          <dd>
            {relatieEntity.maaktonderdeelvanHuishoudens
              ? relatieEntity.maaktonderdeelvanHuishoudens.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {relatieEntity.maaktonderdeelvanHuishoudens && i === relatieEntity.maaktonderdeelvanHuishoudens.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeftrelatie Client</dt>
          <dd>
            {relatieEntity.heeftrelatieClients
              ? relatieEntity.heeftrelatieClients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {relatieEntity.heeftrelatieClients && i === relatieEntity.heeftrelatieClients.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/relatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/relatie/${relatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RelatieDetail;
