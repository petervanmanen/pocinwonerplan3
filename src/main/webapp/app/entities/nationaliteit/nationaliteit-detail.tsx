import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nationaliteit.reducer';

export const NationaliteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nationaliteitEntity = useAppSelector(state => state.nationaliteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nationaliteitDetailsHeading">Nationaliteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nationaliteitEntity.id}</dd>
          <dt>
            <span id="buitenlandsenationaliteit">Buitenlandsenationaliteit</span>
          </dt>
          <dd>{nationaliteitEntity.buitenlandsenationaliteit ? 'true' : 'false'}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {nationaliteitEntity.datumeindegeldigheid ? (
              <TextFormat value={nationaliteitEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datuminganggeldigheid">Datuminganggeldigheid</span>
          </dt>
          <dd>
            {nationaliteitEntity.datuminganggeldigheid ? (
              <TextFormat value={nationaliteitEntity.datuminganggeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumopnamen">Datumopnamen</span>
          </dt>
          <dd>{nationaliteitEntity.datumopnamen}</dd>
          <dt>
            <span id="datumverliesnationaliteit">Datumverliesnationaliteit</span>
          </dt>
          <dd>
            {nationaliteitEntity.datumverliesnationaliteit ? (
              <TextFormat value={nationaliteitEntity.datumverliesnationaliteit} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="nationaliteit">Nationaliteit</span>
          </dt>
          <dd>{nationaliteitEntity.nationaliteit}</dd>
          <dt>
            <span id="nationaliteitcode">Nationaliteitcode</span>
          </dt>
          <dd>{nationaliteitEntity.nationaliteitcode}</dd>
          <dt>
            <span id="redenverkrijgingnederlandsenationaliteit">Redenverkrijgingnederlandsenationaliteit</span>
          </dt>
          <dd>{nationaliteitEntity.redenverkrijgingnederlandsenationaliteit}</dd>
          <dt>
            <span id="redenverliesnederlandsenationaliteit">Redenverliesnederlandsenationaliteit</span>
          </dt>
          <dd>{nationaliteitEntity.redenverliesnederlandsenationaliteit}</dd>
        </dl>
        <Button tag={Link} to="/nationaliteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nationaliteit/${nationaliteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NationaliteitDetail;
