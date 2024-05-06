import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subsidiebeschikking.reducer';

export const SubsidiebeschikkingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subsidiebeschikkingEntity = useAppSelector(state => state.subsidiebeschikking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subsidiebeschikkingDetailsHeading">Subsidiebeschikking</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.id}</dd>
          <dt>
            <span id="beschikkingsnummer">Beschikkingsnummer</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.beschikkingsnummer}</dd>
          <dt>
            <span id="beschiktbedrag">Beschiktbedrag</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.beschiktbedrag}</dd>
          <dt>
            <span id="besluit">Besluit</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.besluit}</dd>
          <dt>
            <span id="internkenmerk">Internkenmerk</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.internkenmerk}</dd>
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.kenmerk}</dd>
          <dt>
            <span id="ontvangen">Ontvangen</span>
          </dt>
          <dd>
            {subsidiebeschikkingEntity.ontvangen ? (
              <TextFormat value={subsidiebeschikkingEntity.ontvangen} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{subsidiebeschikkingEntity.opmerkingen}</dd>
          <dt>Betreft Subsidie</dt>
          <dd>{subsidiebeschikkingEntity.betreftSubsidie ? subsidiebeschikkingEntity.betreftSubsidie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subsidiebeschikking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subsidiebeschikking/${subsidiebeschikkingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubsidiebeschikkingDetail;
