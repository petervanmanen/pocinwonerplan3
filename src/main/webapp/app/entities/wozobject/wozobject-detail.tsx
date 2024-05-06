import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wozobject.reducer';

export const WozobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wozobjectEntity = useAppSelector(state => state.wozobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wozobjectDetailsHeading">Wozobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wozobjectEntity.id}</dd>
          <dt>
            <span id="empty">Empty</span>
          </dt>
          <dd>{wozobjectEntity.empty}</dd>
          <dt>
            <span id="datumbegingeldigheidwozobject">Datumbegingeldigheidwozobject</span>
          </dt>
          <dd>
            {wozobjectEntity.datumbegingeldigheidwozobject ? (
              <TextFormat value={wozobjectEntity.datumbegingeldigheidwozobject} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidwozobject">Datumeindegeldigheidwozobject</span>
          </dt>
          <dd>
            {wozobjectEntity.datumeindegeldigheidwozobject ? (
              <TextFormat value={wozobjectEntity.datumeindegeldigheidwozobject} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumwaardepeiling">Datumwaardepeiling</span>
          </dt>
          <dd>
            {wozobjectEntity.datumwaardepeiling ? (
              <TextFormat value={wozobjectEntity.datumwaardepeiling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gebruikscode">Gebruikscode</span>
          </dt>
          <dd>{wozobjectEntity.gebruikscode}</dd>
          <dt>
            <span id="geometriewozobject">Geometriewozobject</span>
          </dt>
          <dd>{wozobjectEntity.geometriewozobject}</dd>
          <dt>
            <span id="grondoppervlakte">Grondoppervlakte</span>
          </dt>
          <dd>{wozobjectEntity.grondoppervlakte}</dd>
          <dt>
            <span id="soortobjectcode">Soortobjectcode</span>
          </dt>
          <dd>{wozobjectEntity.soortobjectcode}</dd>
          <dt>
            <span id="statuswozobject">Statuswozobject</span>
          </dt>
          <dd>{wozobjectEntity.statuswozobject}</dd>
          <dt>
            <span id="vastgesteldewaarde">Vastgesteldewaarde</span>
          </dt>
          <dd>{wozobjectEntity.vastgesteldewaarde}</dd>
          <dt>
            <span id="wozobjectnummer">Wozobjectnummer</span>
          </dt>
          <dd>{wozobjectEntity.wozobjectnummer}</dd>
        </dl>
        <Button tag={Link} to="/wozobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wozobject/${wozobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WozobjectDetail;
