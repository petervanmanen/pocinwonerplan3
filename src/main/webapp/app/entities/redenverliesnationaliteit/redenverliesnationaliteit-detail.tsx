import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './redenverliesnationaliteit.reducer';

export const RedenverliesnationaliteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const redenverliesnationaliteitEntity = useAppSelector(state => state.redenverliesnationaliteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="redenverliesnationaliteitDetailsHeading">Redenverliesnationaliteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{redenverliesnationaliteitEntity.id}</dd>
          <dt>
            <span id="datumaanvanggeldigheidverlies">Datumaanvanggeldigheidverlies</span>
          </dt>
          <dd>
            {redenverliesnationaliteitEntity.datumaanvanggeldigheidverlies ? (
              <TextFormat
                value={redenverliesnationaliteitEntity.datumaanvanggeldigheidverlies}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidverlies">Datumeindegeldigheidverlies</span>
          </dt>
          <dd>
            {redenverliesnationaliteitEntity.datumeindegeldigheidverlies ? (
              <TextFormat value={redenverliesnationaliteitEntity.datumeindegeldigheidverlies} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijvingverlies">Omschrijvingverlies</span>
          </dt>
          <dd>{redenverliesnationaliteitEntity.omschrijvingverlies}</dd>
          <dt>
            <span id="redennummerverlies">Redennummerverlies</span>
          </dt>
          <dd>{redenverliesnationaliteitEntity.redennummerverlies}</dd>
        </dl>
        <Button tag={Link} to="/redenverliesnationaliteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/redenverliesnationaliteit/${redenverliesnationaliteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RedenverliesnationaliteitDetail;
