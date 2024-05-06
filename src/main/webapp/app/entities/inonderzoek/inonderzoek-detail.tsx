import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inonderzoek.reducer';

export const InonderzoekDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inonderzoekEntity = useAppSelector(state => state.inonderzoek.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inonderzoekDetailsHeading">Inonderzoek</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inonderzoekEntity.id}</dd>
          <dt>
            <span id="aanduidinggegevensinonderzoek">Aanduidinggegevensinonderzoek</span>
          </dt>
          <dd>{inonderzoekEntity.aanduidinggegevensinonderzoek}</dd>
        </dl>
        <Button tag={Link} to="/inonderzoek" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inonderzoek/${inonderzoekEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InonderzoekDetail;
