import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './openbareruimte.reducer';

export const OpenbareruimteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const openbareruimteEntity = useAppSelector(state => state.openbareruimte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="openbareruimteDetailsHeading">Openbareruimte</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{openbareruimteEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {openbareruimteEntity.datumbegingeldigheid ? (
              <TextFormat value={openbareruimteEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {openbareruimteEntity.datumeinde ? (
              <TextFormat value={openbareruimteEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {openbareruimteEntity.datumeindegeldigheid ? (
              <TextFormat value={openbareruimteEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {openbareruimteEntity.datumingang ? (
              <TextFormat value={openbareruimteEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{openbareruimteEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{openbareruimteEntity.geometrie}</dd>
          <dt>
            <span id="huisnummerrangeevenenonevennummers">Huisnummerrangeevenenonevennummers</span>
          </dt>
          <dd>{openbareruimteEntity.huisnummerrangeevenenonevennummers}</dd>
          <dt>
            <span id="huisnummerrangeevennummers">Huisnummerrangeevennummers</span>
          </dt>
          <dd>{openbareruimteEntity.huisnummerrangeevennummers}</dd>
          <dt>
            <span id="huisnummerrangeonevennummers">Huisnummerrangeonevennummers</span>
          </dt>
          <dd>{openbareruimteEntity.huisnummerrangeonevennummers}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{openbareruimteEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{openbareruimteEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="labelnaam">Labelnaam</span>
          </dt>
          <dd>{openbareruimteEntity.labelnaam}</dd>
          <dt>
            <span id="naamopenbareruimte">Naamopenbareruimte</span>
          </dt>
          <dd>{openbareruimteEntity.naamopenbareruimte}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{openbareruimteEntity.status}</dd>
          <dt>
            <span id="straatcode">Straatcode</span>
          </dt>
          <dd>{openbareruimteEntity.straatcode}</dd>
          <dt>
            <span id="straatnaam">Straatnaam</span>
          </dt>
          <dd>{openbareruimteEntity.straatnaam}</dd>
          <dt>
            <span id="typeopenbareruimte">Typeopenbareruimte</span>
          </dt>
          <dd>{openbareruimteEntity.typeopenbareruimte}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{openbareruimteEntity.versie}</dd>
          <dt>
            <span id="wegsegment">Wegsegment</span>
          </dt>
          <dd>{openbareruimteEntity.wegsegment}</dd>
        </dl>
        <Button tag={Link} to="/openbareruimte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/openbareruimte/${openbareruimteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpenbareruimteDetail;
