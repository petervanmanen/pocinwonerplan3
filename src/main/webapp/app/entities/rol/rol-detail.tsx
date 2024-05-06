import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rol.reducer';

export const RolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rolEntity = useAppSelector(state => state.rol.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rolDetailsHeading">Rol</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rolEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{rolEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{rolEntity.omschrijving}</dd>
          <dt>Heeft Werknemer</dt>
          <dd>
            {rolEntity.heeftWerknemers
              ? rolEntity.heeftWerknemers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {rolEntity.heeftWerknemers && i === rolEntity.heeftWerknemers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/rol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rol/${rolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RolDetail;
