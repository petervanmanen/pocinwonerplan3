import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gebouwinstallatie.reducer';

export const GebouwinstallatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gebouwinstallatieEntity = useAppSelector(state => state.gebouwinstallatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gebouwinstallatieDetailsHeading">Gebouwinstallatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gebouwinstallatieEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidgebouwinstallatie">Datumbegingeldigheidgebouwinstallatie</span>
          </dt>
          <dd>
            {gebouwinstallatieEntity.datumbegingeldigheidgebouwinstallatie ? (
              <TextFormat
                value={gebouwinstallatieEntity.datumbegingeldigheidgebouwinstallatie}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidgebouwinstallatie">Datumeindegeldigheidgebouwinstallatie</span>
          </dt>
          <dd>
            {gebouwinstallatieEntity.datumeindegeldigheidgebouwinstallatie ? (
              <TextFormat
                value={gebouwinstallatieEntity.datumeindegeldigheidgebouwinstallatie}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="geometriegebouwinstallatie">Geometriegebouwinstallatie</span>
          </dt>
          <dd>{gebouwinstallatieEntity.geometriegebouwinstallatie}</dd>
          <dt>
            <span id="identificatiegebouwinstallatie">Identificatiegebouwinstallatie</span>
          </dt>
          <dd>{gebouwinstallatieEntity.identificatiegebouwinstallatie}</dd>
          <dt>
            <span id="lod0geometriegebouwinstallatie">Lod 0 Geometriegebouwinstallatie</span>
          </dt>
          <dd>{gebouwinstallatieEntity.lod0geometriegebouwinstallatie}</dd>
          <dt>
            <span id="relatievehoogteligginggebouwinstallatie">Relatievehoogteligginggebouwinstallatie</span>
          </dt>
          <dd>{gebouwinstallatieEntity.relatievehoogteligginggebouwinstallatie}</dd>
          <dt>
            <span id="statusgebouwinstallatie">Statusgebouwinstallatie</span>
          </dt>
          <dd>{gebouwinstallatieEntity.statusgebouwinstallatie}</dd>
          <dt>
            <span id="typegebouwinstallatie">Typegebouwinstallatie</span>
          </dt>
          <dd>{gebouwinstallatieEntity.typegebouwinstallatie}</dd>
        </dl>
        <Button tag={Link} to="/gebouwinstallatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gebouwinstallatie/${gebouwinstallatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GebouwinstallatieDetail;
