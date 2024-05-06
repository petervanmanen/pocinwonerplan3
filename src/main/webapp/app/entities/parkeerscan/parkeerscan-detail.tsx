import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parkeerscan.reducer';

export const ParkeerscanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parkeerscanEntity = useAppSelector(state => state.parkeerscan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parkeerscanDetailsHeading">Parkeerscan</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{parkeerscanEntity.id}</dd>
          <dt>
            <span id="codegebruiker">Codegebruiker</span>
          </dt>
          <dd>{parkeerscanEntity.codegebruiker}</dd>
          <dt>
            <span id="codescanvoertuig">Codescanvoertuig</span>
          </dt>
          <dd>{parkeerscanEntity.codescanvoertuig}</dd>
          <dt>
            <span id="coordinaten">Coordinaten</span>
          </dt>
          <dd>{parkeerscanEntity.coordinaten}</dd>
          <dt>
            <span id="foto">Foto</span>
          </dt>
          <dd>{parkeerscanEntity.foto}</dd>
          <dt>
            <span id="kenteken">Kenteken</span>
          </dt>
          <dd>{parkeerscanEntity.kenteken}</dd>
          <dt>
            <span id="parkeerrecht">Parkeerrecht</span>
          </dt>
          <dd>{parkeerscanEntity.parkeerrecht ? 'true' : 'false'}</dd>
          <dt>
            <span id="tijdstip">Tijdstip</span>
          </dt>
          <dd>{parkeerscanEntity.tijdstip}</dd>
          <dt>
            <span id="transactieid">Transactieid</span>
          </dt>
          <dd>{parkeerscanEntity.transactieid}</dd>
          <dt>Komtvoortuit Naheffing</dt>
          <dd>{parkeerscanEntity.komtvoortuitNaheffing ? parkeerscanEntity.komtvoortuitNaheffing.id : ''}</dd>
          <dt>Uitgevoerddoor Medewerker</dt>
          <dd>{parkeerscanEntity.uitgevoerddoorMedewerker ? parkeerscanEntity.uitgevoerddoorMedewerker.id : ''}</dd>
          <dt>Betreft Voertuig</dt>
          <dd>{parkeerscanEntity.betreftVoertuig ? parkeerscanEntity.betreftVoertuig.id : ''}</dd>
          <dt>Betreft Parkeervlak</dt>
          <dd>{parkeerscanEntity.betreftParkeervlak ? parkeerscanEntity.betreftParkeervlak.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parkeerscan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parkeerscan/${parkeerscanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParkeerscanDetail;
