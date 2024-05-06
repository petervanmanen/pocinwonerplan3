import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './elog.reducer';

export const ElogDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const elogEntity = useAppSelector(state => state.elog.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="elogDetailsHeading">Elog</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{elogEntity.id}</dd>
          <dt>
            <span id="korteomschrijving">Korteomschrijving</span>
          </dt>
          <dd>{elogEntity.korteomschrijving}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{elogEntity.omschrijving}</dd>
          <dt>
            <span id="tijd">Tijd</span>
          </dt>
          <dd>{elogEntity.tijd}</dd>
        </dl>
        <Button tag={Link} to="/elog" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/elog/${elogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ElogDetail;
