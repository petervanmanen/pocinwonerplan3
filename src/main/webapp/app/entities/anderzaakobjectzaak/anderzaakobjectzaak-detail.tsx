import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './anderzaakobjectzaak.reducer';

export const AnderzaakobjectzaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const anderzaakobjectzaakEntity = useAppSelector(state => state.anderzaakobjectzaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="anderzaakobjectzaakDetailsHeading">Anderzaakobjectzaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{anderzaakobjectzaakEntity.id}</dd>
          <dt>
            <span id="anderzaakobjectaanduiding">Anderzaakobjectaanduiding</span>
          </dt>
          <dd>{anderzaakobjectzaakEntity.anderzaakobjectaanduiding}</dd>
          <dt>
            <span id="anderzaakobjectlocatie">Anderzaakobjectlocatie</span>
          </dt>
          <dd>{anderzaakobjectzaakEntity.anderzaakobjectlocatie}</dd>
          <dt>
            <span id="anderzaakobjectomschrijving">Anderzaakobjectomschrijving</span>
          </dt>
          <dd>{anderzaakobjectzaakEntity.anderzaakobjectomschrijving}</dd>
          <dt>
            <span id="anderzaakobjectregistratie">Anderzaakobjectregistratie</span>
          </dt>
          <dd>{anderzaakobjectzaakEntity.anderzaakobjectregistratie}</dd>
        </dl>
        <Button tag={Link} to="/anderzaakobjectzaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/anderzaakobjectzaak/${anderzaakobjectzaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnderzaakobjectzaakDetail;
