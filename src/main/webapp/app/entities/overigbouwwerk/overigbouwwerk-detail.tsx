import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overigbouwwerk.reducer';

export const OverigbouwwerkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overigbouwwerkEntity = useAppSelector(state => state.overigbouwwerk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overigbouwwerkDetailsHeading">Overigbouwwerk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overigbouwwerkEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidoverigbouwwerk">Datumbegingeldigheidoverigbouwwerk</span>
          </dt>
          <dd>
            {overigbouwwerkEntity.datumbegingeldigheidoverigbouwwerk ? (
              <TextFormat value={overigbouwwerkEntity.datumbegingeldigheidoverigbouwwerk} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidoverigbouwwerk">Datumeindegeldigheidoverigbouwwerk</span>
          </dt>
          <dd>
            {overigbouwwerkEntity.datumeindegeldigheidoverigbouwwerk ? (
              <TextFormat value={overigbouwwerkEntity.datumeindegeldigheidoverigbouwwerk} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometrieoverigbouwwerk">Geometrieoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.geometrieoverigbouwwerk}</dd>
          <dt>
            <span id="identificatieoverigbouwwerk">Identificatieoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.identificatieoverigbouwwerk}</dd>
          <dt>
            <span id="lod0geometrieoverigbouwwerk">Lod 0 Geometrieoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.lod0geometrieoverigbouwwerk}</dd>
          <dt>
            <span id="lod1geometrieoverigbouwwerk">Lod 1 Geometrieoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.lod1geometrieoverigbouwwerk}</dd>
          <dt>
            <span id="lod2geometrieoverigbouwwerk">Lod 2 Geometrieoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.lod2geometrieoverigbouwwerk}</dd>
          <dt>
            <span id="lod3geometrieoverigbouwwerk">Lod 3 Geometrieoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.lod3geometrieoverigbouwwerk}</dd>
          <dt>
            <span id="relatievehoogteliggingoverigbouwwerk">Relatievehoogteliggingoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.relatievehoogteliggingoverigbouwwerk}</dd>
          <dt>
            <span id="statusoverigbouwwerk">Statusoverigbouwwerk</span>
          </dt>
          <dd>{overigbouwwerkEntity.statusoverigbouwwerk}</dd>
        </dl>
        <Button tag={Link} to="/overigbouwwerk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overigbouwwerk/${overigbouwwerkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverigbouwwerkDetail;
