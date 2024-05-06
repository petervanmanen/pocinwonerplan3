import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfsrechtingeschrevennatuurlijkpersoon.reducer';

export const VerblijfsrechtingeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfsrechtingeschrevennatuurlijkpersoonEntity = useAppSelector(
    state => state.verblijfsrechtingeschrevennatuurlijkpersoon.entity,
  );
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfsrechtingeschrevennatuurlijkpersoonDetailsHeading">Verblijfsrechtingeschrevennatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfsrechtingeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="aanduidingverblijfsrecht">Aanduidingverblijfsrecht</span>
          </dt>
          <dd>{verblijfsrechtingeschrevennatuurlijkpersoonEntity.aanduidingverblijfsrecht}</dd>
          <dt>
            <span id="datumaanvangverblijfsrecht">Datumaanvangverblijfsrecht</span>
          </dt>
          <dd>{verblijfsrechtingeschrevennatuurlijkpersoonEntity.datumaanvangverblijfsrecht}</dd>
          <dt>
            <span id="datummededelingverblijfsrecht">Datummededelingverblijfsrecht</span>
          </dt>
          <dd>{verblijfsrechtingeschrevennatuurlijkpersoonEntity.datummededelingverblijfsrecht}</dd>
          <dt>
            <span id="datumvoorzieneindeverblijfsrecht">Datumvoorzieneindeverblijfsrecht</span>
          </dt>
          <dd>{verblijfsrechtingeschrevennatuurlijkpersoonEntity.datumvoorzieneindeverblijfsrecht}</dd>
        </dl>
        <Button tag={Link} to="/verblijfsrechtingeschrevennatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/verblijfsrechtingeschrevennatuurlijkpersoon/${verblijfsrechtingeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfsrechtingeschrevennatuurlijkpersoonDetail;
