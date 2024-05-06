import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './parkeerzone.reducer';

export const ParkeerzoneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const parkeerzoneEntity = useAppSelector(state => state.parkeerzone.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="parkeerzoneDetailsHeading">Parkeerzone</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{parkeerzoneEntity.id}</dd>
          <dt>
            <span id="aantalparkeervlakken">Aantalparkeervlakken</span>
          </dt>
          <dd>{parkeerzoneEntity.aantalparkeervlakken}</dd>
          <dt>
            <span id="alleendagtarief">Alleendagtarief</span>
          </dt>
          <dd>{parkeerzoneEntity.alleendagtarief ? 'true' : 'false'}</dd>
          <dt>
            <span id="dagtarief">Dagtarief</span>
          </dt>
          <dd>{parkeerzoneEntity.dagtarief}</dd>
          <dt>
            <span id="eindedag">Eindedag</span>
          </dt>
          <dd>{parkeerzoneEntity.eindedag}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{parkeerzoneEntity.eindtijd}</dd>
          <dt>
            <span id="gebruik">Gebruik</span>
          </dt>
          <dd>{parkeerzoneEntity.gebruik}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{parkeerzoneEntity.geometrie}</dd>
          <dt>
            <span id="ipmcode">Ipmcode</span>
          </dt>
          <dd>{parkeerzoneEntity.ipmcode}</dd>
          <dt>
            <span id="ipmnaam">Ipmnaam</span>
          </dt>
          <dd>{parkeerzoneEntity.ipmnaam}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{parkeerzoneEntity.naam}</dd>
          <dt>
            <span id="parkeergarage">Parkeergarage</span>
          </dt>
          <dd>{parkeerzoneEntity.parkeergarage ? 'true' : 'false'}</dd>
          <dt>
            <span id="sectorcode">Sectorcode</span>
          </dt>
          <dd>{parkeerzoneEntity.sectorcode}</dd>
          <dt>
            <span id="soortcode">Soortcode</span>
          </dt>
          <dd>{parkeerzoneEntity.soortcode}</dd>
          <dt>
            <span id="startdag">Startdag</span>
          </dt>
          <dd>{parkeerzoneEntity.startdag}</dd>
          <dt>
            <span id="starttarief">Starttarief</span>
          </dt>
          <dd>{parkeerzoneEntity.starttarief}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{parkeerzoneEntity.starttijd}</dd>
          <dt>
            <span id="typecode">Typecode</span>
          </dt>
          <dd>{parkeerzoneEntity.typecode}</dd>
          <dt>
            <span id="typenaam">Typenaam</span>
          </dt>
          <dd>{parkeerzoneEntity.typenaam}</dd>
          <dt>
            <span id="uurtarief">Uurtarief</span>
          </dt>
          <dd>{parkeerzoneEntity.uurtarief}</dd>
        </dl>
        <Button tag={Link} to="/parkeerzone" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parkeerzone/${parkeerzoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParkeerzoneDetail;
