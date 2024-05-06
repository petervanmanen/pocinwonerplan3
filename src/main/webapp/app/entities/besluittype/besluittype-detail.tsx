import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './besluittype.reducer';

export const BesluittypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const besluittypeEntity = useAppSelector(state => state.besluittype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="besluittypeDetailsHeading">Besluittype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{besluittypeEntity.id}</dd>
          <dt>
            <span id="besluitcategorie">Besluitcategorie</span>
          </dt>
          <dd>{besluittypeEntity.besluitcategorie}</dd>
          <dt>
            <span id="besluittypeomschrijving">Besluittypeomschrijving</span>
          </dt>
          <dd>{besluittypeEntity.besluittypeomschrijving}</dd>
          <dt>
            <span id="besluittypeomschrijvinggeneriek">Besluittypeomschrijvinggeneriek</span>
          </dt>
          <dd>{besluittypeEntity.besluittypeomschrijvinggeneriek}</dd>
          <dt>
            <span id="datumbegingeldigheidbesluittype">Datumbegingeldigheidbesluittype</span>
          </dt>
          <dd>{besluittypeEntity.datumbegingeldigheidbesluittype}</dd>
          <dt>
            <span id="datumeindegeldigheidbesluittype">Datumeindegeldigheidbesluittype</span>
          </dt>
          <dd>{besluittypeEntity.datumeindegeldigheidbesluittype}</dd>
          <dt>
            <span id="indicatiepublicatie">Indicatiepublicatie</span>
          </dt>
          <dd>{besluittypeEntity.indicatiepublicatie}</dd>
          <dt>
            <span id="publicatietekst">Publicatietekst</span>
          </dt>
          <dd>{besluittypeEntity.publicatietekst}</dd>
          <dt>
            <span id="publicatietermijn">Publicatietermijn</span>
          </dt>
          <dd>{besluittypeEntity.publicatietermijn}</dd>
          <dt>
            <span id="reactietermijn">Reactietermijn</span>
          </dt>
          <dd>{besluittypeEntity.reactietermijn}</dd>
        </dl>
        <Button tag={Link} to="/besluittype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/besluittype/${besluittypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BesluittypeDetail;
