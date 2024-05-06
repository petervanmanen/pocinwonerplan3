import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kunstwerkdeel.reducer';

export const KunstwerkdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kunstwerkdeelEntity = useAppSelector(state => state.kunstwerkdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kunstwerkdeelDetailsHeading">Kunstwerkdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kunstwerkdeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidkunstwerkdeel">Datumbegingeldigheidkunstwerkdeel</span>
          </dt>
          <dd>
            {kunstwerkdeelEntity.datumbegingeldigheidkunstwerkdeel ? (
              <TextFormat value={kunstwerkdeelEntity.datumbegingeldigheidkunstwerkdeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidkunstwerkdeel">Datumeindegeldigheidkunstwerkdeel</span>
          </dt>
          <dd>
            {kunstwerkdeelEntity.datumeindegeldigheidkunstwerkdeel ? (
              <TextFormat value={kunstwerkdeelEntity.datumeindegeldigheidkunstwerkdeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometriekunstwerkdeel">Geometriekunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.geometriekunstwerkdeel}</dd>
          <dt>
            <span id="identificatiekunstwerkdeel">Identificatiekunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.identificatiekunstwerkdeel}</dd>
          <dt>
            <span id="lod0geometriekunstwerkdeel">Lod 0 Geometriekunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.lod0geometriekunstwerkdeel}</dd>
          <dt>
            <span id="lod1geometriekunstwerkdeel">Lod 1 Geometriekunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.lod1geometriekunstwerkdeel}</dd>
          <dt>
            <span id="lod2geometriekunstwerkdeel">Lod 2 Geometriekunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.lod2geometriekunstwerkdeel}</dd>
          <dt>
            <span id="lod3geometriekunstwerkdeel">Lod 3 Geometriekunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.lod3geometriekunstwerkdeel}</dd>
          <dt>
            <span id="relatievehoogteliggingkunstwerkdeel">Relatievehoogteliggingkunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.relatievehoogteliggingkunstwerkdeel}</dd>
          <dt>
            <span id="statuskunstwerkdeel">Statuskunstwerkdeel</span>
          </dt>
          <dd>{kunstwerkdeelEntity.statuskunstwerkdeel}</dd>
        </dl>
        <Button tag={Link} to="/kunstwerkdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kunstwerkdeel/${kunstwerkdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KunstwerkdeelDetail;
