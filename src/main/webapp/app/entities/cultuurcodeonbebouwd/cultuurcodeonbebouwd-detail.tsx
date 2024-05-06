import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cultuurcodeonbebouwd.reducer';

export const CultuurcodeonbebouwdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cultuurcodeonbebouwdEntity = useAppSelector(state => state.cultuurcodeonbebouwd.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cultuurcodeonbebouwdDetailsHeading">Cultuurcodeonbebouwd</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cultuurcodeonbebouwdEntity.id}</dd>
          <dt>
            <span id="cultuurcodeonbebouwd">Cultuurcodeonbebouwd</span>
          </dt>
          <dd>{cultuurcodeonbebouwdEntity.cultuurcodeonbebouwd}</dd>
          <dt>
            <span id="datumbegingeldigheidcultuurcodeonbebouwd">Datumbegingeldigheidcultuurcodeonbebouwd</span>
          </dt>
          <dd>
            {cultuurcodeonbebouwdEntity.datumbegingeldigheidcultuurcodeonbebouwd ? (
              <TextFormat
                value={cultuurcodeonbebouwdEntity.datumbegingeldigheidcultuurcodeonbebouwd}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidcultuurcodeonbebouwd">Datumeindegeldigheidcultuurcodeonbebouwd</span>
          </dt>
          <dd>
            {cultuurcodeonbebouwdEntity.datumeindegeldigheidcultuurcodeonbebouwd ? (
              <TextFormat
                value={cultuurcodeonbebouwdEntity.datumeindegeldigheidcultuurcodeonbebouwd}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="naamcultuurcodeonbebouwd">Naamcultuurcodeonbebouwd</span>
          </dt>
          <dd>{cultuurcodeonbebouwdEntity.naamcultuurcodeonbebouwd}</dd>
        </dl>
        <Button tag={Link} to="/cultuurcodeonbebouwd" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cultuurcodeonbebouwd/${cultuurcodeonbebouwdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CultuurcodeonbebouwdDetail;
