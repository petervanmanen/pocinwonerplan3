import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wijk.reducer';

export const WijkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wijkEntity = useAppSelector(state => state.wijk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wijkDetailsHeading">Wijk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wijkEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {wijkEntity.datumbegingeldigheid ? (
              <TextFormat value={wijkEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{wijkEntity.datumeinde}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {wijkEntity.datumeindegeldigheid ? (
              <TextFormat value={wijkEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {wijkEntity.datumingang ? <TextFormat value={wijkEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{wijkEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{wijkEntity.geometrie}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{wijkEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{wijkEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{wijkEntity.status}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{wijkEntity.versie}</dd>
          <dt>
            <span id="wijkcode">Wijkcode</span>
          </dt>
          <dd>{wijkEntity.wijkcode}</dd>
          <dt>
            <span id="wijknaam">Wijknaam</span>
          </dt>
          <dd>{wijkEntity.wijknaam}</dd>
          <dt>Valtbinnen Areaal</dt>
          <dd>
            {wijkEntity.valtbinnenAreaals
              ? wijkEntity.valtbinnenAreaals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {wijkEntity.valtbinnenAreaals && i === wijkEntity.valtbinnenAreaals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/wijk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wijk/${wijkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WijkDetail;
