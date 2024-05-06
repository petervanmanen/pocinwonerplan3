import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sollicitatiegesprek.reducer';

export const SollicitatiegesprekDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sollicitatiegesprekEntity = useAppSelector(state => state.sollicitatiegesprek.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sollicitatiegesprekDetailsHeading">Sollicitatiegesprek</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sollicitatiegesprekEntity.id}</dd>
          <dt>
            <span id="aangenomen">Aangenomen</span>
          </dt>
          <dd>{sollicitatiegesprekEntity.aangenomen ? 'true' : 'false'}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {sollicitatiegesprekEntity.datum ? (
              <TextFormat value={sollicitatiegesprekEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{sollicitatiegesprekEntity.opmerkingen}</dd>
          <dt>
            <span id="volgendgesprek">Volgendgesprek</span>
          </dt>
          <dd>{sollicitatiegesprekEntity.volgendgesprek ? 'true' : 'false'}</dd>
          <dt>Inkadervan Sollicitatie</dt>
          <dd>{sollicitatiegesprekEntity.inkadervanSollicitatie ? sollicitatiegesprekEntity.inkadervanSollicitatie.id : ''}</dd>
          <dt>Kandidaat Sollicitant</dt>
          <dd>
            {sollicitatiegesprekEntity.kandidaatSollicitants
              ? sollicitatiegesprekEntity.kandidaatSollicitants.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sollicitatiegesprekEntity.kandidaatSollicitants && i === sollicitatiegesprekEntity.kandidaatSollicitants.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Doetsollicitatiegesprek Werknemer</dt>
          <dd>
            {sollicitatiegesprekEntity.doetsollicitatiegesprekWerknemers
              ? sollicitatiegesprekEntity.doetsollicitatiegesprekWerknemers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sollicitatiegesprekEntity.doetsollicitatiegesprekWerknemers &&
                    i === sollicitatiegesprekEntity.doetsollicitatiegesprekWerknemers.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sollicitatiegesprek" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sollicitatiegesprek/${sollicitatiegesprekEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SollicitatiegesprekDetail;
