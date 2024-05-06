import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gunning.reducer';

export const GunningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gunningEntity = useAppSelector(state => state.gunning.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gunningDetailsHeading">Gunning</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gunningEntity.id}</dd>
          <dt>
            <span id="bericht">Bericht</span>
          </dt>
          <dd>{gunningEntity.bericht}</dd>
          <dt>
            <span id="datumgunning">Datumgunning</span>
          </dt>
          <dd>{gunningEntity.datumgunning}</dd>
          <dt>
            <span id="datumpublicatie">Datumpublicatie</span>
          </dt>
          <dd>{gunningEntity.datumpublicatie}</dd>
          <dt>
            <span id="datumvoorlopigegunning">Datumvoorlopigegunning</span>
          </dt>
          <dd>{gunningEntity.datumvoorlopigegunning}</dd>
          <dt>
            <span id="gegundeprijs">Gegundeprijs</span>
          </dt>
          <dd>{gunningEntity.gegundeprijs}</dd>
          <dt>Betreft Inschrijving</dt>
          <dd>{gunningEntity.betreftInschrijving ? gunningEntity.betreftInschrijving.id : ''}</dd>
          <dt>Betreft Kandidaat</dt>
          <dd>{gunningEntity.betreftKandidaat ? gunningEntity.betreftKandidaat.id : ''}</dd>
          <dt>Betreft Offerte</dt>
          <dd>{gunningEntity.betreftOfferte ? gunningEntity.betreftOfferte.id : ''}</dd>
          <dt>Inhuur Medewerker</dt>
          <dd>{gunningEntity.inhuurMedewerker ? gunningEntity.inhuurMedewerker.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/gunning" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gunning/${gunningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GunningDetail;
