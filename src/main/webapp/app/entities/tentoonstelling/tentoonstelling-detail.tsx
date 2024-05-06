import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tentoonstelling.reducer';

export const TentoonstellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tentoonstellingEntity = useAppSelector(state => state.tentoonstelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tentoonstellingDetailsHeading">Tentoonstelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tentoonstellingEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {tentoonstellingEntity.datumeinde ? (
              <TextFormat value={tentoonstellingEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {tentoonstellingEntity.datumstart ? (
              <TextFormat value={tentoonstellingEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{tentoonstellingEntity.omschrijving}</dd>
          <dt>
            <span id="subtitel">Subtitel</span>
          </dt>
          <dd>{tentoonstellingEntity.subtitel}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{tentoonstellingEntity.titel}</dd>
          <dt>Isbedoeldvoor Bruikleen</dt>
          <dd>
            {tentoonstellingEntity.isbedoeldvoorBruikleens
              ? tentoonstellingEntity.isbedoeldvoorBruikleens.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tentoonstellingEntity.isbedoeldvoorBruikleens && i === tentoonstellingEntity.isbedoeldvoorBruikleens.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Onderdeel Museumobject</dt>
          <dd>
            {tentoonstellingEntity.onderdeelMuseumobjects
              ? tentoonstellingEntity.onderdeelMuseumobjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tentoonstellingEntity.onderdeelMuseumobjects && i === tentoonstellingEntity.onderdeelMuseumobjects.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Steltsamen Samensteller</dt>
          <dd>
            {tentoonstellingEntity.steltsamenSamenstellers
              ? tentoonstellingEntity.steltsamenSamenstellers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tentoonstellingEntity.steltsamenSamenstellers && i === tentoonstellingEntity.steltsamenSamenstellers.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tentoonstelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tentoonstelling/${tentoonstellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TentoonstellingDetail;
