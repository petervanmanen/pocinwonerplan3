import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './genotenopleiding.reducer';

export const GenotenopleidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const genotenopleidingEntity = useAppSelector(state => state.genotenopleiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="genotenopleidingDetailsHeading">Genotenopleiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{genotenopleidingEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {genotenopleidingEntity.datumeinde ? (
              <TextFormat value={genotenopleidingEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {genotenopleidingEntity.datumstart ? (
              <TextFormat value={genotenopleidingEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtoewijzing">Datumtoewijzing</span>
          </dt>
          <dd>
            {genotenopleidingEntity.datumtoewijzing ? (
              <TextFormat value={genotenopleidingEntity.datumtoewijzing} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="prijs">Prijs</span>
          </dt>
          <dd>{genotenopleidingEntity.prijs}</dd>
          <dt>
            <span id="verrekenen">Verrekenen</span>
          </dt>
          <dd>{genotenopleidingEntity.verrekenen}</dd>
        </dl>
        <Button tag={Link} to="/genotenopleiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/genotenopleiding/${genotenopleidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GenotenopleidingDetail;
