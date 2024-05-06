import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './handelsnamenvestiging.reducer';

export const HandelsnamenvestigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const handelsnamenvestigingEntity = useAppSelector(state => state.handelsnamenvestiging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="handelsnamenvestigingDetailsHeading">Handelsnamenvestiging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{handelsnamenvestigingEntity.id}</dd>
          <dt>
            <span id="handelsnaam">Handelsnaam</span>
          </dt>
          <dd>{handelsnamenvestigingEntity.handelsnaam}</dd>
          <dt>
            <span id="verkortenaam">Verkortenaam</span>
          </dt>
          <dd>{handelsnamenvestigingEntity.verkortenaam}</dd>
          <dt>
            <span id="volgorde">Volgorde</span>
          </dt>
          <dd>{handelsnamenvestigingEntity.volgorde}</dd>
        </dl>
        <Button tag={Link} to="/handelsnamenvestiging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/handelsnamenvestiging/${handelsnamenvestigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HandelsnamenvestigingDetail;
