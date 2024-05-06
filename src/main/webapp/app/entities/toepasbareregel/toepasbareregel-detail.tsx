import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './toepasbareregel.reducer';

export const ToepasbareregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const toepasbareregelEntity = useAppSelector(state => state.toepasbareregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="toepasbareregelDetailsHeading">Toepasbareregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{toepasbareregelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {toepasbareregelEntity.datumbegingeldigheid ? (
              <TextFormat value={toepasbareregelEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {toepasbareregelEntity.datumeindegeldigheid ? (
              <TextFormat value={toepasbareregelEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="domein">Domein</span>
          </dt>
          <dd>{toepasbareregelEntity.domein}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{toepasbareregelEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{toepasbareregelEntity.omschrijving}</dd>
          <dt>
            <span id="soortaansluitpunt">Soortaansluitpunt</span>
          </dt>
          <dd>{toepasbareregelEntity.soortaansluitpunt}</dd>
          <dt>
            <span id="toestemming">Toestemming</span>
          </dt>
          <dd>{toepasbareregelEntity.toestemming}</dd>
        </dl>
        <Button tag={Link} to="/toepasbareregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/toepasbareregel/${toepasbareregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ToepasbareregelDetail;
