import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './containertype.reducer';

export const ContainertypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const containertypeEntity = useAppSelector(state => state.containertype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="containertypeDetailsHeading">Containertype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{containertypeEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{containertypeEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{containertypeEntity.omschrijving}</dd>
          <dt>Geschiktvoor Vuilniswagen</dt>
          <dd>
            {containertypeEntity.geschiktvoorVuilniswagens
              ? containertypeEntity.geschiktvoorVuilniswagens.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {containertypeEntity.geschiktvoorVuilniswagens && i === containertypeEntity.geschiktvoorVuilniswagens.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/containertype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/containertype/${containertypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContainertypeDetail;
