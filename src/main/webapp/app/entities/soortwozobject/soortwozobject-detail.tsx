import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soortwozobject.reducer';

export const SoortwozobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soortwozobjectEntity = useAppSelector(state => state.soortwozobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soortwozobjectDetailsHeading">Soortwozobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{soortwozobjectEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidsoortobjectcode">Datumbegingeldigheidsoortobjectcode</span>
          </dt>
          <dd>
            {soortwozobjectEntity.datumbegingeldigheidsoortobjectcode ? (
              <TextFormat value={soortwozobjectEntity.datumbegingeldigheidsoortobjectcode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidsoortobjectcode">Datumeindegeldigheidsoortobjectcode</span>
          </dt>
          <dd>
            {soortwozobjectEntity.datumeindegeldigheidsoortobjectcode ? (
              <TextFormat value={soortwozobjectEntity.datumeindegeldigheidsoortobjectcode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naamsoortobjectcode">Naamsoortobjectcode</span>
          </dt>
          <dd>{soortwozobjectEntity.naamsoortobjectcode}</dd>
          <dt>
            <span id="opmerkingensoortobjectcode">Opmerkingensoortobjectcode</span>
          </dt>
          <dd>{soortwozobjectEntity.opmerkingensoortobjectcode}</dd>
          <dt>
            <span id="soortobjectcode">Soortobjectcode</span>
          </dt>
          <dd>{soortwozobjectEntity.soortobjectcode}</dd>
        </dl>
        <Button tag={Link} to="/soortwozobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soortwozobject/${soortwozobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoortwozobjectDetail;
