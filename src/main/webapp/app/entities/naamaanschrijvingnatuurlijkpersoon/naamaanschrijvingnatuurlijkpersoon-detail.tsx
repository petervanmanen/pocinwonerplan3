import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './naamaanschrijvingnatuurlijkpersoon.reducer';

export const NaamaanschrijvingnatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const naamaanschrijvingnatuurlijkpersoonEntity = useAppSelector(state => state.naamaanschrijvingnatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="naamaanschrijvingnatuurlijkpersoonDetailsHeading">Naamaanschrijvingnatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{naamaanschrijvingnatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="aanhefaanschrijving">Aanhefaanschrijving</span>
          </dt>
          <dd>{naamaanschrijvingnatuurlijkpersoonEntity.aanhefaanschrijving}</dd>
          <dt>
            <span id="geslachtsnaamaanschrijving">Geslachtsnaamaanschrijving</span>
          </dt>
          <dd>{naamaanschrijvingnatuurlijkpersoonEntity.geslachtsnaamaanschrijving}</dd>
          <dt>
            <span id="voorlettersaanschrijving">Voorlettersaanschrijving</span>
          </dt>
          <dd>{naamaanschrijvingnatuurlijkpersoonEntity.voorlettersaanschrijving}</dd>
          <dt>
            <span id="voornamenaanschrijving">Voornamenaanschrijving</span>
          </dt>
          <dd>{naamaanschrijvingnatuurlijkpersoonEntity.voornamenaanschrijving}</dd>
        </dl>
        <Button tag={Link} to="/naamaanschrijvingnatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/naamaanschrijvingnatuurlijkpersoon/${naamaanschrijvingnatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NaamaanschrijvingnatuurlijkpersoonDetail;
