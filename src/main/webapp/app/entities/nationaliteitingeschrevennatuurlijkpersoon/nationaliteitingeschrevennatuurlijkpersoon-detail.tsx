import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nationaliteitingeschrevennatuurlijkpersoon.reducer';

export const NationaliteitingeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nationaliteitingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.nationaliteitingeschrevennatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nationaliteitingeschrevennatuurlijkpersoonDetailsHeading">Nationaliteitingeschrevennatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nationaliteitingeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="buitenlandspersoonsnummer">Buitenlandspersoonsnummer</span>
          </dt>
          <dd>{nationaliteitingeschrevennatuurlijkpersoonEntity.buitenlandspersoonsnummer}</dd>
          <dt>
            <span id="nationaliteit">Nationaliteit</span>
          </dt>
          <dd>{nationaliteitingeschrevennatuurlijkpersoonEntity.nationaliteit}</dd>
          <dt>
            <span id="redenverkrijging">Redenverkrijging</span>
          </dt>
          <dd>{nationaliteitingeschrevennatuurlijkpersoonEntity.redenverkrijging}</dd>
          <dt>
            <span id="redenverlies">Redenverlies</span>
          </dt>
          <dd>{nationaliteitingeschrevennatuurlijkpersoonEntity.redenverlies}</dd>
        </dl>
        <Button tag={Link} to="/nationaliteitingeschrevennatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/nationaliteitingeschrevennatuurlijkpersoon/${nationaliteitingeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NationaliteitingeschrevennatuurlijkpersoonDetail;
