import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project.reducer';

export const ProjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectEntity = useAppSelector(state => state.project.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectDetailsHeading">Project</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectEntity.id}</dd>
          <dt>
            <span id="coordinaten">Coordinaten</span>
          </dt>
          <dd>{projectEntity.coordinaten}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {projectEntity.datumeinde ? <TextFormat value={projectEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {projectEntity.datumstart ? <TextFormat value={projectEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="jaartot">Jaartot</span>
          </dt>
          <dd>{projectEntity.jaartot}</dd>
          <dt>
            <span id="jaarvan">Jaarvan</span>
          </dt>
          <dd>{projectEntity.jaarvan}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{projectEntity.locatie}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{projectEntity.naam}</dd>
          <dt>
            <span id="naamcode">Naamcode</span>
          </dt>
          <dd>{projectEntity.naamcode}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{projectEntity.projectcd}</dd>
          <dt>
            <span id="toponiem">Toponiem</span>
          </dt>
          <dd>{projectEntity.toponiem}</dd>
          <dt>
            <span id="trefwoorden">Trefwoorden</span>
          </dt>
          <dd>{projectEntity.trefwoorden}</dd>
          <dt>Heeftuitstroomreden Uitstroomreden</dt>
          <dd>{projectEntity.heeftuitstroomredenUitstroomreden ? projectEntity.heeftuitstroomredenUitstroomreden.id : ''}</dd>
          <dt>Heeftresultaat Resultaat</dt>
          <dd>{projectEntity.heeftresultaatResultaat ? projectEntity.heeftresultaatResultaat.id : ''}</dd>
          <dt>Soortproject Projectsoort</dt>
          <dd>{projectEntity.soortprojectProjectsoort ? projectEntity.soortprojectProjectsoort.id : ''}</dd>
          <dt>Wordtbegrensddoor Locatie</dt>
          <dd>
            {projectEntity.wordtbegrensddoorLocaties
              ? projectEntity.wordtbegrensddoorLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projectEntity.wordtbegrensddoorLocaties && i === projectEntity.wordtbegrensddoorLocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>
            {projectEntity.heeftKostenplaats
              ? projectEntity.heeftKostenplaats.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projectEntity.heeftKostenplaats && i === projectEntity.heeftKostenplaats.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeftproject Traject</dt>
          <dd>{projectEntity.heeftprojectTraject ? projectEntity.heeftprojectTraject.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/project" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project/${projectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectDetail;
