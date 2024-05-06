import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './academischetitel.reducer';

export const AcademischetitelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const academischetitelEntity = useAppSelector(state => state.academischetitel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="academischetitelDetailsHeading">Academischetitel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{academischetitelEntity.id}</dd>
          <dt>
            <span id="codeacademischetitel">Codeacademischetitel</span>
          </dt>
          <dd>{academischetitelEntity.codeacademischetitel}</dd>
          <dt>
            <span id="datumbegingeldigheidtitel">Datumbegingeldigheidtitel</span>
          </dt>
          <dd>{academischetitelEntity.datumbegingeldigheidtitel}</dd>
          <dt>
            <span id="datumeindegeldigheidtitel">Datumeindegeldigheidtitel</span>
          </dt>
          <dd>{academischetitelEntity.datumeindegeldigheidtitel}</dd>
          <dt>
            <span id="omschrijvingacademischetitel">Omschrijvingacademischetitel</span>
          </dt>
          <dd>{academischetitelEntity.omschrijvingacademischetitel}</dd>
          <dt>
            <span id="positieacademischetiteltovnaam">Positieacademischetiteltovnaam</span>
          </dt>
          <dd>{academischetitelEntity.positieacademischetiteltovnaam}</dd>
        </dl>
        <Button tag={Link} to="/academischetitel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/academischetitel/${academischetitelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AcademischetitelDetail;
