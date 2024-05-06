import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './geboorteingeschrevennatuurlijkpersoon.reducer';

export const GeboorteingeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const geboorteingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="geboorteingeschrevennatuurlijkpersoonDetailsHeading">Geboorteingeschrevennatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="buitenlandseplaatsgeboorte">Buitenlandseplaatsgeboorte</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.buitenlandseplaatsgeboorte}</dd>
          <dt>
            <span id="buitenlandseregiogeboorte">Buitenlandseregiogeboorte</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.buitenlandseregiogeboorte}</dd>
          <dt>
            <span id="datumgeboorte">Datumgeboorte</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.datumgeboorte}</dd>
          <dt>
            <span id="gemeentegeboorte">Gemeentegeboorte</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.gemeentegeboorte}</dd>
          <dt>
            <span id="landofgebiedgeboorte">Landofgebiedgeboorte</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.landofgebiedgeboorte}</dd>
          <dt>
            <span id="omschrijvinglocatiegeboorte">Omschrijvinglocatiegeboorte</span>
          </dt>
          <dd>{geboorteingeschrevennatuurlijkpersoonEntity.omschrijvinglocatiegeboorte}</dd>
        </dl>
        <Button tag={Link} to="/geboorteingeschrevennatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/geboorteingeschrevennatuurlijkpersoon/${geboorteingeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GeboorteingeschrevennatuurlijkpersoonDetail;
