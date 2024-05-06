import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stemming.reducer';

export const StemmingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stemmingEntity = useAppSelector(state => state.stemming.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stemmingDetailsHeading">Stemming</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stemmingEntity.id}</dd>
          <dt>
            <span id="resultaat">Resultaat</span>
          </dt>
          <dd>{stemmingEntity.resultaat}</dd>
          <dt>
            <span id="stemmingstype">Stemmingstype</span>
          </dt>
          <dd>{stemmingEntity.stemmingstype}</dd>
          <dt>Betreft Raadsstuk</dt>
          <dd>{stemmingEntity.betreftRaadsstuk ? stemmingEntity.betreftRaadsstuk.id : ''}</dd>
          <dt>Hoortbij Agendapunt</dt>
          <dd>{stemmingEntity.hoortbijAgendapunt ? stemmingEntity.hoortbijAgendapunt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stemming" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stemming/${stemmingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StemmingDetail;
