import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overlijdeningeschrevennatuurlijkpersoon.reducer';

export const OverlijdeningeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overlijdeningeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.overlijdeningeschrevennatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overlijdeningeschrevennatuurlijkpersoonDetailsHeading">Overlijdeningeschrevennatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="buitenlandseplaatsoverlijden">Buitenlandseplaatsoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.buitenlandseplaatsoverlijden}</dd>
          <dt>
            <span id="buitenlandseregiooverlijden">Buitenlandseregiooverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.buitenlandseregiooverlijden}</dd>
          <dt>
            <span id="datumoverlijden">Datumoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.datumoverlijden}</dd>
          <dt>
            <span id="gemeenteoverlijden">Gemeenteoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.gemeenteoverlijden}</dd>
          <dt>
            <span id="landofgebiedoverlijden">Landofgebiedoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.landofgebiedoverlijden}</dd>
          <dt>
            <span id="omschrijvinglocatieoverlijden">Omschrijvinglocatieoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevennatuurlijkpersoonEntity.omschrijvinglocatieoverlijden}</dd>
        </dl>
        <Button tag={Link} to="/overlijdeningeschrevennatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/overlijdeningeschrevennatuurlijkpersoon/${overlijdeningeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverlijdeningeschrevennatuurlijkpersoonDetail;
