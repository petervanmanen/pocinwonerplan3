import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overigeadresseerbaarobjectaanduiding.reducer';

export const OverigeadresseerbaarobjectaanduidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overigeadresseerbaarobjectaanduidingEntity = useAppSelector(state => state.overigeadresseerbaarobjectaanduiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overigeadresseerbaarobjectaanduidingDetailsHeading">Overigeadresseerbaarobjectaanduiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overigeadresseerbaarobjectaanduidingEntity.id}</dd>
          <dt>
            <span id="identificatiecodeoverigadresseerbaarobjectaanduiding">Identificatiecodeoverigadresseerbaarobjectaanduiding</span>
          </dt>
          <dd>{overigeadresseerbaarobjectaanduidingEntity.identificatiecodeoverigadresseerbaarobjectaanduiding}</dd>
        </dl>
        <Button tag={Link} to="/overigeadresseerbaarobjectaanduiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/overigeadresseerbaarobjectaanduiding/${overigeadresseerbaarobjectaanduidingEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverigeadresseerbaarobjectaanduidingDetail;
