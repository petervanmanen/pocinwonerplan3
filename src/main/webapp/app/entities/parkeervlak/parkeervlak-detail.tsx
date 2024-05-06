import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parkeervlak.reducer';

export const ParkeervlakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parkeervlakEntity = useAppSelector(state => state.parkeervlak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parkeervlakDetailsHeading">Parkeervlak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{parkeervlakEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{parkeervlakEntity.aantal}</dd>
          <dt>
            <span id="coordinaten">Coordinaten</span>
          </dt>
          <dd>{parkeervlakEntity.coordinaten}</dd>
          <dt>
            <span id="doelgroep">Doelgroep</span>
          </dt>
          <dd>{parkeervlakEntity.doelgroep}</dd>
          <dt>
            <span id="fiscaal">Fiscaal</span>
          </dt>
          <dd>{parkeervlakEntity.fiscaal ? 'true' : 'false'}</dd>
          <dt>
            <span id="plaats">Plaats</span>
          </dt>
          <dd>{parkeervlakEntity.plaats}</dd>
          <dt>
            <span id="vlakid">Vlakid</span>
          </dt>
          <dd>{parkeervlakEntity.vlakid}</dd>
          <dt>Bevat Straatsectie</dt>
          <dd>{parkeervlakEntity.bevatStraatsectie ? parkeervlakEntity.bevatStraatsectie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parkeervlak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parkeervlak/${parkeervlakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParkeervlakDetail;
