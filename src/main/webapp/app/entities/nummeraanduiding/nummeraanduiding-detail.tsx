import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nummeraanduiding.reducer';

export const NummeraanduidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nummeraanduidingEntity = useAppSelector(state => state.nummeraanduiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nummeraanduidingDetailsHeading">Nummeraanduiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nummeraanduidingEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {nummeraanduidingEntity.datumbegingeldigheid ? (
              <TextFormat value={nummeraanduidingEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {nummeraanduidingEntity.datumeinde ? (
              <TextFormat value={nummeraanduidingEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {nummeraanduidingEntity.datumeindegeldigheid ? (
              <TextFormat value={nummeraanduidingEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {nummeraanduidingEntity.datumingang ? (
              <TextFormat value={nummeraanduidingEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{nummeraanduidingEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{nummeraanduidingEntity.geometrie}</dd>
          <dt>
            <span id="huisletter">Huisletter</span>
          </dt>
          <dd>{nummeraanduidingEntity.huisletter}</dd>
          <dt>
            <span id="huisnummer">Huisnummer</span>
          </dt>
          <dd>{nummeraanduidingEntity.huisnummer}</dd>
          <dt>
            <span id="huisnummertoevoeging">Huisnummertoevoeging</span>
          </dt>
          <dd>{nummeraanduidingEntity.huisnummertoevoeging}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{nummeraanduidingEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{nummeraanduidingEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="postcode">Postcode</span>
          </dt>
          <dd>{nummeraanduidingEntity.postcode}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{nummeraanduidingEntity.status}</dd>
          <dt>
            <span id="typeadresseerbaarobject">Typeadresseerbaarobject</span>
          </dt>
          <dd>{nummeraanduidingEntity.typeadresseerbaarobject}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{nummeraanduidingEntity.versie}</dd>
          <dt>Ligtin Woonplaats</dt>
          <dd>{nummeraanduidingEntity.ligtinWoonplaats ? nummeraanduidingEntity.ligtinWoonplaats.id : ''}</dd>
          <dt>Ligtin Buurt</dt>
          <dd>{nummeraanduidingEntity.ligtinBuurt ? nummeraanduidingEntity.ligtinBuurt.id : ''}</dd>
          <dt>Ligtin Gebied</dt>
          <dd>
            {nummeraanduidingEntity.ligtinGebieds
              ? nummeraanduidingEntity.ligtinGebieds.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {nummeraanduidingEntity.ligtinGebieds && i === nummeraanduidingEntity.ligtinGebieds.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/nummeraanduiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nummeraanduiding/${nummeraanduidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NummeraanduidingDetail;
