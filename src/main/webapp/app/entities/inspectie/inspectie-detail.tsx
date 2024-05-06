import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspectie.reducer';

export const InspectieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectieEntity = useAppSelector(state => state.inspectie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectieDetailsHeading">Inspectie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inspectieEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{inspectieEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="datumaanmaak">Datumaanmaak</span>
          </dt>
          <dd>
            {inspectieEntity.datumaanmaak ? (
              <TextFormat value={inspectieEntity.datumaanmaak} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumgepland">Datumgepland</span>
          </dt>
          <dd>
            {inspectieEntity.datumgepland ? (
              <TextFormat value={inspectieEntity.datumgepland} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datuminspectie">Datuminspectie</span>
          </dt>
          <dd>
            {inspectieEntity.datuminspectie ? (
              <TextFormat value={inspectieEntity.datuminspectie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {inspectieEntity.datummutatie ? (
              <TextFormat value={inspectieEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gemuteerddoor">Gemuteerddoor</span>
          </dt>
          <dd>{inspectieEntity.gemuteerddoor}</dd>
          <dt>
            <span id="inspectietype">Inspectietype</span>
          </dt>
          <dd>{inspectieEntity.inspectietype}</dd>
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{inspectieEntity.kenmerk}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{inspectieEntity.omschrijving}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{inspectieEntity.opmerkingen}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{inspectieEntity.status}</dd>
          <dt>Betreft Vastgoedobject</dt>
          <dd>{inspectieEntity.betreftVastgoedobject ? inspectieEntity.betreftVastgoedobject.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspectie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspectie/${inspectieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectieDetail;
