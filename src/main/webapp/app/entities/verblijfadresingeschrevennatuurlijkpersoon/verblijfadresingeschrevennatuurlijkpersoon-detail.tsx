import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfadresingeschrevennatuurlijkpersoon.reducer';

export const VerblijfadresingeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfadresingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfadresingeschrevennatuurlijkpersoonDetailsHeading">Verblijfadresingeschrevennatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfadresingeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="adresherkomst">Adresherkomst</span>
          </dt>
          <dd>{verblijfadresingeschrevennatuurlijkpersoonEntity.adresherkomst}</dd>
          <dt>
            <span id="beschrijvinglocatie">Beschrijvinglocatie</span>
          </dt>
          <dd>{verblijfadresingeschrevennatuurlijkpersoonEntity.beschrijvinglocatie}</dd>
        </dl>
        <Button tag={Link} to="/verblijfadresingeschrevennatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/verblijfadresingeschrevennatuurlijkpersoon/${verblijfadresingeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfadresingeschrevennatuurlijkpersoonDetail;
