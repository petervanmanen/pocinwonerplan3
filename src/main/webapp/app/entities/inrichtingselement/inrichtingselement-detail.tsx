import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inrichtingselement.reducer';

export const InrichtingselementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inrichtingselementEntity = useAppSelector(state => state.inrichtingselement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inrichtingselementDetailsHeading">Inrichtingselement</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inrichtingselementEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidinrichtingselement">Datumbegingeldigheidinrichtingselement</span>
          </dt>
          <dd>
            {inrichtingselementEntity.datumbegingeldigheidinrichtingselement ? (
              <TextFormat
                value={inrichtingselementEntity.datumbegingeldigheidinrichtingselement}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidinrichtingselement">Datumeindegeldigheidinrichtingselement</span>
          </dt>
          <dd>
            {inrichtingselementEntity.datumeindegeldigheidinrichtingselement ? (
              <TextFormat
                value={inrichtingselementEntity.datumeindegeldigheidinrichtingselement}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="geometrieinrichtingselement">Geometrieinrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.geometrieinrichtingselement}</dd>
          <dt>
            <span id="identificatieinrichtingselement">Identificatieinrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.identificatieinrichtingselement}</dd>
          <dt>
            <span id="lod0geometrieinrichtingselement">Lod 0 Geometrieinrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.lod0geometrieinrichtingselement}</dd>
          <dt>
            <span id="plustypeinrichtingselement">Plustypeinrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.plustypeinrichtingselement}</dd>
          <dt>
            <span id="relatievehoogteligginginrichtingselement">Relatievehoogteligginginrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.relatievehoogteligginginrichtingselement}</dd>
          <dt>
            <span id="statusinrichtingselement">Statusinrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.statusinrichtingselement}</dd>
          <dt>
            <span id="typeinrichtingselement">Typeinrichtingselement</span>
          </dt>
          <dd>{inrichtingselementEntity.typeinrichtingselement}</dd>
        </dl>
        <Button tag={Link} to="/inrichtingselement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inrichtingselement/${inrichtingselementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InrichtingselementDetail;
