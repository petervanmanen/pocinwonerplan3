import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './buurt.reducer';

export const BuurtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buurtEntity = useAppSelector(state => state.buurt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buurtDetailsHeading">Buurt</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{buurtEntity.id}</dd>
          <dt>
            <span id="buurtcode">Buurtcode</span>
          </dt>
          <dd>{buurtEntity.buurtcode}</dd>
          <dt>
            <span id="buurtgeometrie">Buurtgeometrie</span>
          </dt>
          <dd>{buurtEntity.buurtgeometrie}</dd>
          <dt>
            <span id="buurtnaam">Buurtnaam</span>
          </dt>
          <dd>{buurtEntity.buurtnaam}</dd>
          <dt>
            <span id="datumbegingeldigheidbuurt">Datumbegingeldigheidbuurt</span>
          </dt>
          <dd>
            {buurtEntity.datumbegingeldigheidbuurt ? (
              <TextFormat value={buurtEntity.datumbegingeldigheidbuurt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {buurtEntity.datumeinde ? <TextFormat value={buurtEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidbuurt">Datumeindegeldigheidbuurt</span>
          </dt>
          <dd>
            {buurtEntity.datumeindegeldigheidbuurt ? (
              <TextFormat value={buurtEntity.datumeindegeldigheidbuurt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {buurtEntity.datumingang ? <TextFormat value={buurtEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{buurtEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{buurtEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{buurtEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{buurtEntity.status}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{buurtEntity.versie}</dd>
          <dt>Ligtin Areaal</dt>
          <dd>
            {buurtEntity.ligtinAreaals
              ? buurtEntity.ligtinAreaals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {buurtEntity.ligtinAreaals && i === buurtEntity.ligtinAreaals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/buurt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/buurt/${buurtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuurtDetail;
