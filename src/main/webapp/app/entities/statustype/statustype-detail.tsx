import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './statustype.reducer';

export const StatustypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const statustypeEntity = useAppSelector(state => state.statustype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="statustypeDetailsHeading">Statustype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{statustypeEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidstatustype">Datumbegingeldigheidstatustype</span>
          </dt>
          <dd>{statustypeEntity.datumbegingeldigheidstatustype}</dd>
          <dt>
            <span id="datumeindegeldigheidstatustype">Datumeindegeldigheidstatustype</span>
          </dt>
          <dd>{statustypeEntity.datumeindegeldigheidstatustype}</dd>
          <dt>
            <span id="doorlooptijdstatus">Doorlooptijdstatus</span>
          </dt>
          <dd>{statustypeEntity.doorlooptijdstatus}</dd>
          <dt>
            <span id="statustypeomschrijving">Statustypeomschrijving</span>
          </dt>
          <dd>{statustypeEntity.statustypeomschrijving}</dd>
          <dt>
            <span id="statustypeomschrijvinggeneriek">Statustypeomschrijvinggeneriek</span>
          </dt>
          <dd>{statustypeEntity.statustypeomschrijvinggeneriek}</dd>
          <dt>
            <span id="statustypevolgnummer">Statustypevolgnummer</span>
          </dt>
          <dd>{statustypeEntity.statustypevolgnummer}</dd>
          <dt>Heeft Zaaktype</dt>
          <dd>{statustypeEntity.heeftZaaktype ? statustypeEntity.heeftZaaktype.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/statustype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/statustype/${statustypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StatustypeDetail;
