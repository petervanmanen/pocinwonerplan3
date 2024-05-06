import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './geboorteingeschrevenpersoon.reducer';

export const GeboorteingeschrevenpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const geboorteingeschrevenpersoonEntity = useAppSelector(state => state.geboorteingeschrevenpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="geboorteingeschrevenpersoonDetailsHeading">Geboorteingeschrevenpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{geboorteingeschrevenpersoonEntity.id}</dd>
          <dt>
            <span id="datumgeboorte">Datumgeboorte</span>
          </dt>
          <dd>{geboorteingeschrevenpersoonEntity.datumgeboorte}</dd>
          <dt>
            <span id="geboorteland">Geboorteland</span>
          </dt>
          <dd>{geboorteingeschrevenpersoonEntity.geboorteland}</dd>
          <dt>
            <span id="geboorteplaats">Geboorteplaats</span>
          </dt>
          <dd>{geboorteingeschrevenpersoonEntity.geboorteplaats}</dd>
        </dl>
        <Button tag={Link} to="/geboorteingeschrevenpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/geboorteingeschrevenpersoon/${geboorteingeschrevenpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GeboorteingeschrevenpersoonDetail;
