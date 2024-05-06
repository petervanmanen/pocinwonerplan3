import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './briefadres.reducer';

export const BriefadresDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const briefadresEntity = useAppSelector(state => state.briefadres.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="briefadresDetailsHeading">Briefadres</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{briefadresEntity.id}</dd>
          <dt>
            <span id="adresfunctie">Adresfunctie</span>
          </dt>
          <dd>{briefadresEntity.adresfunctie}</dd>
          <dt>
            <span id="datumaanvang">Datumaanvang</span>
          </dt>
          <dd>
            {briefadresEntity.datumaanvang ? (
              <TextFormat value={briefadresEntity.datumaanvang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {briefadresEntity.datumeinde ? (
              <TextFormat value={briefadresEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijvingaangifte">Omschrijvingaangifte</span>
          </dt>
          <dd>{briefadresEntity.omschrijvingaangifte}</dd>
          <dt>Empty Nummeraanduiding</dt>
          <dd>{briefadresEntity.emptyNummeraanduiding ? briefadresEntity.emptyNummeraanduiding.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/briefadres" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/briefadres/${briefadresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BriefadresDetail;
