import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sport.reducer';

export const SportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sportEntity = useAppSelector(state => state.sport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sportDetailsHeading">Sport</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sportEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{sportEntity.naam}</dd>
          <dt>Oefentuit Sportvereniging</dt>
          <dd>
            {sportEntity.oefentuitSportverenigings
              ? sportEntity.oefentuitSportverenigings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sportEntity.oefentuitSportverenigings && i === sportEntity.oefentuitSportverenigings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sport" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sport/${sportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SportDetail;
