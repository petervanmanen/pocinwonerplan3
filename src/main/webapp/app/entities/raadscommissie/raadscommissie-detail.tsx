import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './raadscommissie.reducer';

export const RaadscommissieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const raadscommissieEntity = useAppSelector(state => state.raadscommissie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="raadscommissieDetailsHeading">Raadscommissie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{raadscommissieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{raadscommissieEntity.naam}</dd>
          <dt>Islidvan Raadslid</dt>
          <dd>
            {raadscommissieEntity.islidvanRaadslids
              ? raadscommissieEntity.islidvanRaadslids.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadscommissieEntity.islidvanRaadslids && i === raadscommissieEntity.islidvanRaadslids.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/raadscommissie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/raadscommissie/${raadscommissieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RaadscommissieDetail;
