import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fractie.reducer';

export const FractieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fractieEntity = useAppSelector(state => state.fractie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fractieDetailsHeading">Fractie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fractieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{fractieEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{fractieEntity.omschrijving}</dd>
          <dt>Inzamelpuntvan Milieustraat</dt>
          <dd>
            {fractieEntity.inzamelpuntvanMilieustraats
              ? fractieEntity.inzamelpuntvanMilieustraats.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {fractieEntity.inzamelpuntvanMilieustraats && i === fractieEntity.inzamelpuntvanMilieustraats.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Fractie Storting</dt>
          <dd>
            {fractieEntity.fractieStortings
              ? fractieEntity.fractieStortings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {fractieEntity.fractieStortings && i === fractieEntity.fractieStortings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/fractie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fractie/${fractieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FractieDetail;
