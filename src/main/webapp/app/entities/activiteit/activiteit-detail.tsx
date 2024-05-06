import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activiteit.reducer';

export const ActiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activiteitEntity = useAppSelector(state => state.activiteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activiteitDetailsHeading">Activiteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activiteitEntity.id}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{activiteitEntity.omschrijving}</dd>
          <dt>Gerelateerdeactiviteit Activiteit</dt>
          <dd>{activiteitEntity.gerelateerdeactiviteitActiviteit ? activiteitEntity.gerelateerdeactiviteitActiviteit.id : ''}</dd>
          <dt>Bovenliggendeactiviteit Activiteit</dt>
          <dd>{activiteitEntity.bovenliggendeactiviteitActiviteit ? activiteitEntity.bovenliggendeactiviteitActiviteit.id : ''}</dd>
          <dt>Heeft Rondleiding</dt>
          <dd>{activiteitEntity.heeftRondleiding ? activiteitEntity.heeftRondleiding.id : ''}</dd>
          <dt>Vansoort Activiteitsoort</dt>
          <dd>{activiteitEntity.vansoortActiviteitsoort ? activiteitEntity.vansoortActiviteitsoort.id : ''}</dd>
          <dt>Isverbondenmet Locatie</dt>
          <dd>
            {activiteitEntity.isverbondenmetLocaties
              ? activiteitEntity.isverbondenmetLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {activiteitEntity.isverbondenmetLocaties && i === activiteitEntity.isverbondenmetLocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Bestaatuit Activiteit 2</dt>
          <dd>{activiteitEntity.bestaatuitActiviteit2 ? activiteitEntity.bestaatuitActiviteit2.id : ''}</dd>
          <dt>Bestaatuit Programma</dt>
          <dd>{activiteitEntity.bestaatuitProgramma ? activiteitEntity.bestaatuitProgramma.id : ''}</dd>
          <dt>Betreft Verzoek</dt>
          <dd>
            {activiteitEntity.betreftVerzoeks
              ? activiteitEntity.betreftVerzoeks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {activiteitEntity.betreftVerzoeks && i === activiteitEntity.betreftVerzoeks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/activiteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activiteit/${activiteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActiviteitDetail;
