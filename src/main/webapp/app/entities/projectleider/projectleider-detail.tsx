import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projectleider.reducer';

export const ProjectleiderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectleiderEntity = useAppSelector(state => state.projectleider.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectleiderDetailsHeading">Projectleider</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectleiderEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{projectleiderEntity.naam}</dd>
        </dl>
        <Button tag={Link} to="/projectleider" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projectleider/${projectleiderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectleiderDetail;
