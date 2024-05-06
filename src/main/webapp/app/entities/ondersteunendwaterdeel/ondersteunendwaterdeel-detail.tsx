import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ondersteunendwaterdeel.reducer';

export const OndersteunendwaterdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ondersteunendwaterdeelEntity = useAppSelector(state => state.ondersteunendwaterdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ondersteunendwaterdeelDetailsHeading">Ondersteunendwaterdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidondersteunendwaterdeel">Datumbegingeldigheidondersteunendwaterdeel</span>
          </dt>
          <dd>
            {ondersteunendwaterdeelEntity.datumbegingeldigheidondersteunendwaterdeel ? (
              <TextFormat
                value={ondersteunendwaterdeelEntity.datumbegingeldigheidondersteunendwaterdeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidondersteunendwaterdeel">Datumeindegeldigheidondersteunendwaterdeel</span>
          </dt>
          <dd>
            {ondersteunendwaterdeelEntity.datumeindegeldigheidondersteunendwaterdeel ? (
              <TextFormat
                value={ondersteunendwaterdeelEntity.datumeindegeldigheidondersteunendwaterdeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="geometrieondersteunendwaterdeel">Geometrieondersteunendwaterdeel</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.geometrieondersteunendwaterdeel}</dd>
          <dt>
            <span id="identificatieondersteunendwaterdeel">Identificatieondersteunendwaterdeel</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.identificatieondersteunendwaterdeel}</dd>
          <dt>
            <span id="plustypeondersteunendwaterdeel">Plustypeondersteunendwaterdeel</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.plustypeondersteunendwaterdeel}</dd>
          <dt>
            <span id="relatievehoogteliggingondersteunendwaterdeel">Relatievehoogteliggingondersteunendwaterdeel</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.relatievehoogteliggingondersteunendwaterdeel}</dd>
          <dt>
            <span id="statusondersteunendwaterdeel">Statusondersteunendwaterdeel</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.statusondersteunendwaterdeel}</dd>
          <dt>
            <span id="typeondersteunendwaterdeel">Typeondersteunendwaterdeel</span>
          </dt>
          <dd>{ondersteunendwaterdeelEntity.typeondersteunendwaterdeel}</dd>
        </dl>
        <Button tag={Link} to="/ondersteunendwaterdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ondersteunendwaterdeel/${ondersteunendwaterdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OndersteunendwaterdeelDetail;
