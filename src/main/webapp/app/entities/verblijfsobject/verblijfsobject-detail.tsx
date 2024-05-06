import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfsobject.reducer';

export const VerblijfsobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfsobjectEntity = useAppSelector(state => state.verblijfsobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfsobjectDetailsHeading">Verblijfsobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfsobjectEntity.id}</dd>
          <dt>
            <span id="aantalkamers">Aantalkamers</span>
          </dt>
          <dd>{verblijfsobjectEntity.aantalkamers}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {verblijfsobjectEntity.datumbegingeldigheid ? (
              <TextFormat value={verblijfsobjectEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {verblijfsobjectEntity.datumeinde ? (
              <TextFormat value={verblijfsobjectEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {verblijfsobjectEntity.datumeindegeldigheid ? (
              <TextFormat value={verblijfsobjectEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {verblijfsobjectEntity.datumingang ? (
              <TextFormat value={verblijfsobjectEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentdatum">Documentdatum</span>
          </dt>
          <dd>
            {verblijfsobjectEntity.documentdatum ? (
              <TextFormat value={verblijfsobjectEntity.documentdatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentnr">Documentnr</span>
          </dt>
          <dd>{verblijfsobjectEntity.documentnr}</dd>
          <dt>
            <span id="gebruiksdoel">Gebruiksdoel</span>
          </dt>
          <dd>{verblijfsobjectEntity.gebruiksdoel}</dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{verblijfsobjectEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{verblijfsobjectEntity.geometrie}</dd>
          <dt>
            <span id="hoogstebouwlaagverblijfsobject">Hoogstebouwlaagverblijfsobject</span>
          </dt>
          <dd>{verblijfsobjectEntity.hoogstebouwlaagverblijfsobject}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{verblijfsobjectEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{verblijfsobjectEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="laagstebouwlaagverblijfsobject">Laagstebouwlaagverblijfsobject</span>
          </dt>
          <dd>{verblijfsobjectEntity.laagstebouwlaagverblijfsobject}</dd>
          <dt>
            <span id="ontsluitingverdieping">Ontsluitingverdieping</span>
          </dt>
          <dd>{verblijfsobjectEntity.ontsluitingverdieping}</dd>
          <dt>
            <span id="soortwoonobject">Soortwoonobject</span>
          </dt>
          <dd>{verblijfsobjectEntity.soortwoonobject}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{verblijfsobjectEntity.status}</dd>
          <dt>
            <span id="toegangbouwlaagverblijfsobject">Toegangbouwlaagverblijfsobject</span>
          </dt>
          <dd>{verblijfsobjectEntity.toegangbouwlaagverblijfsobject}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{verblijfsobjectEntity.versie}</dd>
          <dt>Heeft Vastgoedobject</dt>
          <dd>{verblijfsobjectEntity.heeftVastgoedobject ? verblijfsobjectEntity.heeftVastgoedobject.id : ''}</dd>
          <dt>Maaktdeeluitvan Pand</dt>
          <dd>
            {verblijfsobjectEntity.maaktdeeluitvanPands
              ? verblijfsobjectEntity.maaktdeeluitvanPands.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {verblijfsobjectEntity.maaktdeeluitvanPands && i === verblijfsobjectEntity.maaktdeeluitvanPands.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/verblijfsobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verblijfsobject/${verblijfsobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfsobjectDetail;
