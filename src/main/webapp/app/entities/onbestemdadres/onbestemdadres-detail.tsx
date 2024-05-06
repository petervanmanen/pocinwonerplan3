import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './onbestemdadres.reducer';

export const OnbestemdadresDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const onbestemdadresEntity = useAppSelector(state => state.onbestemdadres.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="onbestemdadresDetailsHeading">Onbestemdadres</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{onbestemdadresEntity.id}</dd>
          <dt>
            <span id="huisletter">Huisletter</span>
          </dt>
          <dd>{onbestemdadresEntity.huisletter}</dd>
          <dt>
            <span id="huisnummer">Huisnummer</span>
          </dt>
          <dd>{onbestemdadresEntity.huisnummer}</dd>
          <dt>
            <span id="huisnummertoevoeging">Huisnummertoevoeging</span>
          </dt>
          <dd>{onbestemdadresEntity.huisnummertoevoeging}</dd>
          <dt>
            <span id="postcode">Postcode</span>
          </dt>
          <dd>{onbestemdadresEntity.postcode}</dd>
          <dt>
            <span id="straatnaam">Straatnaam</span>
          </dt>
          <dd>{onbestemdadresEntity.straatnaam}</dd>
        </dl>
        <Button tag={Link} to="/onbestemdadres" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/onbestemdadres/${onbestemdadresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OnbestemdadresDetail;
