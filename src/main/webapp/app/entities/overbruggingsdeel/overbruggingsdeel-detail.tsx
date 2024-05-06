import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overbruggingsdeel.reducer';

export const OverbruggingsdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overbruggingsdeelEntity = useAppSelector(state => state.overbruggingsdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overbruggingsdeelDetailsHeading">Overbruggingsdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overbruggingsdeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidoverbruggingsdeel">Datumbegingeldigheidoverbruggingsdeel</span>
          </dt>
          <dd>
            {overbruggingsdeelEntity.datumbegingeldigheidoverbruggingsdeel ? (
              <TextFormat
                value={overbruggingsdeelEntity.datumbegingeldigheidoverbruggingsdeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidoverbruggingsdeel">Datumeindegeldigheidoverbruggingsdeel</span>
          </dt>
          <dd>
            {overbruggingsdeelEntity.datumeindegeldigheidoverbruggingsdeel ? (
              <TextFormat
                value={overbruggingsdeelEntity.datumeindegeldigheidoverbruggingsdeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="geometrieoverbruggingsdeel">Geometrieoverbruggingsdeel</span>
          </dt>
          <dd>{overbruggingsdeelEntity.geometrieoverbruggingsdeel}</dd>
          <dt>
            <span id="hoortbijtypeoverbrugging">Hoortbijtypeoverbrugging</span>
          </dt>
          <dd>{overbruggingsdeelEntity.hoortbijtypeoverbrugging}</dd>
          <dt>
            <span id="identificatieoverbruggingsdeel">Identificatieoverbruggingsdeel</span>
          </dt>
          <dd>{overbruggingsdeelEntity.identificatieoverbruggingsdeel}</dd>
          <dt>
            <span id="lod0geometrieoverbruggingsdeel">Lod 0 Geometrieoverbruggingsdeel</span>
          </dt>
          <dd>{overbruggingsdeelEntity.lod0geometrieoverbruggingsdeel}</dd>
          <dt>
            <span id="overbruggingisbeweegbaar">Overbruggingisbeweegbaar</span>
          </dt>
          <dd>{overbruggingsdeelEntity.overbruggingisbeweegbaar}</dd>
          <dt>
            <span id="relatievehoogteliggingoverbruggingsdeel">Relatievehoogteliggingoverbruggingsdeel</span>
          </dt>
          <dd>{overbruggingsdeelEntity.relatievehoogteliggingoverbruggingsdeel}</dd>
          <dt>
            <span id="statusoverbruggingsdeel">Statusoverbruggingsdeel</span>
          </dt>
          <dd>{overbruggingsdeelEntity.statusoverbruggingsdeel}</dd>
          <dt>
            <span id="typeoverbruggingsdeel">Typeoverbruggingsdeel</span>
          </dt>
          <dd>{overbruggingsdeelEntity.typeoverbruggingsdeel}</dd>
        </dl>
        <Button tag={Link} to="/overbruggingsdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overbruggingsdeel/${overbruggingsdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverbruggingsdeelDetail;
