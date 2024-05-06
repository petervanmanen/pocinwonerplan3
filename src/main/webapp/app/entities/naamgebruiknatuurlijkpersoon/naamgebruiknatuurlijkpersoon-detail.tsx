import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './naamgebruiknatuurlijkpersoon.reducer';

export const NaamgebruiknatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const naamgebruiknatuurlijkpersoonEntity = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="naamgebruiknatuurlijkpersoonDetailsHeading">Naamgebruiknatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{naamgebruiknatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="aanhefaanschrijving">Aanhefaanschrijving</span>
          </dt>
          <dd>{naamgebruiknatuurlijkpersoonEntity.aanhefaanschrijving}</dd>
          <dt>
            <span id="adellijketitelnaamgebruik">Adellijketitelnaamgebruik</span>
          </dt>
          <dd>{naamgebruiknatuurlijkpersoonEntity.adellijketitelnaamgebruik}</dd>
          <dt>
            <span id="geslachtsnaamstamnaamgebruik">Geslachtsnaamstamnaamgebruik</span>
          </dt>
          <dd>{naamgebruiknatuurlijkpersoonEntity.geslachtsnaamstamnaamgebruik}</dd>
        </dl>
        <Button tag={Link} to="/naamgebruiknatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/naamgebruiknatuurlijkpersoon/${naamgebruiknatuurlijkpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NaamgebruiknatuurlijkpersoonDetail;
