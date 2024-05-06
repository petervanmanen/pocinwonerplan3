import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './museumobject.reducer';

export const MuseumobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const museumobjectEntity = useAppSelector(state => state.museumobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="museumobjectDetailsHeading">Museumobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{museumobjectEntity.id}</dd>
          <dt>
            <span id="afmeting">Afmeting</span>
          </dt>
          <dd>{museumobjectEntity.afmeting}</dd>
          <dt>
            <span id="bezittot">Bezittot</span>
          </dt>
          <dd>
            {museumobjectEntity.bezittot ? (
              <TextFormat value={museumobjectEntity.bezittot} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bezitvanaf">Bezitvanaf</span>
          </dt>
          <dd>
            {museumobjectEntity.bezitvanaf ? (
              <TextFormat value={museumobjectEntity.bezitvanaf} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="medium">Medium</span>
          </dt>
          <dd>{museumobjectEntity.medium}</dd>
          <dt>
            <span id="verkrijging">Verkrijging</span>
          </dt>
          <dd>{museumobjectEntity.verkrijging}</dd>
          <dt>Betreft Bruikleen</dt>
          <dd>{museumobjectEntity.betreftBruikleen ? museumobjectEntity.betreftBruikleen.id : ''}</dd>
          <dt>Locatie Standplaats</dt>
          <dd>{museumobjectEntity.locatieStandplaats ? museumobjectEntity.locatieStandplaats.id : ''}</dd>
          <dt>Heeft Belanghebbende</dt>
          <dd>
            {museumobjectEntity.heeftBelanghebbendes
              ? museumobjectEntity.heeftBelanghebbendes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {museumobjectEntity.heeftBelanghebbendes && i === museumobjectEntity.heeftBelanghebbendes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Onderdeel Tentoonstelling</dt>
          <dd>
            {museumobjectEntity.onderdeelTentoonstellings
              ? museumobjectEntity.onderdeelTentoonstellings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {museumobjectEntity.onderdeelTentoonstellings && i === museumobjectEntity.onderdeelTentoonstellings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Bevat Collectie</dt>
          <dd>
            {museumobjectEntity.bevatCollecties
              ? museumobjectEntity.bevatCollecties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {museumobjectEntity.bevatCollecties && i === museumobjectEntity.bevatCollecties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Betreft Incident</dt>
          <dd>
            {museumobjectEntity.betreftIncidents
              ? museumobjectEntity.betreftIncidents.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {museumobjectEntity.betreftIncidents && i === museumobjectEntity.betreftIncidents.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/museumobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/museumobject/${museumobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MuseumobjectDetail;
