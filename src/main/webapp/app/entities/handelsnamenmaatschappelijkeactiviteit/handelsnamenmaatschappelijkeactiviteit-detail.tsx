import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './handelsnamenmaatschappelijkeactiviteit.reducer';

export const HandelsnamenmaatschappelijkeactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const handelsnamenmaatschappelijkeactiviteitEntity = useAppSelector(state => state.handelsnamenmaatschappelijkeactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="handelsnamenmaatschappelijkeactiviteitDetailsHeading">Handelsnamenmaatschappelijkeactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{handelsnamenmaatschappelijkeactiviteitEntity.id}</dd>
          <dt>
            <span id="handelsnaam">Handelsnaam</span>
          </dt>
          <dd>{handelsnamenmaatschappelijkeactiviteitEntity.handelsnaam}</dd>
          <dt>
            <span id="verkortenaam">Verkortenaam</span>
          </dt>
          <dd>{handelsnamenmaatschappelijkeactiviteitEntity.verkortenaam}</dd>
          <dt>
            <span id="volgorde">Volgorde</span>
          </dt>
          <dd>{handelsnamenmaatschappelijkeactiviteitEntity.volgorde}</dd>
        </dl>
        <Button tag={Link} to="/handelsnamenmaatschappelijkeactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/handelsnamenmaatschappelijkeactiviteit/${handelsnamenmaatschappelijkeactiviteitEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HandelsnamenmaatschappelijkeactiviteitDetail;
