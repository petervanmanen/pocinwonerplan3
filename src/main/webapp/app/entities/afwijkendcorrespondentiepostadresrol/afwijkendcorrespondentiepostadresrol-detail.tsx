import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './afwijkendcorrespondentiepostadresrol.reducer';

export const AfwijkendcorrespondentiepostadresrolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const afwijkendcorrespondentiepostadresrolEntity = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="afwijkendcorrespondentiepostadresrolDetailsHeading">Afwijkendcorrespondentiepostadresrol</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{afwijkendcorrespondentiepostadresrolEntity.id}</dd>
          <dt>
            <span id="postadrestype">Postadrestype</span>
          </dt>
          <dd>{afwijkendcorrespondentiepostadresrolEntity.postadrestype}</dd>
          <dt>
            <span id="postbusofantwoordnummer">Postbusofantwoordnummer</span>
          </dt>
          <dd>{afwijkendcorrespondentiepostadresrolEntity.postbusofantwoordnummer}</dd>
          <dt>
            <span id="postcodepostadres">Postcodepostadres</span>
          </dt>
          <dd>{afwijkendcorrespondentiepostadresrolEntity.postcodepostadres}</dd>
        </dl>
        <Button tag={Link} to="/afwijkendcorrespondentiepostadresrol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/afwijkendcorrespondentiepostadresrol/${afwijkendcorrespondentiepostadresrolEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AfwijkendcorrespondentiepostadresrolDetail;
