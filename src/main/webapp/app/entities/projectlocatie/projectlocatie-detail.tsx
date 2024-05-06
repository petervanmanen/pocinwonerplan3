import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projectlocatie.reducer';

export const ProjectlocatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectlocatieEntity = useAppSelector(state => state.projectlocatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectlocatieDetailsHeading">Projectlocatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectlocatieEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{projectlocatieEntity.adres}</dd>
          <dt>
            <span id="kadastraalperceel">Kadastraalperceel</span>
          </dt>
          <dd>{projectlocatieEntity.kadastraalperceel}</dd>
          <dt>
            <span id="kadastralegemeente">Kadastralegemeente</span>
          </dt>
          <dd>{projectlocatieEntity.kadastralegemeente}</dd>
          <dt>
            <span id="kadastralesectie">Kadastralesectie</span>
          </dt>
          <dd>{projectlocatieEntity.kadastralesectie}</dd>
          <dt>Betreft Locatie</dt>
          <dd>{projectlocatieEntity.betreftLocatie ? projectlocatieEntity.betreftLocatie.id : ''}</dd>
          <dt>Heeft Project</dt>
          <dd>{projectlocatieEntity.heeftProject ? projectlocatieEntity.heeftProject.id : ''}</dd>
          <dt>Betreft Verzoek</dt>
          <dd>
            {projectlocatieEntity.betreftVerzoeks
              ? projectlocatieEntity.betreftVerzoeks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projectlocatieEntity.betreftVerzoeks && i === projectlocatieEntity.betreftVerzoeks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/projectlocatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projectlocatie/${projectlocatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectlocatieDetail;
