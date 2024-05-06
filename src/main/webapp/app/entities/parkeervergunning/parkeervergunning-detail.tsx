import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parkeervergunning.reducer';

export const ParkeervergunningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parkeervergunningEntity = useAppSelector(state => state.parkeervergunning.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parkeervergunningDetailsHeading">Parkeervergunning</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{parkeervergunningEntity.id}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>{parkeervergunningEntity.datumeindegeldigheid}</dd>
          <dt>
            <span id="datumreservering">Datumreservering</span>
          </dt>
          <dd>
            {parkeervergunningEntity.datumreservering ? (
              <TextFormat value={parkeervergunningEntity.datumreservering} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{parkeervergunningEntity.datumstart}</dd>
          <dt>
            <span id="kenteken">Kenteken</span>
          </dt>
          <dd>{parkeervergunningEntity.kenteken}</dd>
          <dt>
            <span id="minutenafgeschreven">Minutenafgeschreven</span>
          </dt>
          <dd>{parkeervergunningEntity.minutenafgeschreven}</dd>
          <dt>
            <span id="minutengeldig">Minutengeldig</span>
          </dt>
          <dd>{parkeervergunningEntity.minutengeldig}</dd>
          <dt>
            <span id="minutenresterend">Minutenresterend</span>
          </dt>
          <dd>{parkeervergunningEntity.minutenresterend}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{parkeervergunningEntity.nummer}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{parkeervergunningEntity.type}</dd>
          <dt>Resulteert Parkeerrecht</dt>
          <dd>{parkeervergunningEntity.resulteertParkeerrecht ? parkeervergunningEntity.resulteertParkeerrecht.id : ''}</dd>
          <dt>Houder Rechtspersoon</dt>
          <dd>{parkeervergunningEntity.houderRechtspersoon ? parkeervergunningEntity.houderRechtspersoon.id : ''}</dd>
          <dt>Soort Productgroep</dt>
          <dd>{parkeervergunningEntity.soortProductgroep ? parkeervergunningEntity.soortProductgroep.id : ''}</dd>
          <dt>Soort Productsoort</dt>
          <dd>{parkeervergunningEntity.soortProductsoort ? parkeervergunningEntity.soortProductsoort.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parkeervergunning" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parkeervergunning/${parkeervergunningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParkeervergunningDetail;
