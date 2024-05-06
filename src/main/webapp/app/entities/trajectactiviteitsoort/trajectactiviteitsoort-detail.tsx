import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trajectactiviteitsoort.reducer';

export const TrajectactiviteitsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trajectactiviteitsoortEntity = useAppSelector(state => state.trajectactiviteitsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trajectactiviteitsoortDetailsHeading">Trajectactiviteitsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trajectactiviteitsoortEntity.id}</dd>
          <dt>
            <span id="aanleverensrg">Aanleverensrg</span>
          </dt>
          <dd>{trajectactiviteitsoortEntity.aanleverensrg}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{trajectactiviteitsoortEntity.omschrijving}</dd>
          <dt>
            <span id="typetrajectsrg">Typetrajectsrg</span>
          </dt>
          <dd>{trajectactiviteitsoortEntity.typetrajectsrg}</dd>
        </dl>
        <Button tag={Link} to="/trajectactiviteitsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trajectactiviteitsoort/${trajectactiviteitsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrajectactiviteitsoortDetail;
