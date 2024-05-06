import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './werknemer.reducer';

export const WerknemerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const werknemerEntity = useAppSelector(state => state.werknemer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="werknemerDetailsHeading">Werknemer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{werknemerEntity.id}</dd>
          <dt>
            <span id="geboortedatum">Geboortedatum</span>
          </dt>
          <dd>
            {werknemerEntity.geboortedatum ? (
              <TextFormat value={werknemerEntity.geboortedatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{werknemerEntity.naam}</dd>
          <dt>
            <span id="voornaam">Voornaam</span>
          </dt>
          <dd>{werknemerEntity.voornaam}</dd>
          <dt>
            <span id="woonplaats">Woonplaats</span>
          </dt>
          <dd>{werknemerEntity.woonplaats}</dd>
          <dt>Heeftondergaan Geweldsincident</dt>
          <dd>{werknemerEntity.heeftondergaanGeweldsincident ? werknemerEntity.heeftondergaanGeweldsincident.id : ''}</dd>
          <dt>Heeft Rol</dt>
          <dd>
            {werknemerEntity.heeftRols
              ? werknemerEntity.heeftRols.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {werknemerEntity.heeftRols && i === werknemerEntity.heeftRols.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Doetsollicitatiegesprek Sollicitatiegesprek</dt>
          <dd>
            {werknemerEntity.doetsollicitatiegesprekSollicitatiegespreks
              ? werknemerEntity.doetsollicitatiegesprekSollicitatiegespreks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {werknemerEntity.doetsollicitatiegesprekSollicitatiegespreks &&
                    i === werknemerEntity.doetsollicitatiegesprekSollicitatiegespreks.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/werknemer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/werknemer/${werknemerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WerknemerDetail;
