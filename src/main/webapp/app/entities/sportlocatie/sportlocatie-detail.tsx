import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sportlocatie.reducer';

export const SportlocatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sportlocatieEntity = useAppSelector(state => state.sportlocatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sportlocatieDetailsHeading">Sportlocatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sportlocatieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{sportlocatieEntity.naam}</dd>
          <dt>Gebruikt School</dt>
          <dd>
            {sportlocatieEntity.gebruiktSchools
              ? sportlocatieEntity.gebruiktSchools.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sportlocatieEntity.gebruiktSchools && i === sportlocatieEntity.gebruiktSchools.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Gebruikt Sportvereniging</dt>
          <dd>
            {sportlocatieEntity.gebruiktSportverenigings
              ? sportlocatieEntity.gebruiktSportverenigings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sportlocatieEntity.gebruiktSportverenigings && i === sportlocatieEntity.gebruiktSportverenigings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sportlocatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sportlocatie/${sportlocatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SportlocatieDetail;
