import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './raadslid.reducer';

export const RaadslidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const raadslidEntity = useAppSelector(state => state.raadslid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="raadslidDetailsHeading">Raadslid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{raadslidEntity.id}</dd>
          <dt>
            <span id="achternaam">Achternaam</span>
          </dt>
          <dd>{raadslidEntity.achternaam}</dd>
          <dt>
            <span id="datumaanstelling">Datumaanstelling</span>
          </dt>
          <dd>
            {raadslidEntity.datumaanstelling ? (
              <TextFormat value={raadslidEntity.datumaanstelling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumuittreding">Datumuittreding</span>
          </dt>
          <dd>
            {raadslidEntity.datumuittreding ? (
              <TextFormat value={raadslidEntity.datumuittreding} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fractie">Fractie</span>
          </dt>
          <dd>{raadslidEntity.fractie}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{raadslidEntity.titel}</dd>
          <dt>
            <span id="voornaam">Voornaam</span>
          </dt>
          <dd>{raadslidEntity.voornaam}</dd>
          <dt>Islidvan Raadscommissie</dt>
          <dd>
            {raadslidEntity.islidvanRaadscommissies
              ? raadslidEntity.islidvanRaadscommissies.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadslidEntity.islidvanRaadscommissies && i === raadslidEntity.islidvanRaadscommissies.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/raadslid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/raadslid/${raadslidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RaadslidDetail;
