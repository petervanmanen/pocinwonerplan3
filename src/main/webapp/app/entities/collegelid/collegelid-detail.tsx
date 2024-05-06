import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './collegelid.reducer';

export const CollegelidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const collegelidEntity = useAppSelector(state => state.collegelid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="collegelidDetailsHeading">Collegelid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{collegelidEntity.id}</dd>
          <dt>
            <span id="achternaam">Achternaam</span>
          </dt>
          <dd>{collegelidEntity.achternaam}</dd>
          <dt>
            <span id="datumaanstelling">Datumaanstelling</span>
          </dt>
          <dd>
            {collegelidEntity.datumaanstelling ? (
              <TextFormat value={collegelidEntity.datumaanstelling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumuittreding">Datumuittreding</span>
          </dt>
          <dd>
            {collegelidEntity.datumuittreding ? (
              <TextFormat value={collegelidEntity.datumuittreding} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fractie">Fractie</span>
          </dt>
          <dd>{collegelidEntity.fractie}</dd>
          <dt>
            <span id="portefeuille">Portefeuille</span>
          </dt>
          <dd>{collegelidEntity.portefeuille}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{collegelidEntity.titel}</dd>
          <dt>
            <span id="voornaam">Voornaam</span>
          </dt>
          <dd>{collegelidEntity.voornaam}</dd>
        </dl>
        <Button tag={Link} to="/collegelid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/collegelid/${collegelidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CollegelidDetail;
