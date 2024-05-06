import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './epackage.reducer';

export const EpackageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const epackageEntity = useAppSelector(state => state.epackage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="epackageDetailsHeading">Epackage</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{epackageEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{epackageEntity.naam}</dd>
          <dt>
            <span id="proces">Proces</span>
          </dt>
          <dd>{epackageEntity.proces}</dd>
          <dt>
            <span id="project">Project</span>
          </dt>
          <dd>{epackageEntity.project}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{epackageEntity.status}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{epackageEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/epackage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/epackage/${epackageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EpackageDetail;
