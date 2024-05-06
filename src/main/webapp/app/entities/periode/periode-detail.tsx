import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './periode.reducer';

export const PeriodeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const periodeEntity = useAppSelector(state => state.periode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="periodeDetailsHeading">Periode</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{periodeEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{periodeEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{periodeEntity.datumstart}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{periodeEntity.omschrijving}</dd>
          <dt>Stamtuit Archief</dt>
          <dd>
            {periodeEntity.stamtuitArchiefs
              ? periodeEntity.stamtuitArchiefs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {periodeEntity.stamtuitArchiefs && i === periodeEntity.stamtuitArchiefs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Stamtuit Archiefstuk</dt>
          <dd>
            {periodeEntity.stamtuitArchiefstuks
              ? periodeEntity.stamtuitArchiefstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {periodeEntity.stamtuitArchiefstuks && i === periodeEntity.stamtuitArchiefstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Binnen Hoofdstuk</dt>
          <dd>
            {periodeEntity.binnenHoofdstuks
              ? periodeEntity.binnenHoofdstuks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {periodeEntity.binnenHoofdstuks && i === periodeEntity.binnenHoofdstuks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/periode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/periode/${periodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PeriodeDetail;
