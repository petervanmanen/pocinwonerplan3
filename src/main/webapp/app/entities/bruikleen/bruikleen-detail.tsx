import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bruikleen.reducer';

export const BruikleenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bruikleenEntity = useAppSelector(state => state.bruikleen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bruikleenDetailsHeading">Bruikleen</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bruikleenEntity.id}</dd>
          <dt>
            <span id="aanvraagdoor">Aanvraagdoor</span>
          </dt>
          <dd>{bruikleenEntity.aanvraagdoor}</dd>
          <dt>
            <span id="datumaanvraag">Datumaanvraag</span>
          </dt>
          <dd>
            {bruikleenEntity.datumaanvraag ? (
              <TextFormat value={bruikleenEntity.datumaanvraag} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {bruikleenEntity.datumeinde ? (
              <TextFormat value={bruikleenEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {bruikleenEntity.datumstart ? (
              <TextFormat value={bruikleenEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="toestemmingdoor">Toestemmingdoor</span>
          </dt>
          <dd>{bruikleenEntity.toestemmingdoor}</dd>
          <dt>Isbedoeldvoor Tentoonstelling</dt>
          <dd>
            {bruikleenEntity.isbedoeldvoorTentoonstellings
              ? bruikleenEntity.isbedoeldvoorTentoonstellings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bruikleenEntity.isbedoeldvoorTentoonstellings && i === bruikleenEntity.isbedoeldvoorTentoonstellings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Is Lener</dt>
          <dd>
            {bruikleenEntity.isLeners
              ? bruikleenEntity.isLeners.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bruikleenEntity.isLeners && i === bruikleenEntity.isLeners.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bruikleen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bruikleen/${bruikleenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BruikleenDetail;
