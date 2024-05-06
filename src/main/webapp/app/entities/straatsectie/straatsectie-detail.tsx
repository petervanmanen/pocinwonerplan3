import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './straatsectie.reducer';

export const StraatsectieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const straatsectieEntity = useAppSelector(state => state.straatsectie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="straatsectieDetailsHeading">Straatsectie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{straatsectieEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{straatsectieEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{straatsectieEntity.omschrijving}</dd>
          <dt>
            <span id="zonecode">Zonecode</span>
          </dt>
          <dd>{straatsectieEntity.zonecode}</dd>
        </dl>
        <Button tag={Link} to="/straatsectie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/straatsectie/${straatsectieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StraatsectieDetail;
