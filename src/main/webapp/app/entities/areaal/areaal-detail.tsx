import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './areaal.reducer';

export const AreaalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const areaalEntity = useAppSelector(state => state.areaal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="areaalDetailsHeading">Areaal</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{areaalEntity.id}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{areaalEntity.geometrie}</dd>
          <dt>Ligtin Buurt</dt>
          <dd>
            {areaalEntity.ligtinBuurts
              ? areaalEntity.ligtinBuurts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {areaalEntity.ligtinBuurts && i === areaalEntity.ligtinBuurts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Valtbinnen Wijk</dt>
          <dd>
            {areaalEntity.valtbinnenWijks
              ? areaalEntity.valtbinnenWijks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {areaalEntity.valtbinnenWijks && i === areaalEntity.valtbinnenWijks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Binnen Schouwronde</dt>
          <dd>
            {areaalEntity.binnenSchouwrondes
              ? areaalEntity.binnenSchouwrondes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {areaalEntity.binnenSchouwrondes && i === areaalEntity.binnenSchouwrondes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/areaal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/areaal/${areaalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AreaalDetail;
