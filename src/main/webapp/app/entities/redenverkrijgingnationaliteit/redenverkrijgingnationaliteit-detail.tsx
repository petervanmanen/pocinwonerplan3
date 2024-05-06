import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './redenverkrijgingnationaliteit.reducer';

export const RedenverkrijgingnationaliteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const redenverkrijgingnationaliteitEntity = useAppSelector(state => state.redenverkrijgingnationaliteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="redenverkrijgingnationaliteitDetailsHeading">Redenverkrijgingnationaliteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{redenverkrijgingnationaliteitEntity.id}</dd>
          <dt>
            <span id="datumaanvanggeldigheidverkrijging">Datumaanvanggeldigheidverkrijging</span>
          </dt>
          <dd>
            {redenverkrijgingnationaliteitEntity.datumaanvanggeldigheidverkrijging ? (
              <TextFormat
                value={redenverkrijgingnationaliteitEntity.datumaanvanggeldigheidverkrijging}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidverkrijging">Datumeindegeldigheidverkrijging</span>
          </dt>
          <dd>
            {redenverkrijgingnationaliteitEntity.datumeindegeldigheidverkrijging ? (
              <TextFormat
                value={redenverkrijgingnationaliteitEntity.datumeindegeldigheidverkrijging}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijvingverkrijging">Omschrijvingverkrijging</span>
          </dt>
          <dd>{redenverkrijgingnationaliteitEntity.omschrijvingverkrijging}</dd>
          <dt>
            <span id="redennummerverkrijging">Redennummerverkrijging</span>
          </dt>
          <dd>{redenverkrijgingnationaliteitEntity.redennummerverkrijging}</dd>
        </dl>
        <Button tag={Link} to="/redenverkrijgingnationaliteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/redenverkrijgingnationaliteit/${redenverkrijgingnationaliteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RedenverkrijgingnationaliteitDetail;
