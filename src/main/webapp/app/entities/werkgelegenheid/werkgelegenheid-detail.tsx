import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './werkgelegenheid.reducer';

export const WerkgelegenheidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const werkgelegenheidEntity = useAppSelector(state => state.werkgelegenheid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="werkgelegenheidDetailsHeading">Werkgelegenheid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{werkgelegenheidEntity.id}</dd>
          <dt>
            <span id="aantalfulltimemannen">Aantalfulltimemannen</span>
          </dt>
          <dd>{werkgelegenheidEntity.aantalfulltimemannen}</dd>
          <dt>
            <span id="aantalfulltimevrouwen">Aantalfulltimevrouwen</span>
          </dt>
          <dd>{werkgelegenheidEntity.aantalfulltimevrouwen}</dd>
          <dt>
            <span id="aantalparttimemannen">Aantalparttimemannen</span>
          </dt>
          <dd>{werkgelegenheidEntity.aantalparttimemannen}</dd>
          <dt>
            <span id="aantalparttimevrouwen">Aantalparttimevrouwen</span>
          </dt>
          <dd>{werkgelegenheidEntity.aantalparttimevrouwen}</dd>
          <dt>
            <span id="grootteklasse">Grootteklasse</span>
          </dt>
          <dd>{werkgelegenheidEntity.grootteklasse}</dd>
        </dl>
        <Button tag={Link} to="/werkgelegenheid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/werkgelegenheid/${werkgelegenheidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WerkgelegenheidDetail;
