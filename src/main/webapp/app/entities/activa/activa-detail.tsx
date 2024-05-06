import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activa.reducer';

export const ActivaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activaEntity = useAppSelector(state => state.activa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activaDetailsHeading">Activa</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activaEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{activaEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{activaEntity.omschrijving}</dd>
          <dt>Issoort Activasoort</dt>
          <dd>{activaEntity.issoortActivasoort ? activaEntity.issoortActivasoort.id : ''}</dd>
          <dt>Heeft Hoofdrekening</dt>
          <dd>
            {activaEntity.heeftHoofdrekenings
              ? activaEntity.heeftHoofdrekenings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {activaEntity.heeftHoofdrekenings && i === activaEntity.heeftHoofdrekenings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/activa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activa/${activaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivaDetail;
