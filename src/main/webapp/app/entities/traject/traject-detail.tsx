import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './traject.reducer';

export const TrajectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trajectEntity = useAppSelector(state => state.traject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trajectDetailsHeading">Traject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trajectEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{trajectEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{trajectEntity.datumstart}</dd>
          <dt>
            <span id="datumtoekenning">Datumtoekenning</span>
          </dt>
          <dd>{trajectEntity.datumtoekenning}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{trajectEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{trajectEntity.omschrijving}</dd>
          <dt>
            <span id="resultaat">Resultaat</span>
          </dt>
          <dd>{trajectEntity.resultaat}</dd>
          <dt>Heeftresultaat Resultaat</dt>
          <dd>{trajectEntity.heeftresultaatResultaat ? trajectEntity.heeftresultaatResultaat.id : ''}</dd>
          <dt>Heeftuitstroomreden Uitstroomreden</dt>
          <dd>{trajectEntity.heeftuitstroomredenUitstroomreden ? trajectEntity.heeftuitstroomredenUitstroomreden.id : ''}</dd>
          <dt>Istrajectsoort Trajectsoort</dt>
          <dd>{trajectEntity.istrajectsoortTrajectsoort ? trajectEntity.istrajectsoortTrajectsoort.id : ''}</dd>
          <dt>Heeftparticipatietraject Client</dt>
          <dd>{trajectEntity.heeftparticipatietrajectClient ? trajectEntity.heeftparticipatietrajectClient.id : ''}</dd>
          <dt>Heefttraject Client</dt>
          <dd>{trajectEntity.heefttrajectClient ? trajectEntity.heefttrajectClient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/traject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/traject/${trajectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrajectDetail;
