import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cultuurcodebebouwd.reducer';

export const CultuurcodebebouwdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cultuurcodebebouwdEntity = useAppSelector(state => state.cultuurcodebebouwd.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cultuurcodebebouwdDetailsHeading">Cultuurcodebebouwd</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cultuurcodebebouwdEntity.id}</dd>
          <dt>
            <span id="cultuurcodebebouwd">Cultuurcodebebouwd</span>
          </dt>
          <dd>{cultuurcodebebouwdEntity.cultuurcodebebouwd}</dd>
          <dt>
            <span id="datumbegingeldigheidcultuurcodebebouwd">Datumbegingeldigheidcultuurcodebebouwd</span>
          </dt>
          <dd>
            {cultuurcodebebouwdEntity.datumbegingeldigheidcultuurcodebebouwd ? (
              <TextFormat
                value={cultuurcodebebouwdEntity.datumbegingeldigheidcultuurcodebebouwd}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidcultuurcodebebouwd">Datumeindegeldigheidcultuurcodebebouwd</span>
          </dt>
          <dd>
            {cultuurcodebebouwdEntity.datumeindegeldigheidcultuurcodebebouwd ? (
              <TextFormat
                value={cultuurcodebebouwdEntity.datumeindegeldigheidcultuurcodebebouwd}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="naamcultuurcodebebouwd">Naamcultuurcodebebouwd</span>
          </dt>
          <dd>{cultuurcodebebouwdEntity.naamcultuurcodebebouwd}</dd>
        </dl>
        <Button tag={Link} to="/cultuurcodebebouwd" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cultuurcodebebouwd/${cultuurcodebebouwdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CultuurcodebebouwdDetail;
