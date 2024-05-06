import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pand.reducer';

export const PandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pandEntity = useAppSelector(state => state.pand.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pandDetailsHeading">Pand</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pandEntity.id}</dd>
          <dt>
            <span id="brutoinhoudpand">Brutoinhoudpand</span>
          </dt>
          <dd>{pandEntity.brutoinhoudpand}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {pandEntity.datumbegingeldigheid ? (
              <TextFormat value={pandEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{pandEntity.datumeinde ? <TextFormat value={pandEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {pandEntity.datumeindegeldigheid ? (
              <TextFormat value={pandEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {pandEntity.datumingang ? <TextFormat value={pandEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{pandEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="geometriebovenaanzicht">Geometriebovenaanzicht</span>
          </dt>
          <dd>{pandEntity.geometriebovenaanzicht}</dd>
          <dt>
            <span id="geometriemaaiveld">Geometriemaaiveld</span>
          </dt>
          <dd>{pandEntity.geometriemaaiveld}</dd>
          <dt>
            <span id="geometriepunt">Geometriepunt</span>
          </dt>
          <dd>{pandEntity.geometriepunt}</dd>
          <dt>
            <span id="hoogstebouwlaagpand">Hoogstebouwlaagpand</span>
          </dt>
          <dd>{pandEntity.hoogstebouwlaagpand}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{pandEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{pandEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="laagstebouwlaagpand">Laagstebouwlaagpand</span>
          </dt>
          <dd>{pandEntity.laagstebouwlaagpand}</dd>
          <dt>
            <span id="oorspronkelijkbouwjaar">Oorspronkelijkbouwjaar</span>
          </dt>
          <dd>{pandEntity.oorspronkelijkbouwjaar}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{pandEntity.oppervlakte}</dd>
          <dt>
            <span id="relatievehoogteliggingpand">Relatievehoogteliggingpand</span>
          </dt>
          <dd>{pandEntity.relatievehoogteliggingpand}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{pandEntity.status}</dd>
          <dt>
            <span id="statusvoortgangbouw">Statusvoortgangbouw</span>
          </dt>
          <dd>{pandEntity.statusvoortgangbouw}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{pandEntity.versie}</dd>
          <dt>Heeft Vastgoedobject</dt>
          <dd>{pandEntity.heeftVastgoedobject ? pandEntity.heeftVastgoedobject.id : ''}</dd>
          <dt>Zonderverblijfsobjectligtin Buurt</dt>
          <dd>{pandEntity.zonderverblijfsobjectligtinBuurt ? pandEntity.zonderverblijfsobjectligtinBuurt.id : ''}</dd>
          <dt>Maaktdeeluitvan Verblijfsobject</dt>
          <dd>
            {pandEntity.maaktdeeluitvanVerblijfsobjects
              ? pandEntity.maaktdeeluitvanVerblijfsobjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {pandEntity.maaktdeeluitvanVerblijfsobjects && i === pandEntity.maaktdeeluitvanVerblijfsobjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pand/${pandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PandDetail;
