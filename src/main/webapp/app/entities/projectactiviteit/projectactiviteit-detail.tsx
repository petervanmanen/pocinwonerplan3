import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projectactiviteit.reducer';

export const ProjectactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectactiviteitEntity = useAppSelector(state => state.projectactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectactiviteitDetailsHeading">Projectactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectactiviteitEntity.id}</dd>
          <dt>Heeft Project</dt>
          <dd>{projectactiviteitEntity.heeftProject ? projectactiviteitEntity.heeftProject.id : ''}</dd>
          <dt>Betreft Verzoek</dt>
          <dd>
            {projectactiviteitEntity.betreftVerzoeks
              ? projectactiviteitEntity.betreftVerzoeks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projectactiviteitEntity.betreftVerzoeks && i === projectactiviteitEntity.betreftVerzoeks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/projectactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projectactiviteit/${projectactiviteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectactiviteitDetail;
